package com.spring.common.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAuth {
    private String name;
    private String email;
}