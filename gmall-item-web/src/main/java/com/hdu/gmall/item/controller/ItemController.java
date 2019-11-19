package com.hdu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdu.gmall.bean.PmsSkuInfo;
import com.hdu.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Reference
    private SkuService skuService;

    @RequestMapping("/{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map){
        System.out.println(skuId);
        PmsSkuInfo skuInfo = skuService.getSkuInfoById(skuId);
        map.put("skuInfo",skuInfo);

        return "item";
    }
}
