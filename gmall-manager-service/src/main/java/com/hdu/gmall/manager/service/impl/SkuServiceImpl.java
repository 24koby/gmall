package com.hdu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.hdu.gmall.bean.PmsSkuAttrValue;
import com.hdu.gmall.bean.PmsSkuImage;
import com.hdu.gmall.bean.PmsSkuInfo;
import com.hdu.gmall.bean.PmsSkuSaleAttrValue;
import com.hdu.gmall.manager.mapper.PmsSkuAttrValueMapper;
import com.hdu.gmall.manager.mapper.PmsSkuImageMapper;
import com.hdu.gmall.manager.mapper.PmsSkuInfoMapper;
import com.hdu.gmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.hdu.gmall.service.SkuService;
import com.hdu.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {


    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public String saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        try {
            pmsSkuInfoMapper.insert(pmsSkuInfo);
            String spuId = pmsSkuInfo.getId();
            List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue skuAttrValue:skuAttrValueList) {
                skuAttrValue.setSkuId(spuId);
                pmsSkuAttrValueMapper.insertSelective(skuAttrValue);
            }

            List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
            for (PmsSkuImage skuImage:skuImageList) {
                skuImage.setSkuId(spuId);
                pmsSkuImageMapper.insertSelective(skuImage);
            }
            
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue skuSaleAttrValue:skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(spuId);
                pmsSkuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
            }
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }


    public PmsSkuInfo getSkuByIdFromDb(String skuId){
        Example e = new Example(PmsSkuInfo.class);
        e.createCriteria().andEqualTo("id",skuId);
        List<PmsSkuInfo> pmsSkuInfosList = pmsSkuInfoMapper.selectByExample(e);

        Example expImg = new Example(PmsSkuImage.class);
        expImg.createCriteria().andEqualTo("skuId",skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.selectByExample(expImg);
        if (pmsSkuInfosList.size() > 0){
            pmsSkuInfosList.get(0).setSkuImageList(pmsSkuImages);
            return pmsSkuInfosList.get(0);
        }
        return null;
    }

    @Override
    public PmsSkuInfo getSkuInfoById(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        
//       连接缓存
        Jedis jedis = redisUtil.getJedis();
//        查询缓存
        String skuKey = "sku:" + skuId + ":info";
        String skuJson = jedis.get(skuKey);
        if (StringUtils.isNotBlank(skuJson)){
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }else {
            String token = UUID.randomUUID().toString();
            String ok = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10000);
            if (StringUtils.isNotBlank(ok) && ok.equals("OK")){
                //        缓存不命中，查询数据库
                pmsSkuInfo = getSkuByIdFromDb(skuId);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //        查询数据库，存入redis
                if (pmsSkuInfo != null) {
                    String json = JSON.toJSONString(pmsSkuInfo);
                    jedis.set("sku:" + skuId + ":info", json);
                } else {
//                数据库不存在该sku,
//                为了防止缓存穿透，null或者空字符串设置给redis
                    jedis.setex("sku:" + skuId + ":info",60*3, JSON.toJSONString(""));
                }
                String token2 = jedis.get("sku:" + skuId + ":lock");
                if (StringUtils.isNotBlank(token2) && token2.equals(token)){
                    jedis.del("sku:" + skuId + ":lock");
                }
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuInfoById(skuId);
            }

        }
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {

        List<PmsSkuInfo> list = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return list;
    }
}
