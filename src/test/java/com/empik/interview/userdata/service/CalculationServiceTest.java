package com.empik.interview.userdata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.empik.interview.exception.DivideByZeroExceptionException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

public class CalculationServiceTest {

    private final CalculationService calculationService = new CalculationService();

    @Test
    public void testCalculateWithValidInputs() {
        BigDecimal result = calculationService.calculate(4, 5);
        BigDecimal expectedResult = BigDecimal.valueOf(6).divide(BigDecimal.valueOf(4 * (2 + 5)), CalculationService.DEFAULT_SCALE, RoundingMode.HALF_UP);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCalculateWithFollowersZero() {
        assertThrows(DivideByZeroExceptionException.class, () -> calculationService.calculate(0, 5));
    }

    @Test
    public void testCalculateWithPublicReposZero() {
        BigDecimal result = calculationService.calculate(4, 0);
        BigDecimal expectedResult = BigDecimal.valueOf(6).divide(BigDecimal.valueOf(4 * 2), CalculationService.DEFAULT_SCALE, RoundingMode.HALF_UP);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCalculateWithBothInputsZero() {
        assertThrows(DivideByZeroExceptionException.class, () -> calculationService.calculate(0, 0));
    }
}
