package com.empik.interview.requestcounting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "login_request_count")
public class LoginRequestCountEntity {

    private Integer count = 0;
    @Id
    private String login;
}
