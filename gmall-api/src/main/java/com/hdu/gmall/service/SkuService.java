package com.hdu.gmall.service;

import com.hdu.gmall.bean.PmsSkuInfo;

public interface SkuService {
    String saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuInfoById(String skuId);
}
