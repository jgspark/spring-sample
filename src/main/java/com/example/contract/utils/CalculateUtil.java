package com.example.contract.utils;

import com.example.contract.config.exception.DataNotFoundException;
import org.springframework.util.ObjectUtils;

final public class CalculateUtil {

    private CalculateUtil() {
    }

    public static boolean isEmpty(Integer num) {
        return ObjectUtils.isEmpty(num) || num == 0;
    }

    public static void checkedEmpty(Integer num) {
        if (isEmpty(num)) {
            throw new DataNotFoundException("empty data");
        }
    }
}
