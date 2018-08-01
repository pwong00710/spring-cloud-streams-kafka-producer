. ./env.sh
export CONSUMER_INSTANCE_INDEX=$1
export PRODUCER_PORT=808$1
java -jar target/spring-cloud-stream-kafka-producer-0.0.1-SNAPSHOT.jar

