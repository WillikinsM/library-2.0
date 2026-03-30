#!/bin/bash
set -e

echo "Starting deployment of Library 2.0 microservices using OpenShift S2I..."

# You need to be logged into OpenShift cluster and inside a project
# e.g., oc login ...
# oc project library-project

NAMESPACE="library-project"

# === Step 1: Apply MongoDB credentials Secret ===
echo "Applying MongoDB secret..."
oc apply -f k8s/mongodb-secret.yaml -n $NAMESPACE

# === Step 2: Deploy services that require MongoDB (inject secret as env var) ===
MONGODB_SERVICES=("book-service" "order-service" "inventory-service")
for SERVICE in "${MONGODB_SERVICES[@]}"; do
    echo "Deploying $SERVICE (with MongoDB credentials)..."
    oc new-app registry.access.redhat.com/ubi9/openjdk-21:latest~. \
        --name=$SERVICE \
        --env SERVER_PORT=8080 \
        --build-env MAVEN_S2I_ARTIFACT_DIRS=$SERVICE/target \
        --build-env MAVEN_ARGS_APPEND="-pl $SERVICE -am -DskipTests"

    # Inject the MongoDB URI from the Secret into the Deployment
    oc set env deployment/$SERVICE \
        --from=secret/mongodb-credentials \
        --prefix="" \
        SPRING_DATA_MONGODB_URI=mongodb-credentials/uri \
        -n $NAMESPACE
done

# === Step 3: Deploy email-service (no MongoDB needed) ===
echo "Deploying email-service..."
oc new-app registry.access.redhat.com/ubi9/openjdk-21:latest~. \
    --name=email-service \
    --env SERVER_PORT=8080 \
    --build-env MAVEN_S2I_ARTIFACT_DIRS=email-service/target \
    --build-env MAVEN_ARGS_APPEND="-pl email-service -am -DskipTests"

echo ""
echo "Deployment complete!"
echo "Monitor build progress with:  oc logs -f bc/<service-name>"
echo "Make sure to apply the Istio manifests:  oc apply -f k8s/istio-routing.yaml && oc apply -f k8s/istio-security.yaml"
