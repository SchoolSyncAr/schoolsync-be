#!/bin/bash
set -e

# Builds the database
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE schoolsync;
EOSQL

# Builds the admin user for the schoolsync db and grants admin access to it
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER main WITH PASSWORD 'patoniato';
    GRANT ALL PRIVILEGES ON DATABASE schoolsync TO main;
EOSQL
