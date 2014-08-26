## Students' professional/engineering practice management system ##

## Proxy public repositories ##

**Create file $HOME/.sbt/repositories with the following content**
```text
[repositories]
  local
  proxy-ivy: http://115.28.15.44:8080/nexus/content/groups/proxy-ivy/, [organization]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext]
  proxy-maven: http://115.28.15.44:8080/nexus/content/groups/proxy-maven/
  kernelogic-public: http://115.28.15.44:8080/nexus/content/groups/public/
```

**Create file $HOME/.sbt/.credentials with the following content**
```text
realm=Sonatype Nexus Repository Manager
host=115.28.15.44
user=public
password=ASK PETER FU FOR THE PASSWORD
```

**Create file $HOME/.sbt/0.13/plugins/credentials.sbt with the following content**
```text
credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
```

**Use param -Dsbt.override.build.repos=true to start play console**

This step might be optional.
```text
play -Dsbt.override.build.repos=true
```

**Trouble shooting**

Sometimes it fails to download the artifacts when proxy is on and HTTP authentication is needed by the proxy repository.
```text
        tried http://115.28.15.44:8080/nexus/content/groups/proxy-ivy/com.typesafe.activator/activator-launcher/1.2.2/ivys/ivy.xml
try to get credentials for: Sonatype Nexus Repository Manager@115.28.15.44
authentication: k='Sonatype Nexus Repository Manager@115.28.15.44' c='null'
HTTP response status: 401 url=http://115.28.15.44:8080/nexus/content/groups/proxy-ivy/com.typesafe.activator/activator-launcher/1.2.2/ivys/ivy.xml
CLIENT ERROR: Unauthorized url=http://115.28.15.44:8080/nexus/content/groups/proxy-ivy/com.typesafe.activator/activator-launcher/1.2.2/ivys/ivy.xml
```

Workaround: run the following command before run `play`
* Windows: `SET SBT_CREDENTIALS=%HOMEPATH%\.sbt\.credentials`
* Mac OS: `export SBT_CREDENTIALS=~/.sbt/.credentials`

## Development environment setup ##

### XAMPP ###
* Download latest XAMPP from https://www.apachefriends.org/index.html
* Install it

### JDK 7 ###
* Download latest JDK 7 from http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
* Install it
* Environment variable
    * Set `JAVA_HOME` to the newly installed JDK 7 folder

### Eclipse 4.3.2 ###

Scala IDE doesn't fully support Eclipse 4.4 (Luna/Cocoa), in order to make all Play related feature functional, please use Eclipse 4.3 (Kepler). STS doesn't provide a bundle installer for previous version of Eclipse, so STS update site archive should be used in this case.

* Download Eclipse Standard 4.3.2 from http://www.eclipse.org/downloads/packages/release/Kepler/SR2
* Download Spring Tool Suite update site archive for Eclipse 4.3.2 from http://spring.io/tools/sts/all
* Install Spring Tool Suite in Eclipse
* Install Scala IDE from update site http://download.scala-ide.org/sdk/helium/e38/scala210/stable/site
** Check Scala IDE for Eclipse > Scala IDE for Eclipse
** Check Scala IDE plugins (incubation) > Play2 support in Scala IDE 

### Play-2.2.3 ###
* Download Play-2.2.3 from http://www.playframework.com
* Unzip it
* Environment variable
    * Add `PLAY_HOME` to the unzipped folder of Play
    * Add `PLAY_HOME` to `PATH`

## Run the application ##

### Create a database ###
* Run the following command to create a database
```
CREATE DATABASE  `spepms` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

### Specify a different application.conf file ###

This step is optional

* Create a folder named `config` (already ignored by `.gitignore`)
* Create a file named `application-dev.conf`, and make it start with
```
include "../conf/application.conf"
```
* Override the default configurations in application-dev.conf
* Start Play using the following command
```
play -Dconfig.file=config/application-dev.conf
```
