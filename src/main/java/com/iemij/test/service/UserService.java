package com.iemij.test.service;

import com.iemij.test.domain.CommonUser;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Long login(String account,String password);
    CommonUser adminLogin(String account,String password);
    CommonUser getUserInfo(Long uid);
}
