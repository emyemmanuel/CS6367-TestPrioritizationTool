/*
 *  Copyright 2001-2011 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.joda.time;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.runner.*;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;

import test.pack.StatementCoverageData;

public class Listener extends RunListener {
	List<String> list = new ArrayList<String>();
	StringBuilder sb = new StringBuilder();
	long startTime;
	
	@Override
	public void testRunStarted(Description description) throws Exception
    {
		startTime = System.currentTimeMillis();
	}
	public void testStarted(Description description)
    {
		StatementCoverageData.testExecuted(description.getClassName(), description.getMethodName());
	}

	public void testRunFinished(Result result) throws ClassNotFoundException
    {
        List<String> fileOutput1 = StatementCoverageData.writeTotalIntoFile();
        startTime = System.currentTimeMillis();
        System.out.println("\n*******************************************");
        System.out.println("\nStarting Total Prioritization Test Execution at "+startTime+" ms");
        for (String item1 : fileOutput1)
        {
            Result result1 = org.junit.runner.JUnitCore.runClasses(Class.forName(item1));
            for (Failure failure : result1.getFailures()) {
                if(failure.toString().contains("warning")){}
                else
                    System.out.println("Test failed in : " + (System.currentTimeMillis() - startTime));
            }        }
        System.out.println("Ending Total Prioritization Test Execution");
        System.out.println("*******************************************");

		List<String> fileOutput2 = StatementCoverageData.writeAdditionalIntoFile();
        startTime = System.currentTimeMillis();
        System.out.println("\n*******************************************");
        System.out.println("\nStarting Additional Prioritization Test Execution at "+startTime+" ms");
        for (String item2 : fileOutput2)
        {
			Result result2 = org.junit.runner.JUnitCore.runClasses(Class.forName(item2));
            for (Failure failure : result2.getFailures()) {
                if(failure.toString().contains("warning")){}
                else
                    System.out.println("Test failed in : " + (System.currentTimeMillis() - startTime));
            }
        }
        System.out.println("Ending Additional Prioritization Test Execution");
        System.out.println("*******************************************");
	}

	@Override
	public void testFailure(Failure failure) throws Exception
    {
		System.out.println("Test failed in : " + (System.currentTimeMillis() - startTime));
	}
}
