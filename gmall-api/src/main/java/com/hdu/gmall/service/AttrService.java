package com.hdu.gmall.service;

import com.hdu.gmall.bean.PmsBaseAttrInfo;
import com.hdu.gmall.bean.PmsBaseAttrValue;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);
}
