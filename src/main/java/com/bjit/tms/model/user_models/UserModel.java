package com.bjit.tms.model.user_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
//    private Integer userId;
    private String username;
    private String password;
    private String role;
}
