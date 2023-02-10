package com.empik.interview.userdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.empik.interview.exception.GithubServiceUnavailableException;
import com.empik.interview.exception.LoginNotFoundException;
import com.empik.interview.requestcounting.repository.RequestCountRepository;
import com.empik.interview.userdata.restclient.dto.GithubApiUserDetailsDto;
import com.empik.interview.userdata.util.InputProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GithubUserDataIntegrationTest {

    @Value("${external.api.github.users}")
    public String githubUserApiUrl;
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private RestTemplate restTemplate;

    @Autowired
    private RequestCountRepository requestCountRepository;

    @Test
    public void givenGithubLogin_whenGetUserDetails_thenFetchDetailsFromGithubAndCalculateValue() throws Exception {
        GithubApiUserDetailsDto githubApiUserDetailsDto =
            InputProvider.getInputDto("githubMockResources/octocat.json", GithubApiUserDetailsDto.class);
        doReturn(githubApiUserDetailsDto).when(restTemplate).getForObject(githubUserApiUrl + "octocat", GithubApiUserDetailsDto.class);

        mockMvc.perform(get("/users/octocat"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(583231)))
            .andExpect(jsonPath("$.login", is("octocat")))
            .andExpect(jsonPath("$.name", is("The Octocat")))
            .andExpect(jsonPath("$.type", is("User")))
            .andExpect(jsonPath("$.avatarUrl", is("https://avatars.githubusercontent.com/u/583231?v=4")))
            .andExpect(jsonPath("$.createdAt", is("2011-01-25T18:44:36Z")))
            .andExpect(jsonPath("$.calculations", is(0)));
    }

    @Test
    public void givenWrongGithubLogin_whenGetUserDetails_thenReturnNotFound() throws Exception {
        doThrow(LoginNotFoundException.class).when(restTemplate)
            .getForObject(githubUserApiUrl + "wrongLogin", GithubApiUserDetailsDto.class);

        mockMvc.perform(get("/users/wrongLogin")).andExpect(status().isNotFound());
    }

    @Test
    public void givenGithubServiceUnavailable_whenGetUserDetails_thenReturnNotFound() throws Exception {
        doThrow(GithubServiceUnavailableException.class).when(restTemplate)
            .getForObject(githubUserApiUrl + "octocat", GithubApiUserDetailsDto.class);

        mockMvc.perform(get("/users/octocat")).andExpect(status().isServiceUnavailable());
    }

    @Test
    public void givenGithubLogin_whenGetUserDetailsCallIsSuccessful_thenIncrementRequestCount() throws Exception {
        GithubApiUserDetailsDto githubApiUserDetailsDto =
            InputProvider.getInputDto("githubMockResources/octocat.json", GithubApiUserDetailsDto.class);
        doReturn(githubApiUserDetailsDto).when(restTemplate).getForObject(githubUserApiUrl + "octocat", GithubApiUserDetailsDto.class);

        mockMvc.perform(get("/users/octocat")).andExpect(status().isOk());
        mockMvc.perform(get("/users/octocat")).andExpect(status().isOk());

        assertEquals(2, requestCountRepository.findById("octocat").orElseThrow().getCount(),
            "Wrong request count number");
    }
}
