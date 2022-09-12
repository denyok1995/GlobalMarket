FROM openjdk:11
ARG JAR_FILE=build/libs/coupang-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
SHELL ["/bin/bash", "-c"]
EXPOSE 80/tcp
ENTRYPOINT {"java", "-jar", "/app.jar"}