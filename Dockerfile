FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN chmod +x ./.tools/maven/bin/mvn
RUN ./.tools/maven/bin/mvn -DskipTests package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/airline-reservation-system-0.0.1-SNAPSHOT.jar"]
