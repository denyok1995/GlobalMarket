FROM openjdk:11
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} coupang.jar
ENTRYPOINT {"java", "-jar", "/coupang.jar"}