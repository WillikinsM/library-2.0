#!/bin/bash
set -e

echo "---------------------------------------------------------"
echo "🚀 Library 2.0: Professional Binary Deployment Logic"
echo "---------------------------------------------------------"

# 1. Clean up project to ensure a fresh build
echo "📦 Phase 1: Local Maven Build..."
mvn clean package -DskipTests -X

SERVICES=("book-service" "inventory-service" "order-service" "email-service")
PORTS=(8080 8081 8082 8083)

# 2. Iterate through services
for i in "${!SERVICES[@]}"; do
    SERVICE=${SERVICES[$i]}
    PORT=${PORTS[$i]}
    
    echo ""
    echo "🛠 Processing service: $SERVICE (Port: $PORT)"
    
    # Check if the build artifact exists
    JAR_PATH="$SERVICE/target/$SERVICE-1.0-SNAPSHOT.jar"
    if [ ! -f "$JAR_PATH" ]; then
        echo "❌ Error: Artifact for $SERVICE not found at $JAR_PATH"
        exit 1
    fi

    # Create Build Config if it doesn't exist (using binary strategy)
    if ! oc get bc "$SERVICE" -n library-project &> /dev/null; then
        echo "✨ Creating Binary Build Config for $SERVICE..."
        oc new-build registry.access.redhat.com/ubi8/openjdk-17:latest --binary=true --name="$SERVICE" -n library-project
    fi

    # Start the binary build from the local JAR file
    echo "📤 Uploading binary artifact: $JAR_PATH..."
    oc start-build "$SERVICE" --from-file="$JAR_PATH" -n library-project --follow

    # Create the application deployment if it doesn't exist
    if ! oc get dc "$SERVICE" -n library-project &> /dev/null && ! oc get deploy "$SERVICE" -n library-project &> /dev/null; then
        echo "🚀 Creating Deployment for $SERVICE..."
        oc new-app "$SERVICE" -n library-project --env SERVER_PORT="$PORT"
    fi
done

echo ""
echo "---------------------------------------------------------"
echo "✅ Binary Deployment Finished!"
echo "---------------------------------------------------------"
