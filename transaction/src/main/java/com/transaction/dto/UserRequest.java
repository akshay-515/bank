package com.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
}
