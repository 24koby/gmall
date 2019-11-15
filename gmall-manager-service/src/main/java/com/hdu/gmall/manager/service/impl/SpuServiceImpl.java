package com.hdu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hdu.gmall.bean.PmsProductInfo;
import com.hdu.gmall.manager.mapper.PmsProductInfoMapper;
import com.hdu.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpuServiceImpl  implements SpuService {
    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;


    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        Example e = new Example(PmsProductInfo.class);
        e.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        List<PmsProductInfo> pmsProductInfoList = pmsProductInfoMapper.selectByExample(e);
        return pmsProductInfoList;
    }
}
