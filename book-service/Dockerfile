# 1. JDK potrivit
FROM openjdk:17-jdk-slim

# 2. Setăm directorul de lucru
WORKDIR /app

# 3. Copiem jar-ul compilat
COPY target/*.jar app.jar

# 4. Expunem portul pe care e configurat în application.properties
EXPOSE 8081

# 5. Comanda de pornire
ENTRYPOINT ["java","-jar","/app/app.jar"]
