package com.shaon.UnitTestPOne;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CalculatorTest {


    Calculator calculator;

    @Before
    public void setUp(){
        calculator = new Calculator();
    }


    @Test
    public void testMultiply(){
        assertEquals(20,calculator.multiply(4,5));
    }
}
