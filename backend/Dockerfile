FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml .
# RUN ./mvnw dependency:go-offline

COPY ./src ./src
COPY docker-entrypoint.sh .

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["./mvnw", "spring-boot:run"]