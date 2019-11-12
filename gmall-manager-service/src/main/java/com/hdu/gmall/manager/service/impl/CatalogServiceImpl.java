package com.hdu.gmall.manager.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.hdu.gmall.bean.PmsBaseAttrInfo;
import com.hdu.gmall.bean.PmsBaseCatalog1;
import com.hdu.gmall.bean.PmsBaseCatalog2;
import com.hdu.gmall.bean.PmsBaseCatalog3;
import com.hdu.gmall.manager.mapper.PmsBaseCatalog1Mapper;
import com.hdu.gmall.manager.mapper.PmsBaseCatalog2Mapper;
import com.hdu.gmall.manager.mapper.PmsBaseCatalog3Mapper;
import com.hdu.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;


import java.util.List;
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Autowired
    private PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Autowired
    private PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> catalog1s = pmsBaseCatalog1Mapper.selectAll();
        return catalog1s;
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalogId) {
        Example e = new Example(PmsBaseCatalog2.class);
        e.createCriteria().andEqualTo("catalog1Id",catalogId);
        List<PmsBaseCatalog2> catalog2s = pmsBaseCatalog2Mapper.selectByExample(e);
        return catalog2s;
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        Example e = new Example(PmsBaseCatalog3.class);
        e.createCriteria().andEqualTo("catalog2Id",catalog2Id);
        List<PmsBaseCatalog3> catalog3s = pmsBaseCatalog3Mapper.selectByExample(e);
        return catalog3s;
    }


}
