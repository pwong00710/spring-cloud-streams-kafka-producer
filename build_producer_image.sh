mvn clean package -DskipTests
docker build -t kafka_producer:latest -f Dockerfile_Kafka_Producer .
