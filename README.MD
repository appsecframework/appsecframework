# Application Security Pipeline Framework

## Requirements
- Ubuntu 18.04 or later
- Latest version of Docker (https://docs.docker.com/install/linux/docker-ce/ubuntu/) 
- Latest version of Docker Compose (https://docs.docker.com/compose/install/)
- Latest version of Git (https://www.atlassian.com/git/tutorials/install-git)
- Java JRE 8
- Internet Connection

## Installation Steps
1. Clone Repository of Application Security Pipeline Framework to users computer

```
git clone https://github.com/appsecframework/appsecframework.git
```

2. Navigate to the directory of the cloned repository and run "setup.bash" for initial setup, provide parameters as asked

```
cd appsecframework
./setup.bash
```

3. Use Docker Compose to start the required containers of the framework

```
docker-compose up
```
	
4. Wait until Jenkins and DefectDojo are up and running at http://localhost:8080/ and http://localhost:8000/ respectively

5. Start the Java application using Gradlew

```
./gradlew run
```
	
6. Wait until the Java application UI windows appear on the screen, meaning it is ready to use
