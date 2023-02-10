package com.empik.interview.userdata.converter;

import com.empik.interview.userdata.dto.UserDetailsDto;
import com.empik.interview.userdata.restclient.dto.GithubApiUserDetailsDto;
import com.empik.interview.userdata.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubUserDetailsDtoConverter {
    private final CalculationService calculationService;

    public UserDetailsDto convertDto(GithubApiUserDetailsDto githubApiUserDetailsDto) {
        return UserDetailsDto.builder()
            .id(githubApiUserDetailsDto.getId())
            .name(githubApiUserDetailsDto.getName())
            .avatarUrl(githubApiUserDetailsDto.getAvatarUrl())
            .login(githubApiUserDetailsDto.getLogin())
            .createdAt(githubApiUserDetailsDto.getCreatedAt())
            .type(githubApiUserDetailsDto.getType())
            .calculations(calculationService.calculate(githubApiUserDetailsDto.getFollowers(),
                githubApiUserDetailsDto.getPublicRepos()))
            .build();
    }
}
