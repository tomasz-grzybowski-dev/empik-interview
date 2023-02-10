package com.empik.interview.requestcounting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.empik.interview.requestcounting.aspect.RequestCounterAspect;
import com.empik.interview.requestcounting.entity.LoginRequestCountEntity;
import com.empik.interview.requestcounting.repository.RequestCountRepository;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RequestCounterAspectTest {

    @Mock
    private RequestCountRepository requestCountRepository;

    @Mock
    private JoinPoint joinPoint;

    private RequestCounterAspect requestCounterAspect;

    @BeforeEach
    public void setUp() {
        requestCounterAspect = new RequestCounterAspect(requestCountRepository);
    }

    @Test
    public void incrementCountAfterProcessing_incrementsCount() {
        String login = "testLogin";
        LoginRequestCountEntity loginRequestCountEntity = new LoginRequestCountEntity();
        loginRequestCountEntity.setLogin(login);
        loginRequestCountEntity.setCount(1);

        when(joinPoint.getArgs()).thenReturn(new Object[] {login});
        when(requestCountRepository.findById(login)).thenReturn(java.util.Optional.of(loginRequestCountEntity));

        requestCounterAspect.incrementCountAfterProcessing(joinPoint);

        verify(requestCountRepository).save(loginRequestCountEntity);
        assertEquals(2, loginRequestCountEntity.getCount());
    }
}