package com.hdu.gmall.service.impl;





import com.alibaba.dubbo.config.annotation.Service;
import com.hdu.gmall.bean.UmsMember;
import com.hdu.gmall.bean.UmsMemberReceiveAddress;
import com.hdu.gmall.mapper.UmsMemberMapper;
import com.hdu.gmall.mapper.UmsMemberReceiveAddressMapper;
import com.hdu.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
   private UmsMemberMapper umsMemberMapper;
    @Autowired
    private UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    @Override
    public List<UmsMember> getAllUser() {

        return umsMemberMapper.selectAll();
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        Example e = new Example(UmsMemberReceiveAddress.class);
        e.createCriteria().andEqualTo("memberId",memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.selectByExample(e);
        return umsMemberReceiveAddresses;
    }
}
