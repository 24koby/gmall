package com.hdu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hdu.gmall.bean.PmsSkuAttrValue;
import com.hdu.gmall.bean.PmsSkuImage;
import com.hdu.gmall.bean.PmsSkuInfo;
import com.hdu.gmall.bean.PmsSkuSaleAttrValue;
import com.hdu.gmall.manager.mapper.PmsSkuAttrValueMapper;
import com.hdu.gmall.manager.mapper.PmsSkuImageMapper;
import com.hdu.gmall.manager.mapper.PmsSkuInfoMapper;
import com.hdu.gmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.hdu.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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

    @Override
    public PmsSkuInfo getSkuInfoById(String skuId) {
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
}
