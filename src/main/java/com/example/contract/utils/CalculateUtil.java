package com.example.contract.utils;

import org.springframework.util.ObjectUtils;

final public class CalculateUtil {

    private CalculateUtil() {
    }

    public static boolean isEmpty(Integer num) {
        return ObjectUtils.isEmpty(num) || num == 0;
    }

    public static void checkedEmpty(Integer num) {
        if (isEmpty(num)) {
            // todo : 예외 처리
            throw new NullPointerException();
        }
    }
}
