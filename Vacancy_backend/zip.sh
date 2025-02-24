#!/bin/bash
echo 'Zip all'
zip -r Vacancy.zip . -x "./target/*" "./node_modules/*" "./vacancy/node_modules/*" "./vacancy/node/*" "./vacancy/build/*"
