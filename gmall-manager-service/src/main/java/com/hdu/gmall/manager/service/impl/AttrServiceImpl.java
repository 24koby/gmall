package com.hdu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hdu.gmall.bean.PmsBaseSaleAttr;
import com.hdu.gmall.bean.PmsProductSaleAttr;
import com.hdu.gmall.manager.mapper.PmsBaseSaleAttrMapper;
import com.hdu.gmall.manager.mapper.PmsProductSaleAttrMapper;
import com.hdu.gmall.service.AttrService;
import com.hdu.gmall.bean.PmsBaseAttrInfo;
import com.hdu.gmall.bean.PmsBaseAttrValue;
import com.hdu.gmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.hdu.gmall.manager.mapper.PmsBaseAttrValueMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Override
    public List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id) {
        Example e = new Example(PmsBaseAttrInfo.class);
        e.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        List<PmsBaseAttrInfo> attrInfos = pmsBaseAttrInfoMapper.selectByExample(e);
        return attrInfos;
    }

    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        try {
            if (!StringUtils.isNotBlank(pmsBaseAttrInfo.getId())){
                pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
                System.out.println(pmsBaseAttrInfo.getId());
                List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                for (PmsBaseAttrValue value: attrValueList) {
                    value.setAttrId(pmsBaseAttrInfo.getId());
                    pmsBaseAttrValueMapper.insertSelective(value);
                }
            } else {
                pmsBaseAttrInfoMapper.updateByPrimaryKeySelective(pmsBaseAttrInfo);

                Example e = new Example(PmsBaseAttrValue.class);
                e.createCriteria().andEqualTo("attrId",pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.deleteByExample(e);
                for (PmsBaseAttrValue value: pmsBaseAttrInfo.getAttrValueList()) {
                    value.setAttrId(pmsBaseAttrInfo.getId());
                    pmsBaseAttrValueMapper.insertSelective(value);
                }
            }
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        Example e = new Example(PmsBaseAttrValue.class);
        e.createCriteria().andEqualTo("attrId",attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValueList = pmsBaseAttrValueMapper.selectByExample(e);
        return pmsBaseAttrValueList;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectAll();
        return pmsBaseSaleAttrs;
    }
}
