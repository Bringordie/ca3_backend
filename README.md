## CA3

[![Build Status](https://travis-ci.com/Bringordie/ca3_backend.svg?branch=master)](https://travis-ci.com/Bringordie/ca3_backend)

**Opsætningning af pipeline**

1) Clone dette projekt.

2) I netbeans gå ind i .pom filen og skift linjen med remote.server til at peje på dit domæne. 

3) På din droplet gå ind i opt/tomcat/bin/setenv.sh og skift CONNECTION_STR til at peje på dit nuværende projekt.

4) Husk at restarte tomcat efter ændringer, brug commanden "sudo service tomcat restart".

5) På Travis find dit repository, gå ind i settings og lav to environment variables REMOTE_USER og REMOTE_PW (du kan se hvilke værdier de skal have på din droplet i filen /opt/tomcat/conf/tomcat-users.xml).

6) På travis tryk på knappen der viser status på dit build, vælg formatet markdown, kopier det der står i result og paste det ind øverst i din readme fil på github.

