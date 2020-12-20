package com.iemij.test.impl;

import com.iemij.test.domain.CommonUser;
import com.iemij.test.mapper.UserMapper;
import com.iemij.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Long login(String account, String password) {
        CommonUser user = new CommonUser();
        user.setAccount(account);
        user.setPassword(password);
        user = userMapper.selectOne(user);
        return user.getUid();
    }

    @Override
    public CommonUser adminLogin(String account, String password) {
        CommonUser user = new CommonUser();
        user.setAccount(account);
        user.setPassword(password);
        user = userMapper.selectOne(user);
        return user;
    }

    @Override
    public CommonUser getUserInfo(Long uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
