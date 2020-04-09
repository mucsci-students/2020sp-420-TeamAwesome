#!/bin/bash

mvn clean install
mvn exec:java -D exec.mainClass=core.UMLDriver -Dexec.args="gui"
