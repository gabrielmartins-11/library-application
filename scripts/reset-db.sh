#!/usr/bin/env bash
set -e

# Use Spring datasource env vars (already exported in your terminal)
DB_USER="${SPRING_DATASOURCE_USERNAME}"
DB_PASSWORD="${SPRING_DATASOURCE_PASSWORD}"

# Drop and recreate the database
mysql -u "$DB_USER" -p"$DB_PASSWORD" -e "DROP DATABASE IF EXISTS library_db; CREATE DATABASE library_db;"

# Create schema
mysql -u "$DB_USER" -p"$DB_PASSWORD" library_db < src/main/resources/db/create_schema.sql

# Initialize data
mysql -u "$DB_USER" -p"$DB_PASSWORD" library_db < src/main/resources/db/initialize_data.sql

