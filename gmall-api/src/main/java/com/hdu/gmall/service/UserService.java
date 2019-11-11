package com.hdu.gmall.service;

import com.hdu.gmall.bean.UmsMember;
import com.hdu.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
