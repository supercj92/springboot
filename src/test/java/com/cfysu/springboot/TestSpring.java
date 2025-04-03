package com.cfysu.springboot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * @Author canglong
 * @Date 2024/9/11
 */
public class TestSpring {
    @Test
    public void testDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
        Date parse = format.parse("2024/9/10/11:00");
        System.out.println(parse.getTime());
        parse = format.parse("2024/9/10/17:00");
        System.out.println(parse.getTime());
    }
}
