#!/bin/bash

# Find the JAR file and extract the version number
FILE_NAME=$(find . -name "*walletobjects*.jar")
VERSION_NUM=$(echo $FILE_NAME | grep -E -o "[0-9]{1,2}\.[0-9]{1,2}\.[0-9]{1,2}")

# Install the file to the local Maven repository
mvn install:install-file \
    -Dfile=$FILE_NAME \
    -DgroupId=com.wallet.objects.library \
    -DartifactId=wallet-objects \
    -Dversion=$VERSION_NUM \
    -Dpackaging=jar