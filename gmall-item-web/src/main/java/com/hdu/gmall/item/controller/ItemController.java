package com.hdu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.hdu.gmall.bean.PmsProductSaleAttr;
import com.hdu.gmall.bean.PmsSkuAttrValue;
import com.hdu.gmall.bean.PmsSkuInfo;
import com.hdu.gmall.bean.PmsSkuSaleAttrValue;
import com.hdu.gmall.service.SkuService;
import com.hdu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(skuInfo.getProductId());
        Map<String,String> skuSaleAttrMap = new HashMap<>();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos){
            String id = pmsSkuInfo.getId();
            String k = "";
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();

            for (PmsSkuSaleAttrValue skuSaleAttrValue:skuSaleAttrValueList) {
                k += skuSaleAttrValue.getSaleAttrValueId() + "|";
            }
            skuSaleAttrMap.put(k,id);
        }
        String jsonStr = JSON.toJSONString(skuSaleAttrMap);
        map.put("jsonStr",jsonStr);
        return "item";
    }
}
