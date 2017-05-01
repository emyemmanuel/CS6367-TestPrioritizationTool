# CS6367-TestPrioritizationTool
Test Prioritization Tool
The test prioritization tool enables optimization to select and prioritize tests for applications. The tool developed includes the total and additional test prioritization strategies.

The example project on which the tool is tested

https://github.com/apache/commons-dbutils.git, 633749db5b0fd25b9a3ca133e7496a353de4fd5d

https://github.com/JodaOrg/joda-time.git, acff94148b2110b95f7aeae6a1bdcafb756061f0

Steps to run the tool on joda-time.git is as follows

1. Update the pom.xml with the pom.xml uploaded in the repository (see below for changes made in pom.xml)

2. Add Listener.java to the example project so that the path of listener is  ~/joda-time/src/test/java/org/joda/time/Listener.java

3. Add the test-agent.jar to the example project so that the path is ~/joda-time/test-agent.jar (Steps to make jar is mentioned below)

4. Once the Listener.java and test-agent.jar are added to joda-time use the command $ mvn test  to run the tool. Make sure the Maven path and environment variables are aready set before running this command

5. The output will first run the TestClasses without any prioritization, followed by total test prioritization and finally additional test prioritization of the TestClasses





Updates made in pom.xml

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
</dependencies>

Steps to make a jar file
