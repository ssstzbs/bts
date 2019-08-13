mvn clean package
mkdir -p release
rm -rf release/*
cp starter/target/starter-0.0.1-SNAPSHOT.jar ./release/server.jar