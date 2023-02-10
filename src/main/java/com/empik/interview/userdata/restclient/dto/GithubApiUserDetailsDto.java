package com.empik.interview.userdata.restclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class GithubApiUserDetailsDto {
    private Integer id;
    private String login;
    private String name;
    private String type;
    private Integer followers;

    @JsonProperty("public_repos")
    private Integer publicRepos;
    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("created_at")
    private String createdAt;


}
