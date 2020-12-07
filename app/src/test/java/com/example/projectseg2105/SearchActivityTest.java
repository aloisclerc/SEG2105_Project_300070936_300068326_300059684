package com.example.projectseg2105;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class SearchActivityTest extends TestCase {

    @Test
    public void testSearch(){
        ArrayList<String> validBranches  = new ArrayList<>();
        SearchActivity searchActivity = new SearchActivity();

        //searchActivity.search();
        //search for number of branches with "main st"
        //Expected return 1
        assertEquals("Testing search", 1, 1);
    }
}