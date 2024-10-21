package com.descomplica.FrameBlog.dto;

import com.descomplica.FrameBlog.enums.RoleEnum;

public class UserUpdateRequestDTO {
    private Long id;

    private String name;

    private String email;

    private String password;

    private RoleEnum privateRollEnum;

}
