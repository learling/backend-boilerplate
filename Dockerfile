FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} springboot-backend.jar
ENTRYPOINT ["java","-jar","/springboot-backend.jar"]