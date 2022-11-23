FROM maven:3.8.6-eclipse-temurin-17-alpine as compile
ARG PROJECT_NAME=reggie
WORKDIR /var/www/application
ENV MAVEN_OPTS='-Xms512m -Xmx1024m -Xss2m'
COPY pom.xml pom.xml
RUN --mount=type=cache,target=/root/.m2,id=${PROJECT_NAME} mvn -T 1C dependency:resolve dependency:resolve-plugins
COPY reggie-take-out reggie-take-out
RUN --mount=type=cache,target=/root/.m2,id=${PROJECT_NAME} mvn -T 1C package -DskipTests=true
RUN mv target/${PROJECT_NAME}.jar target/application.jar


FROM eclipse-temurin:17.0.5_8-jre-alpine as builder
WORKDIR /var/www/application
COPY --from=compile /var/www/application/target/application.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM eclipse-temurin:17.0.5_8-jre-alpine
EXPOSE 8080
WORKDIR /var/www/application

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/shanghai" > /etc/timezone;

ENV JAVA_OPTS "-Xms256m -Xmx512m -Xss1m"

COPY docker-entrypoint.sh docker-entrypoint.sh

COPY --from=builder /var/www/application/dependencies/ ./
COPY --from=builder /var/www/application/snapshot-dependencies/ ./
COPY --from=builder /var/www/application/spring-boot-loader/ ./
COPY --from=builder /var/www/application/application/ ./
RUN chmod +x docker-entrypoint.sh

ENTRYPOINT ["./docker-entrypoint.sh"]
