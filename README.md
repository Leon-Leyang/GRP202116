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

#### Important Note: Do not change the file structure of this project.

### 1. Import SQL Database

+ Please make sure that you have **MySQL** installed. You could use the [MySQL Intallation Guide](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/) for reference.

+ To import the database, open the terminal in the same directory with the  [GRP2021.sql](./SQL/GRP2021.sql) file and execute following commands:

  ```
  // Template:
  $ mysql -u(yourUsername)    -p(yourPassword)    <  (yourDatabase.sql)
  
  // A real world example:
  $ mysql -uroot -p123456 < GRP2021.sql
  ```

  

### 2. Start the Flask Server

*_TODO: Build executable files for the flask server._*

+ You could use a preferred IDE ([PyCharm](https://www.jetbrains.com/pycharm/) Recommended) to import the source code under the [flask-server](./flask-server/) directory.
+ Inside the **Run Configurations**, add `--host=0.0.0.0 --port=6000` to the **Additional options**.
+ Start the **app.py** flask application.



### 3. Start the Spring Boot Backend

+ **Please Note: Import the [backend](./backend/) as a whold project for Maven management.**

+ You could use a preferred IDE ([**IntelliJ IDEA**](https://www.jetbrains.com/idea/) Recommended) to import the source code under the [backend](./backend/) directory.

+ After importing the source code, wait for the **Maven** decencies to be downloaded and resolved.

+ Then run the backend **in this order**:

  1. eureka-server

  2. sidecar-server

  3. consumer-server

* Should you encounter any problems, please close all three applications and try to restart them.



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


