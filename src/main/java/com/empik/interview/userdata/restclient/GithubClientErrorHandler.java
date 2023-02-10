package com.empik.interview.userdata.restclient;

import com.empik.interview.exception.BadGithubRequestException;
import com.empik.interview.exception.GithubServiceUnavailableException;
import com.empik.interview.exception.LoginNotFoundException;
import com.empik.interview.exception.UnknownGithubResponseException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

@Slf4j
public class GithubClientErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.debug("Request to Github failed. Status code {}", response.getStatusCode().value());
        if (response.getStatusCode().is5xxServerError()) {
            throw new GithubServiceUnavailableException();
        }
        if (response.getStatusCode().is4xxClientError()) {
            if (HttpStatus.NOT_FOUND.value() == response.getStatusCode().value()) {
                throw new LoginNotFoundException();
            }
            throw new BadGithubRequestException();
        }
        throw new UnknownGithubResponseException();
    }
}
