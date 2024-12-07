package com.ict.practice.domain.users.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ict.practice.common.util.JwtUtil;
import com.ict.practice.domain.users.service.UsersService;
import com.ict.practice.domain.users.vo.ResultVO;
import com.ict.practice.domain.users.vo.UsersVO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequestMapping("api/users")
public class UsersController {

    @Autowired
    private UsersService service;
    @Autowired
    private PasswordEncoder pwEncoder ;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResultVO usersLogin(@RequestBody UsersVO uvo) {
        ResultVO rvo = new ResultVO();
        try {
            // 사용자 정보 조회
            UsersVO result = service.isExistUser(uvo.getU_id());
            if (result == null) {
                rvo.setSuccess(false);
                rvo.setMsg("존재하지 않는 ID 입니다.");
                return rvo;
            }else {
                // 비밀번호 검증받긴
                if (pwEncoder.matches(uvo.getU_pw(), result.getU_pw())) {

                    String token = jwtUtil.generateToken(uvo.getU_id());
                    
                }else {
                    rvo.setSuccess(false);
                    rvo.setMsg("비밀먼호가 일치하지 않습니다.");
                    return rvo;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return rvo;
    }
    
}
