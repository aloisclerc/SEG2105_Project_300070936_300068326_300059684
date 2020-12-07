package com.example.projectseg2105;

import junit.framework.TestCase;

import org.junit.Test;

public class RateDialogTest extends TestCase {
    @Test
    public void testRate(){
        int rate = 5;
        assertEquals("Testing rating", 5, rate);
    }
    @Test
    public void testComment(){
        String comment = "this is good";
        assertEquals("Testing comment","this is good" ,comment);
    }

}