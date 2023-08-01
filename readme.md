## VESTA (Vulnerability Exploit-based Software Testing Auto-Generator)

#### 1. Overview

This is the code package of VESTA, which relies on the vulnerability exploit code of third-party libraries to analyze whether the projects have exploitable library vulnerabilities. 

This work is submitted to ICSE2024, and this is the abstract of our paper.

> In software development, developers extensively utilize third-party libraries to avoid implementing existing functionalities. When a new third-party library vulnerability is disclosed, project maintainers need to determine whether their projects are affected by the vulnerability, which requires developers to invest substantial effort in assessment. However, existing tools face a series of issues: static analysis tools produce false alarms, dynamic analysis tools require existing tests and test generation tools have low success rates when facing complex vulnerabilities.
>
> Vulnerability exploits, as code snippets provided for reproducing vulnerabilities after disclosure, contain a wealth of vulnerability-related information. This study proposes a new method based on vulnerability exploits, called VESTA (Vulnerability Exploit-based Software Testing Auto-Generator), which provides vulnerability exploit tests as the basis for developers to decide whether to update dependencies. VESTA extends the search-based test generation methods by adding a migration step, ensuring the similarity between the generated test and the vulnerability exploit, which increases the likelihood of detecting potential library vulnerabilities in a project.
>
> We perform experiments on 30 vulnerabilities disclosed in the past five years, involving 60 vulnerability-project pairs, and compare the experimental results with the baseline method, TRANSFER. The success rate of VESTA is 71.7\% which is a 53.4\% improvement over TRANSFER in verifying the effectiveness of exploitable vulnerabilities. 

#### 2. Approach





#### 3. Setup



#### 4. Example





