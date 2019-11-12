package com.hdu.gmall.service;

import com.hdu.gmall.bean.PmsBaseAttrInfo;
import com.hdu.gmall.bean.PmsBaseCatalog1;
import com.hdu.gmall.bean.PmsBaseCatalog2;
import com.hdu.gmall.bean.PmsBaseCatalog3;

import java.util.List;

public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalogId);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);

}
