package com.empik.interview.userdata.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UserDetailsDto {
    private Integer id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private BigDecimal calculations;
}
