package com.empik.interview.userdata.restclient;

import com.empik.interview.userdata.restclient.dto.GithubApiUserDetailsDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubClientFacade {

    private final RestTemplate restTemplate;
    @Value("${external.api.github.users}")
    public String githubUserApiUrl;

    public GithubApiUserDetailsDto callGithubApi(@NonNull String login) {
        return restTemplate.getForObject(githubUserApiUrl + login, GithubApiUserDetailsDto.class);
    }
}
