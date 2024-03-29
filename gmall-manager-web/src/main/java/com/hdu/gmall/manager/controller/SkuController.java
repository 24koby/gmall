package com.hdu.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdu.gmall.bean.PmsSkuInfo;
import com.hdu.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class SkuController {
    @Reference
    private SkuService skuService;

    @RequestMapping("/saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());
        String result = skuService.saveSkuInfo(pmsSkuInfo);
        return result;
    }

}
