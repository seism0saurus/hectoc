#!/bin/bash
cd angular
npm install
ng build --configuration "production" --optimization true --aot --output-path ../src/main/resources/static