#!/bin/bash
set -e

echo "Starting deployment of Library 2.0 microservices using OpenShift S2I..."

# You need to be logged into OpenShift cluster and inside a project
# e.g., oc login ...
# oc project library-project

SERVICES=("book-service" "inventory-service" "order-service" "email-service")

for SERVICE in "${SERVICES[@]}"; do
    echo "Deploying $SERVICE..."
    # Create the application from source code utilizing the Java 17 builder image
    oc new-app registry.access.redhat.com/ubi8/openjdk-17:latest~. \
        --context-dir=$SERVICE \
        --name=$SERVICE \
        --env SERVER_PORT=8080

    # Expose internally if needed
    # oc expose svc/$SERVICE
done

echo "Deployment complete! Make sure to apply the Istio manifests in the k8s/ folder to finish setting up the mesh."
echo "Also, please deploy your Keycloak instance and update the Issuer URL in k8s/istio-security.yaml accordingly."
