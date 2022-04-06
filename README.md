# GRP202116

## Overview

This is the Software Engineering Group Project, ’Flexible Data Annotation Tool for Machine
Learning Techniques’. It aims to develop a web based software which facilitates annotation
process for machine learning techniques. 

It utilizes training data to teach machines to complete work as people, machine learning (ML)
techniques are in great demand in Artificial Intelligent applications. Training data are
those data annotated by humans. Thus, being able to annotate a large amount of data
is of concern to modern deep learning based techniques.



## Getting Started

### 1. Import SQL Database

+ Please make sure that you have **MySQL** installed. You could use the [MySQL Intallation Guide](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/) as reference.

+ To import the database, open the terminal in the same directory with the  [GRP2021.sql](./sql/GRP2021.sql) file and execute following commands:

  ```
  // Template:
  $ mysql -u(yourUsername)    -p(yourPassword)    <  (yourDatabase.sql)
  
  // A real world example:
  $ mysql -uroot -p123456 < GRP2021.sql
  ```

  

### 2. Start the Flask Server

TODO

### 3. Start the Spring Boot Backend

+ (*Recommended*) You could directly use the jar files under the releases to start the Spring Boot backend:

  Inside the directory which contains the jar files, open separate terminals and execute following commands sequentially:

  ```
  // Open the first terminal and execute:
  $ java -jar eureka-server-1.0-SNAPSHOT-exec.jar
  
  // After the above jar file has successfully started (when it displays "Completed initialization in * ms"), open a second terminal and execute:
  $ java -jar sidecar-server-1.0-SNAPSHOT-exec.jar
  
  // After the above jar file has successfully started (when it displays "Completed initialization in * ms"), open a third terminal and execute:
  $ java -jar consumer-server-1.0-SNAPSHOT-exec.jar
  ```

+ Or you could also use an preferred IDE to import the source code under the [backend](./backend/) directory.

+ **Please check** that you are running the backend **in this order**:

  + eureka-server

  + sidecar-server

  + consumer-server

* Should you encounter any problems, please close all three applications and start from the beginning.

### Frontend

+ _TODO: Build executable files for the frontend._
+ Inside the [frontend](./frontend/) directory, open the terminal and run:

```
## Project setup
npm install

### Compiles and hot-reloads for development
npm run serve

### Compiles and minifies for production
npm run build

### Lints and fixes files
npm run lint
```


