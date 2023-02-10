package com.empik.interview.userdata.service;

import com.empik.interview.exception.DivideByZeroExceptionException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    public static final int DEFAULT_SCALE = 10;
    private final static int constNumberA = 6;
    private final static int constNumberB = 2;

    public BigDecimal calculate(Integer followers, Integer publicRepos) {
        if (followers == 0) {
            throw new DivideByZeroExceptionException();
        }
        BigDecimal sumResult = BigDecimal.valueOf(constNumberB).add(BigDecimal.valueOf(publicRepos));
        return BigDecimal.valueOf(constNumberA).divide(BigDecimal.valueOf(followers).multiply(sumResult), DEFAULT_SCALE, RoundingMode.HALF_UP);
    }
}
