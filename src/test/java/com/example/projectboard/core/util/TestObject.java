package com.example.projectboard.core.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestObject {

    private final String str;
    private final String listStr1;
    private final String listStr2;
    private final String nullStr;
    private final Integer number;
    private final Double floatingNumber;
    private final Boolean bool;
    private final BigDecimal bigDecimal;
    private final FormDataEncoderTest.TestEnum testEnum;

    public TestObject(String str, String listStr1, String listStr2, String nullStr, Integer number, Double floatingNumber, Boolean bool, BigDecimal bigDecimal, FormDataEncoderTest.TestEnum testEnum) {
        this.str = str;
        this.listStr1 = listStr1;
        this.listStr2 = listStr2;
        this.nullStr = nullStr;
        this.number = number;
        this.floatingNumber = floatingNumber;
        this.bool = bool;
        this.bigDecimal = bigDecimal;
        this.testEnum = testEnum;
    }

    public TestObject of(String str, String listStr1, String listStr2, String nullStr, Integer number, Double floatingNumber, Boolean bool, BigDecimal bigDecimal, FormDataEncoderTest.TestEnum testEnum) {
        return new TestObject(str, listStr1, listStr2, nullStr, number, floatingNumber, bool, bigDecimal, testEnum);
    }
}
