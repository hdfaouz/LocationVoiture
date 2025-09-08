FROM openjdk:21-jdk-slim

ADD target/LocatioVoitures-0.0.1-SNAPSHOT.jar LocatioVoitures-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "LocatioVoitures-0.0.1-SNAPSHOT.jar"]
