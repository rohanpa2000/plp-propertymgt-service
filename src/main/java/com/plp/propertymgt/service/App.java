package com.plp.propertymgt.service;

import java.text.ParseException;

import org.wso2.msf4j.MicroservicesRunner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException
    {
        new MicroservicesRunner(9022)
        .deploy(new BookingService())
        .start();
     }
}
