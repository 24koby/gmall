package com.hdu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hdu.gmall.bean.AttrService;
import com.hdu.gmall.bean.PmsBaseAttrInfo;
import com.hdu.gmall.bean.PmsBaseAttrValue;
import com.hdu.gmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.hdu.gmall.manager.mapper.PmsBaseAttrValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

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
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);

            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue value: attrValueList) {
                value.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insertSelective(value);
            }
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }
}
