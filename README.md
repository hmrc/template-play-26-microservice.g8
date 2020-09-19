A [Giter8](http://www.foundweekends.org/giter8/) template for creating HMRC Digital Scala Play 2.6 Microservice

How to create a new project based on the template?
==

* Install g8 commandline tool (http://www.foundweekends.org/giter8/setup.html)
* Go to the directory where you want to create the template
* Decide your service name (the hardest part :))
* Run the command

    `g8 {GITHUB_USER}/template-play-26-microservice.g8 --servicename="New Shiny Service" --serviceTargetPort="9999" --package="uk.gov.hmrc.newshinyservice" -o template-test`
    
and then
    
    cd template-test
    git init
	git add .
	git commit -m start
  
* Test generated project using command 

    `sbt test it:test`
    

How to test the template and generate an example project?
==

* Run `./test.sh` 

An example project will be then created and tested in `target/sandbox/template-test`

How to modify the template?
==

 * review template sources in `/src/main/g8`
 * modify files as you need, but be careful about placeholders, paths and so on
 * run `./test.sh` in template root to validate your changes
 
or (safer) ...

* run `./test.sh` first
* open `target/sandbox/template-test` in your preferred IDE, 
* modify the generated example project as you wish, 
* build and test it as usual, you can run `sbt test it:test`,
* when you are done switch back to the template root
* run `./update-g8.sh` in order to port your changes back to the template.
* run `./test.sh` again to validate your changes

What is in the template?
==

Assuming the command above 
the template will supply the following values for the placeholders:

    $packaged$ -> uk/gov/hmrc/newshinyservice
	$package$ -> uk.gov.hmrc.newshinyservice
	$servicenameCamel$ -> NewShinyService
	$servicenamecamel$ -> newShinyService
	$servicenameNoSpaceLowercase$ -> newshinyservice
	$servicenameNoSpaceUppercase$ -> NEWSHINYSERVICE
	$servicenamesnake$ -> new_shiny_service
	$servicenameSnake$ -> NEW_SHINY_SERVICE
	$servicenamePackage$ -> New.Shiny.Service
	$servicenamePackageLowercase$ -> new.shiny.service
	$servicenamePackaged$ -> New/Shiny/Service
	$servicenamePackagedLowercase$ -> new/shiny/service
	$servicenameHyphen$ -> new-shiny-service
	$servicenameLowercase$ -> new shiny service
	$servicenameUppercase$ -> NEW SHINY SERVICE
	$servicename$ -> New Shiny Service
	$serviceTargetPort$ -> 9999

and produce the folders and files as shown below:

    ├── .gitignore
	├── .scalafmt.conf
	├── app
	│   ├── MicroserviceModule.scala
	│   └── uk
	│       └── gov
	│           └── hmrc
	│               └── newshinyservice
	│                   ├── binders
	│                   │   ├── SimpleObjectBinder.scala
	│                   │   └── UrlBinders.scala
	│                   │
	│                   ├── connectors
	│                   │   └── MicroserviceAuthConnector.scala
	│                   │
	│                   ├── controllers
	│                   │   ├── AuthActions.scala
	│                   │   └── NewShinyServiceController.scala
	│                   │
	│                   ├── models
	│                   │   └── NewShinyServiceModel.scala
	│                   │
	│                   ├── repository
	│                   │   ├── NewShinyServiceRepository.scala
	│                   │   └── StrictlyEnsureIndexes.scala
	│                   │
	│                   ├── services
	│                   │   └── AuditService.scala
	│                   │
	│                   └── wiring
	│                       ├── AppConfig.scala
	│                       ├── MicroserviceFilters.scala
	│                       └── MicroserviceMonitoringFilter.scala
	│
	├── build.sbt
	├── conf
	│   ├── app.routes
	│   ├── application-json-logger.xml
	│   ├── application.conf
	│   ├── logback.xml
	│   └── prod.routes
	│
	├── it
	│   └── uk
	│       └── gov
	│           └── hmrc
	│               └── newshinyservice
	│                   ├── controllers
	│                   │   ├── AuthActionsISpec.scala
	│                   │   └── NewShinyServiceControllerISpec.scala
	│                   │
	│                   ├── repository
	│                   │   └── NewShinyServiceRepositoryISpec.scala
	│                   │
	│                   ├── stubs
	│                   │   ├── AuthStubs.scala
	│                   │   └── DataStreamStubs.scala
	│                   │
	│                   └── support
	│                       ├── AppBaseISpec.scala
	│                       ├── BaseISpec.scala
	│                       ├── JsonMatchers.scala
	│                       ├── MetricsTestSupport.scala
	│                       ├── MongoApp.scala
	│                       ├── ServerBaseISpec.scala
	│                       ├── TestApplication.scala
	│                       ├── WireMockSupport.scala
	│                       └── WSResponseMatchers.scala
	│
	├── LICENSE
	├── project
	│   ├── build.properties
	│   └── plugins.sbt
	│
	├── README.md
	├── repository.yaml
	└── test
	    └── uk
	        └── gov
	            └── hmrc
	                └── newshinyservice
	                    └── services
	                        └── AuditServiceSpec.scala