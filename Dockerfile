FROM openjdk:8-alpine

COPY target/house-music.jar .

RUN mkdir /config

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.config.location=/config/config.yml", "-jar", "./house-music.jar"]
