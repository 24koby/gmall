package com.hdu.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdu.gmall.bean.AttrService;
import com.hdu.gmall.bean.PmsBaseAttrInfo;
import com.hdu.gmall.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class AttrController {

    @Reference
    private AttrService attrService;


    @RequestMapping("/attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> getAttrInfoList(@RequestParam("catalog3Id") String catalog3Id){
        List<PmsBaseAttrInfo> attrInfoList = attrService.getAttrInfoList(catalog3Id);
        return attrInfoList;
    }
}
