## Students' professional/engineering practice management system ##

## Development environment setup ##

### XAMPP ###
* Download latest XAMPP from https://www.apachefriends.org/index.html
* Install it

### JDK 7 ###
* Download latest JDK 7 from http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
* Install it
* Environment variable
    * Set `JAVA_HOME` to the newly installed JDK 7 folder
    * Set `_JAVA_OPTIONS` to `"-XX:-UseSplitVerifier"`
### Play-2.2.3 ###
* Download Play-2.2.3 from http://www.playframework.com
* Unzip it
* Environment variable
    * Add `PLAY_HOME` to the unzipped folder of Play
    * Add `PLAY_HOME` to `PATH`

### Node.js ###
* Download latest Node.js from http://nodejs.org/download/
* Install it

### Less compiler ###
* Use the following command to install less compiler (it requires admin access to the OS)
```
npm install -g less
```

## Run the application ##

### Create a database ###
* Run the following command to create a database
```
CREATE DATABASE  `spepms` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

### Specify a different application.conf file ###

This step is optional

* Create a folder named `config` (already ignored by gitignore)
* Create a file named `application-dev.conf`, and make it start with
```
include "../conf/application.conf"
```
* Override the default configurations in application-dev.conf
* Start Play using the following command
```
play -Dconfig.file=config/application-dev.conf
```
