package com.empik.interview.userdata.controller;

import com.empik.interview.exception.BadGithubRequestException;
import com.empik.interview.exception.DivideByZeroExceptionException;
import com.empik.interview.exception.GithubServiceUnavailableException;
import com.empik.interview.exception.LoginNotFoundException;
import com.empik.interview.exception.UnknownGithubResponseException;
import com.empik.interview.userdata.dto.UserDetailsDto;
import com.empik.interview.userdata.service.GithubUserDetailsService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GithubUserDetailsController {

    private final GithubUserDetailsService githubUserDetailsService;

    @GetMapping("/users/{login}")
    public UserDetailsDto getUserDetails(@PathVariable String login) {
        log.debug("Start processing get user details service for login: {}", login);
        return githubUserDetailsService.getUserDetailsFromGithubApi(login);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({BadGithubRequestException.class, UnknownGithubResponseException.class})
    public Map<String, String> handleException() {
        return Map.of("message", "Unexpected server error");
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(GithubServiceUnavailableException.class)
    public Map<String, String> handleGithubServerError() {
        return Map.of("message", "Github Service is not available");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LoginNotFoundException.class)
    public Map<String, String> handleGithubLoginNotFound() {
        return Map.of("message", "Github login is incorrect");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DivideByZeroExceptionException.class)
    public Map<String, String> handleDivideByZeroExceptionException() {
        return Map.of("message", "Calculation error due to division of 0 - number of followers");
    }
}
