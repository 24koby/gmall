package com.hdu.gmall.manager.mapper;

import com.hdu.gmall.bean.PmsSkuAttrValue;
import com.hdu.gmall.bean.PmsSkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

public interface PmsSkuSaleAttrValueMapper extends Mapper<PmsSkuSaleAttrValue> {
    void select(PmsSkuAttrValue pmsSkuAttrValue);
}
