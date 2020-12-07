package com.example.projectseg2105;

import junit.framework.TestCase;

public class FormActivityTest extends TestCase {

    public void testForm(){
        String firstName = "John";
        String lastName = "Doe";
        String DOB = "04/12/1957";
        String appointment = "08/12/2020";
        String address = "123 Main St";
        String answer = "G2";

        assertEquals("Testing Service Submission ", "John", firstName);
        assertEquals("Testing Service Submission ", "Doe", lastName);
        assertEquals("Testing Service Submission ", "04/12/1957", DOB);
        assertEquals("Testing Service Submission ", "08/12/2020", appointment);
        assertEquals("Testing Service Submission ", "123 Main St", address);
        assertEquals("Testing Service Submission ", "G2", answer);
    }

}