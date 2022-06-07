#Build stage
FROM openjdk:8-jdk-alpine AS build

ENV SBT_VERSION 1.6.2

RUN curl -L -o sbt-$SBT_VERSION.zip https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.zip
RUN unzip sbt-$SBT_VERSION.zip -d opt

WORKDIR /SparkApp
ADD . /SparkApp

RUN /ops/sbt/bin/sbt package

#Final stage
FROM openjdk:8-jdk-alpine

COPY --from=build /SparkApp/target/scala-2.11/learning_spark_scala_2.11-0.1.jar /app
WORKDIR /app

ENTRYPOINT ["java", "-jar", "learning_spark_scala_2.11-0.1.jar"]