# syntax=docker/dockerfile:1
FROM amazoncorretto:17.0.5-al2022-RC-headless

# install app
COPY ./target/social-media-0.0.1-SNAPSHOT-spring-boot.jar .

# final configuration
EXPOSE 8081

# start app
CMD java -jar social-media-0.0.1-SNAPSHOT-spring-boot.jar

