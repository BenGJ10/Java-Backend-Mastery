package com.bengregory;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws LifecycleException {
        System.out.println( "Hello World!" );
        Tomcat tomcat = new Tomcat();


        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext("", docBase);

        Tomcat.addServlet(context, "HelloServlet", new Servlet());
        context.addServletMappingDecoded("/hello", "HelloServlet");

        tomcat.start();
        tomcat.getServer().await();

    }
}
