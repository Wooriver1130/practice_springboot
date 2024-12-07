package com.ict.practice.domain.users.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ict.practice.domain.users.mapper.UsersMapper;
import com.ict.practice.domain.users.vo.UsersVO;

public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper mapper;

    @Override
    public UsersVO isExistUser(String u_id) {
        return mapper.isExistUser(u_id) ;
    }

}
