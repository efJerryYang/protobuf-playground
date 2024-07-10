#!/bin/bash

protoc -I=. --java_out=src/main/java/ addressbook.proto
