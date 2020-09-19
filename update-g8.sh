#!/usr/bin/env bash

if [[ -f ./build.sbt ]] && [[ -d ./src/main/g8 ]]; then

   mkdir -p target
   cd target
   if [[ -d .makeitg8 ]] && [[ -d .makeitg8/.git ]] ; then
        cd .makeitg8
        git pull origin master
   else
        rm -r .makeitg8
        git clone https://github.com/arturopala/make-it-g8.git .makeitg8
        cd .makeitg8
   fi

   sbt "run --noclear --source ../../target/sandbox/template-test --target ../.. --name template-play-26-microservice.g8 --package uk.gov.hmrc.newshinyservice --description HMRC+Digital+Scala+Play+2.6+Microservice  -K servicename=New+Shiny+Service serviceTargetPort=9999" -Dbuild.test.command="sbt test it:test" 

else

    echo "WARNING: run the script ./update-g8.sh in the template root folder"
    exit -1

fi
