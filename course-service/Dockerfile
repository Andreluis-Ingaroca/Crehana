FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} course-service.jar

ENTRYPOINT ["java","-jar","/course-service.jar"]