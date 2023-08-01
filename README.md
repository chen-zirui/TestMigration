## VESTA (Vulnerability Exploit-based Software Testing Auto-Generator)



#### 1. Overview

This is the code package of VESTA, which relies on the vulnerability exploit code of third-party libraries to analyze whether the projects have exploitable library vulnerabilities. 

This work is submitted to ICSE2024, and this is the abstract of our paper.

> In software development, developers extensively utilize third-party libraries to avoid implementing existing functionalities. When a new third-party library vulnerability is disclosed, project maintainers need to determine whether their projects are affected by the vulnerability, which requires developers to invest substantial effort in assessment. However, existing tools face a series of issues: static analysis tools produce false alarms, dynamic analysis tools require existing tests and test generation tools have low success rates when facing complex vulnerabilities.
>
> Vulnerability exploits, as code snippets provided for reproducing vulnerabilities after disclosure, contain a wealth of vulnerability-related information. This study proposes a new method based on vulnerability exploits, called VESTA (Vulnerability Exploit-based Software Testing Auto-Generator), which provides vulnerability exploit tests as the basis for developers to decide whether to update dependencies. VESTA extends the search-based test generation methods by adding a migration step, ensuring the similarity between the generated test and the vulnerability exploit, which increases the likelihood of detecting potential library vulnerabilities in a project.
>
> We perform experiments on 30 vulnerabilities disclosed in the past five years, involving 60 vulnerability-project pairs, and compare the experimental results with the baseline method, TRANSFER. The success rate of VESTA is 71.7\% which is a 53.4\% improvement over TRANSFER in verifying the effectiveness of exploitable vulnerabilities. 



#### 2. File

##### /code

In this folder, we save our tools project like Evosuite(After modification), our remote server, insturmentor. You should also clone https://github.com/gousiosg/java-callgraph to get static analysis tools (code/java-callgraph/target/javacg-0.1-SNAPSHOT-static.jar).

##### /document

We put our project list here. To detect vulnerabilities in the project, you should download the specific version project and put it in to target folder as our readme. We also put the setup file (info.txt, VESTA.sh) of the projects.

##### /files

There are some files used by the exploits.

##### /main

Our main method, actually, just connects every part of our method.

##### /resource

**Due to anonymous needs, we don't upload this folder.**
This folder put the target projects. For example, a project named 'testProject' related to vulnerability 'testVul', should put at /resource/testVul/testProject.

##### /result

**Due to anonymous needs, we don't upload this folder.**
This folder put the run production of our method. For example, a project named 'testProject' related to vulnerability 'testVul', should create a folder named  /result/testVul/testProject.

##### /setup.sh

**Due to anonymous needs, we don't upload this file.**
This file puts some information about the environment of the computer, you should fill it as follows and put some information.

```
export PROJECT_DIR=/projectDir/TestMigration
export PYTHON=/usr/bin/python3
export EVOSUITE="java -jar $PROJECT_DIR/code/generator/master/target/evosuite-master-1.0.4.jar -Dsandbox=false"
```



#### 3. Setup

> We will consider making an automatic setup tools in our future work!

##### Step1

Fill in `setup.sh` and run the following command.

```
source ./setup.sh
```

##### Step2

Download the target project and put it into the correct folder.

##### Step3

Make a `CLASSPATH.txt` file in the project folder and run `mvn dependency:build-classpath` to get the class path, copy it into this file, and we also need the `target/classes`path,`evosuite-standalone-runtime-1.0.4.jar`path,`junit-4.12.jar`path, and `amcrest-core-1.3.jar`path.

##### Step4

> Todo: We will replace this step with automatic methods.

Make a info.txt file in the project folder, it should be constructed as follows:

```
<void/>
1
25

```

line1 put the pocValue, line2 put the entry parameter place, line3 put the parameter load type (you can run VESTA to get this value).

##### Step5

Make a VESTA.sh,  it should be constructed as follows:

```
export NOW_DIR=/projectDir/resource/CVE-2017-7957/cqrs-lottery-master/cqrs-example
$PYTHON $PROJECT_DIR/main/VESTA.py CVE-2017-7957 cqrs-lottery-master cqrs-example  "com.thoughtworks.xstream.XStream:fromXML(java.lang.String)"  cqrs-example-1.0-SNAPSHOT.war fatal
```

line1 means the project dir, line2 means 1. main function place 2. CVE Number 3. Project name 4. Module name (child project dir) 5. vulnerable function 6. Jar/war after compiling 7. Vulnerability show

##### Step6

run VESTA.sh, the console will log the result.

A successful result is as follows:

```
PocValue is <void/>

Reproduce!!! Looking at result.txt for more details
Total time:  19.72716522216797  s
```

We will give PocValue and time, the generated test case is in the corresponding project folder.



#### 4. Bugs and Solves

##### Problem1: unable to load classpath

Solution: remove it from Classpath.txt, we will consider updating Evosuite version to avoid this problem.

##### Problem2: manual check is needed

Solution: The bug trigger condition is marked as a manual check, please look into the result.txt for more information.

##### Problem3: file or folder is not found

Solution:  Look into part 2 for the information about the lacking folder.
