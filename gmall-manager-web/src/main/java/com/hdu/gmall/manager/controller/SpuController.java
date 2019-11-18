package com.hdu.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdu.gmall.bean.*;
import com.hdu.gmall.manager.util.PmsUploadFile;
import com.hdu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {
    @Reference
    private SpuService spuService;

    @RequestMapping("/spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){
        return spuService.spuList(catalog3Id);
    }


    @RequestMapping("/spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){
        return spuService.spuSaleAttrList(spuId);
    }


    @RequestMapping("/spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageLists(String spuId){
        return spuService.spuImageList(spuId);
    }


    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        String imgUrl = PmsUploadFile.uploadFile(multipartFile);
        System.out.println(imgUrl);
        return imgUrl;
    }

    @RequestMapping("/saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        String result = spuService.saveSpuInfo(pmsProductInfo);
        return result;
    }
}
