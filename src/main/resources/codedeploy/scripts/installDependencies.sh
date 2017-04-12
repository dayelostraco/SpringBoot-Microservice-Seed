#!/bin/bash

echo "Installing Java 1.8.0 JRE..."
yum -y install java-1.8.0

echo "Removing OpenJDK 1.7.0..."
yum -y remove java-1.7.0-openjdk