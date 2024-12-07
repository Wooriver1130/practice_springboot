package com.ict.practice.domain.users.mapper;

import com.ict.practice.domain.users.vo.UsersVO;

public interface UsersMapper {
    
    public UsersVO isExistUser(String u_id);
}
