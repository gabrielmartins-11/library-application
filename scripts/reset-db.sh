#!/usr/bin/env bash
set -e
mysql -u $DB_USER -p$DB_PASSWORD -e "DROP DATABASE IF EXISTS library_db; CREATE DATABASE library_db;"
mysql -u $DB_USER -p$DB_PASSWORD library_db < src/main/resources/db/create_schema.sql
mysql -u $DB_USER -p$DB_PASSWORD library_db < src/main/resources/db/initialize_data.sql