We will provide a docker image soon.


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

##### /vesta

Our main method, actually, just connects every part of our method.

##### /resource

**Due to anonymous needs, we don't upload this folder.**
This folder put the target projects. For example, a project named 'testProject' related to vulnerability 'testVul', should put at /resource/testVul/testProject.

##### /result

**Due to anonymous needs, we don't upload this folder.**
This folder put the run production of our method. For example, a project named 'testProject' related to vulnerability 'testVul', should create a folder named  /result/testVul/testProject.



#### 3. Setup

We provide a complete replication package in the following link.
