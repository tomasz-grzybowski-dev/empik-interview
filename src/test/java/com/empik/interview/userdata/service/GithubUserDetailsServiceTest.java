package com.empik.interview.userdata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empik.interview.userdata.converter.GithubUserDetailsDtoConverter;
import com.empik.interview.userdata.dto.UserDetailsDto;
import com.empik.interview.userdata.restclient.GithubClientFacade;
import com.empik.interview.userdata.restclient.dto.GithubApiUserDetailsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GithubUserDetailsServiceTest {

    @Mock
    private GithubClientFacade githubClientFacade;
    @Mock
    private GithubUserDetailsDtoConverter githubUserDetailsDtoConverter;
    @InjectMocks
    private GithubUserDetailsService githubUserDetailsService;

    @Test
    void testGetUserDetailsFromGithubApi() {
        String login = "test-user";
        GithubApiUserDetailsDto githubUserDto = GithubApiUserDetailsDto.builder().build();
        UserDetailsDto expectedUserDetailsDto = UserDetailsDto.builder().build();

        when(githubClientFacade.callGithubApi(eq(login))).thenReturn(githubUserDto);
        when(githubUserDetailsDtoConverter.convertDto(eq(githubUserDto))).thenReturn(expectedUserDetailsDto);

        UserDetailsDto actualUserDetailsDto = githubUserDetailsService.getUserDetailsFromGithubApi(login);

        assertEquals(expectedUserDetailsDto, actualUserDetailsDto);
    }
}
