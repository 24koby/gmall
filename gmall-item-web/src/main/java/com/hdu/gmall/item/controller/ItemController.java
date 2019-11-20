package com.hdu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdu.gmall.bean.PmsProductSaleAttr;
import com.hdu.gmall.bean.PmsSkuInfo;
import com.hdu.gmall.service.SkuService;
import com.hdu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ItemController {
    @Reference
    private SkuService skuService;
    @Reference
    private SpuService spuService;
    @RequestMapping("/{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map){
        System.out.println(skuId);
        PmsSkuInfo skuInfo = skuService.getSkuInfoById(skuId);
        map.put("skuInfo",skuInfo);

        List<PmsProductSaleAttr> pmsProductSaleAttrList= spuService.spuSaleAttrListCheckBySku(skuInfo.getProductId(),skuInfo.getId());
        map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrList);
        return "item";
    }
}
