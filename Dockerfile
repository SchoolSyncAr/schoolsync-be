FROM alpine:latest

RUN apk --no-cache add openjdk21

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN sed -i 's/\r$//' gradlew

RUN ./gradlew build

EXPOSE 8081:8080

ENTRYPOINT ["java", "-jar", "build/libs/schoolsync-be-0.0.1-SNAPSHOT.jar"]