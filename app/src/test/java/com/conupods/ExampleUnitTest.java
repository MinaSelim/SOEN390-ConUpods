package com.conupods;

import com.conupods.OutdoorMaps.View.ClassToTry;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int ans = ClassToTry.add(2,2);
        System.out.println("");
        assertEquals(4, ans);
    }


}