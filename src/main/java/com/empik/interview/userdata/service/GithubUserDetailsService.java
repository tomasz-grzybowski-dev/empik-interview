package com.empik.interview.userdata.service;

import com.empik.interview.userdata.converter.GithubUserDetailsDtoConverter;
import com.empik.interview.userdata.dto.UserDetailsDto;
import com.empik.interview.userdata.restclient.GithubClientFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubUserDetailsService {

    private final GithubClientFacade githubClientFacade;
    private final GithubUserDetailsDtoConverter githubUserDetailsDtoConverter;

    public UserDetailsDto getUserDetailsFromGithubApi(@NonNull String login) {
        return githubUserDetailsDtoConverter.convertDto(githubClientFacade.callGithubApi(login));
    }
}
