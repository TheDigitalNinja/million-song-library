#!/usr/bin/env bash

input="$1"

if [[ $input ]]
	then 
		outputDir="$1";
	else
		echo "Default ouput dir: ../server"
		read -p "Output dir? :" outputDir;
fi	

if [ -z "$outputDir" ]; 
	then echo "No output defined, using ../server"; 
	outputDir="../server"
else 
	echo "output dir is set to '$outputDir'"; 
fi

cd ../client
npm run parse-swagger-src
cd ../common

if [ ! -d swagger-codegen ]
 then
    git clone https://github.com/swagger-api/swagger-codegen;
fi

cd swagger-codegen
mvn package
cd ..

# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf/log4j.properties"

executable="swagger-codegen/modules/swagger-codegen-cli/target/swagger-codegen-cli.jar"
args="generate -i ../client/swagger/api/swagger/swagger.json -l jaxrs  -o ${outputDir} -c ../client/swagger/package.json"

java $JAVA_OPTS -jar $executable $args

exit 0