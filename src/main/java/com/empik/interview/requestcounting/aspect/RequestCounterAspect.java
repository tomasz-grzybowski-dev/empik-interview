package com.empik.interview.requestcounting.aspect;

import com.empik.interview.requestcounting.entity.LoginRequestCountEntity;
import com.empik.interview.requestcounting.repository.RequestCountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestCounterAspect {
    private final RequestCountRepository requestCountRepository;

    @Transactional
    @After("execution(* com.empik.interview.userdata.controller.GithubUserDetailsController.getUserDetails( ..))")
    public void incrementCountAfterProcessing(JoinPoint joinPoint) {
        String login = (String) joinPoint.getArgs()[0];

        LoginRequestCountEntity updatedLoginRequestCount = requestCountRepository.findById(login)
            .orElseGet(() -> {
                LoginRequestCountEntity loginRequestCountEntity = new LoginRequestCountEntity();
                loginRequestCountEntity.setLogin(login);
                return loginRequestCountEntity;
            });
        updatedLoginRequestCount.setCount(updatedLoginRequestCount.getCount() + 1);
        log.info("Incrementing request count fro login: {} to {}", login, updatedLoginRequestCount.getCount());
        requestCountRepository.save(updatedLoginRequestCount);
    }
}
