package com.yuu.recruit.utils;

import java.util.Random;

/**
 * @author by yuu
 * @Classname IDUtil
 * @Date 2019/10/14 8:42
 * @see com.yuu.recruit.utils
 */
public class IDUtil {

    /**
     * 随机 id 生成
     *
     * @return
     */
    public static Long getRandomId() {
        Long millis = System.currentTimeMillis();
        // 加上两位随机数
        Random random = new Random();
        int end = random.nextInt(99);
        // 如果不足两位前面补 0
        String str = millis + String.format("%02d", end);
        Long id = new Long(str);
        return id;
    }
}
