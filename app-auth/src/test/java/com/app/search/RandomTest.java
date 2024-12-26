package com.app.search;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomTest {

    @Test
    public void testGetRandomString() {
        int length = 8;
        String randomString = RandomUtil.randomString(length);

        // 验证生成的字符串长度是否为8
        assertEquals(length, randomString.length());

        // 验证生成的字符串是否不为空
        assertNotNull(randomString);

        // 可以进一步验证生成的字符串是否只包含字母和数字
        assertTrue(randomString.matches("[a-zA-Z0-9]+"));
    }
}
