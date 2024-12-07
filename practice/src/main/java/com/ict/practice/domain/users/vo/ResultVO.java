package com.ict.practice.domain.users.vo;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResultVO {
    private boolean success;
    private Object data;
    private String token;
    private String msg;
    private UserDetails userDetails;



}
