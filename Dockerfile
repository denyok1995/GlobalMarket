FROM openjdk:11
ARG JAR_FILE=./build/libs/coupang-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} coupang.jar
ENTRYPOINT {"java", "-jar", "/coupang.jar"}