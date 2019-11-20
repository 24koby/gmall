package com.hdu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hdu.gmall.bean.*;
import com.hdu.gmall.manager.mapper.*;
import com.hdu.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpuServiceImpl  implements SpuService {
    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsProductImageMapper pmsProductImageMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        Example e = new Example(PmsProductInfo.class);
        e.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        List<PmsProductInfo> pmsProductInfoList = pmsProductInfoMapper.selectByExample(e);
        return pmsProductInfoList;
    }

    @Override
    public String saveSpuInfo(PmsProductInfo pmsProductInfo) {
        try {
            pmsProductInfoMapper.insert(pmsProductInfo);

            List<PmsProductImage> imageList = pmsProductInfo.getSpuImageList();

            for (PmsProductImage img: imageList) {
                img.setProductId(pmsProductInfo.getId());
                pmsProductImageMapper.insert(img);
            }

            List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();

            for (PmsProductSaleAttr saleAttr:spuSaleAttrList) {
                saleAttr.setProductId(pmsProductInfo.getId());
                pmsProductSaleAttrMapper.insert(saleAttr);

                List<PmsProductSaleAttrValue> spuSaleAttrValueList = saleAttr.getSpuSaleAttrValueList();
                for (PmsProductSaleAttrValue spuSaleAttrValue:spuSaleAttrValueList) {
                    spuSaleAttrValue.setProductId(pmsProductInfo.getId());
                    pmsProductSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                }
            }
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        Example e = new Example(PmsProductSaleAttr.class);
        e.createCriteria().andEqualTo("productId",spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrList= pmsProductSaleAttrMapper.selectByExample(e);
        for (PmsProductSaleAttr saleAttr: pmsProductSaleAttrList) {
            Example exp = new Example(PmsProductSaleAttrValue.class);
            exp.createCriteria().andEqualTo("productId",spuId);
            exp.createCriteria().andEqualTo("saleAttrId",saleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValuesList = pmsProductSaleAttrValueMapper.selectByExample(exp);
            saleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValuesList);
        }
        return pmsProductSaleAttrList;
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        Example e = new Example(PmsProductImage.class);
        e.createCriteria().andEqualTo("productId",spuId);
        List<PmsProductImage> pmsProductImageList = pmsProductImageMapper.selectByExample(e);
        return pmsProductImageList;
    }

    @Override
    public List<PmsProductSaleAttr> spuSkuSaleAttrList(String spuId) {
        Example e = new Example(PmsProductSaleAttr.class);
        e.createCriteria().andEqualTo("productId",spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductSaleAttrMapper.selectByExample(e);
        return pmsProductSaleAttrList;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId,skuId);
        return pmsProductSaleAttrList;
    }


}
