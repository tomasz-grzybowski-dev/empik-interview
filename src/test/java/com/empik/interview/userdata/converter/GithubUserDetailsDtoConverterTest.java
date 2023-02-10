package com.empik.interview.userdata.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.empik.interview.userdata.dto.UserDetailsDto;
import com.empik.interview.userdata.restclient.dto.GithubApiUserDetailsDto;
import com.empik.interview.userdata.service.CalculationService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GithubUserDetailsDtoConverterTest {
    private GithubUserDetailsDtoConverter converter;

    @Mock
    private CalculationService calculationService;

    @BeforeEach
    public void setUp() {
        converter = new GithubUserDetailsDtoConverter(calculationService);
    }

    @Test
    public void convertDto_shouldReturnUserDetailsDto() {
        // given
        GithubApiUserDetailsDto apiUserDetailsDto = GithubApiUserDetailsDto.builder()
            .id(1)
            .name("John Doe")
            .avatarUrl("https://avatar.url")
            .login("johndoe")
            .createdAt("2020-01-01T00:00:00Z")
            .type("User")
            .followers(100)
            .publicRepos(200)
            .build();
        when(calculationService.calculate(100, 200)).thenReturn(BigDecimal.valueOf(300));

        // when
        UserDetailsDto userDetailsDto = converter.convertDto(apiUserDetailsDto);

        // then
        assertEquals(1, userDetailsDto.getId());
        assertEquals("John Doe", userDetailsDto.getName());
        assertEquals("https://avatar.url", userDetailsDto.getAvatarUrl());
        assertEquals("johndoe", userDetailsDto.getLogin());
        assertEquals("2020-01-01T00:00:00Z", userDetailsDto.getCreatedAt());
        assertEquals("User", userDetailsDto.getType());
        assertEquals(BigDecimal.valueOf(300), userDetailsDto.getCalculations());
    }
}