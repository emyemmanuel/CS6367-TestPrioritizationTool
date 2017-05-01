# CS6367-TestPrioritizationTool
Test Prioritization Tool
The test prioritization tool enables optimization to select and prioritize tests for applications. The tool developed includes the total and additional test prioritization strategies. 

Dependecies:
- ASM 5.0.3 for the compilation of JavaAgent.
- JUnit version 4.11

The example project on which the tool is tested is joda-time which can be found at https://github.com/JodaOrg/joda-time.git, acff94148b2110b95f7aeae6a1bdcafb756061f0

Steps to run the tool on joda-time.git is as follows

1. Update the pom.xml of the example project with the pom.xml uploaded in the repository (https://github.com/emyemmanuel/CS6367-TestPrioritizationTool/blob/master/pom.xml) (see below for changes made in pom.xml)

2. Add Listener.java (https://github.com/emyemmanuel/CS6367-TestPrioritizationTool/blob/master/Listener.java) to the example project so that the path of listener is  ~/joda-time/src/test/java/org/joda/time/Listener.java

3. Add the test-agent.jar to the example project so that the path is ~/joda-time/test-agent.jar (Steps to make jar is mentioned below)

4. Once the Listener.java and test-agent.jar are added to joda-time use the command $ mvn test  to run the tool. Make sure the Maven path and environment variables are aready set before running this command

5. The output will first run the TestClasses without any prioritization, followed by total test prioritization and finally additional test prioritization of the TestClasses



Here are the changes that should be made to the tool while running on any other example project.

1. Updates made in pom.xml

The changes or additions made in the pom.xml are as follows:
-- First Change
<plugin>
	          <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-surefire-plugin</artifactId>
	          <configuration>
	            <includes>
	              <include>**/TestAllPackages.java</include>
	            </includes>
	            <!--argLine>-Djava.security.manager -Djava.security.policy=${basedir}/src/test/resources/java.policy</argLine-->
	 <argLine>-javaagent:test-agent.jar</argLine>     
	<properties>
	        <property>
	            <name>listener</name>
	            <value>org.joda.time.Listener</value>
	         </property>
	      </properties>
	 </configuration>
</plugin>

--Second Change
<plugin>
         <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-assembly-plugin</artifactId>
         <configuration>
           <attach>false</attach>
          <descriptors>
             <descriptor>src/main/assembly/dist.xml</descriptor>
           </descriptors>
          <tarLongFileMode>gnu</tarLongFileMode>
	          </configuration>
        <executions>
           <execution>
            <id>make-assembly</id>
             <phase>deploy</phase>
            <goals>
              <goal>single</goal>
             </goals>
           </execution>
         </executions>
</plugin>


--Third Change
<dependency>
	        <groupId>junit</groupId>
	        <artifactId>junit</artifactId>
      <version>4.11</version>
	        <scope>test</scope>
	      </dependency>
     <dependency>
             <groupId>org.ow2.asm</groupId>
            <artifactId>asm</artifactId>
            <version>5.0.3</version>
          </dependency>
 <dependency>
 <artifactId>test.pack</artifactId>
 <groupId>test-agent</groupId>
 <version>1.0</version>
 <scope>system</scope>
<systemPath>${basedir}/test-agent.jar</systemPath>
</dependency>

2. Changes in java file 
1. Update the class path in MyClassFileTransformer.java and TestJavaAgent.java from "org/joda/time" to the class path of the example project
2. Update the package details in Listener.java to the package details of he example project

3. Steps to make a jar file

1. Compile all the java files using command 
	javac -cp ".;../lib/asm-all-5.0.3.jar" *.java
2. Consolidate all the class files while mantaining the folder structure in this case 
	test->pack
3. Create manifest.txt containing 
	Premain-Class: test.pack.Agent
4. Run the following command 
	jar -cvfm test-agent.jar manifest.txt test


Steps to install maven on mac
http://toolsqa.com/java/maven/how-to-install-maven-on-mac/
