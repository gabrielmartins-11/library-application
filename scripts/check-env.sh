#!/bin/bash

echo "SPRING_DATASOURCE_URL = $SPRING_DATASOURCE_URL"
echo "SPRING_DATASOURCE_USERNAME = $SPRING_DATASOURCE_USERNAME"
echo "SPRING_DATASOURCE_PASSWORD = ${SPRING_DATASOURCE_PASSWORD:+(set)}"

if [[ -z "$SPRING_DATASOURCE_URL" || -z "$SPRING_DATASOURCE_USERNAME" || -z "$SPRING_DATASOURCE_PASSWORD" ]]; then
  echo "❌ ERROR: Missing required environment variables!"
  exit 1
fi

echo "✅ All environment variables are set."