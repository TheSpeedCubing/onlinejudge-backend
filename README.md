# onlinejudge-backend

OnlineJudge Backend System written with Java Spring

## How to (Build Docker Image from Source)

### Requirements

- JDK 17
- Maven
- Docker

### Steps

1. Clone this repository:
    ```bash
    git clone https://github.com/TheSpeedCubing/onlinejudge-backend.git
    cd onlinejudge-backend
    ```
2. Run the build script:
    ```bash
    ./build.sh
    ```

## How to (As a User)

1. Create the docker-compose.yml:
    ```yaml
    services:
      onlinejudge-backend:
        image: ghcr.io/thespeedcubing/onlinejudge-backend
        container_name: onlinejudge-backend
        ports:
          - "8080:8080"
        restart: unless-stopped
        privileged: true
    ```
2. Start the container:
    ```bash
    sudo docker compose up -d
    ```
