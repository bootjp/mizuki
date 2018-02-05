FROM java:openjdk-8-jre-alpine

EXPOSE 4567

COPY ./build/libs/spark-sample-1.0-SNAPSHOT.jar /app/

CMD ["java", "-jar", "/app/spark-sample-1.0-SNAPSHOT.jar"]
