package com.aiq.integration;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class LoanDataPointParserTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LoanDataPointParserTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LoanDataPointParser.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testLoanDataPointParser()
    {
        assertTrue( true );
    }
}
