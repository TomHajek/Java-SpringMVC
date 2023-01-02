# Need JDK17
FROM adoptopenjdk/openjdk16:debian-slim
RUN apt-get update -y
RUN apt-get install --no-install-recommends --assume-yes wget
RUN mkdir app
RUN ["chmod", "+rwx", "/app"]
WORKDIR /app
COPY --chown=0:0 target/Java-SpringMVC-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8082
CMD java -jar Java-SpringMVC-0.0.1-SNAPSHOT.jar
