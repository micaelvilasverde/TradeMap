FROM gradle:7.4.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle assembleDebug

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/app/build/outputs/apk/debug/app-debug.apk /app/build/outputs/apk/app-debug.apk 