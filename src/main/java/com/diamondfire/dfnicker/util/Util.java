package com.diamondfire.dfnicker.util;

import java.util.*;

public class Util {

    public static Deque<String> getUnicodeNumbers() {
        Deque<String> nums = new ArrayDeque<>();
        nums.add("\u0031\uFE0F\u20E3");
        nums.add("\u0032\uFE0F\u20E3");
        nums.add("\u0033\uFE0F\u20E3");
        nums.add("\u0034\uFE0F\u20E3");
        nums.add("\u0035\uFE0F\u20E3");
        nums.add("\u0036\uFE0F\u20E3");
        nums.add("\u0037\uFE0F\u20E3");
        nums.add("\u0038\uFE0F\u20E3");
        nums.add("\u0039\uFE0F\u20E3");
        nums.add("\uD83D\uDD1F");
        return nums;
    }

    public static int clamp(int num, int min, int max) {
        return Math.max(min, Math.min(num, max));
    }

}
