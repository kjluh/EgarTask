FROM openjdk:20
ADD /target/EgarTask-0.0.1-SNAPSHOT.jar backand.jar
ENTRYPOINT ["java", "-jar", "backand.jar"]