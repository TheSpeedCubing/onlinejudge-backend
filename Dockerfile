FROM debian:bookworm-slim

WORKDIR /app

RUN apt-get update && \
    apt-get install -y curl && \
    apt-get install -y openjdk-17-jdk && \
    apt-get install -y gcc && \
    apt-get install -y g++ && \
    apt-get install -y python3

RUN mkdir -p /etc/apt/keyrings && \
    curl https://www.ucw.cz/isolate/debian/signing-key.asc > /etc/apt/keyrings/isolate.asc && \
    echo "deb [arch=amd64 signed-by=/etc/apt/keyrings/isolate.asc] http://www.ucw.cz/isolate/debian/ bookworm-isolate main" >> /etc/apt/sources.list.d/isolate.list && \
    apt-get update && \
    apt-get install -y isolate

COPY target/onlinejudge-backend-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
