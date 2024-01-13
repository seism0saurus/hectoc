#!/bin/bash
cd angular || exit
npm install
ng build --configuration "production" --optimization true --aot --output-path ../src/main/resources/static