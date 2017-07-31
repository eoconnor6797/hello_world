node("Linux") {
	def mvnHome
		stage('Preparation') { // for display purposes
			// Get some code from a GitHub repository
			git 'https://github.com/eoconnor6797/hello_world.git'
				// Get the Maven tool.
				// ** NOTE: This 'M3' Maven tool must be configured
				// **       in the global configuration.           
				mvnHome = tool 'mvn'
				javaHome = tool 'java_1.8'
		}
	stage('Build') {
		// Run the maven build
		if (isUnix()) {
			//  sh "'${javaHome}/bin/javac' hello.java"
			sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean compile package"
		} else {
			//bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean compile package/)
		}
		
	}
	stage('Store') {
		def server = Artifactory.server 'localHost'
			def uploadSpec = """{
			"files": [
			{
				"pattern": "target/*.jar",
					"target": "pipe_test/${BUILD_TAG}/"
			}
			]
	}"""
	server.upload(uploadSpec)
	}

	stage('SonarQube analysis') {
		// requires SonarQube Scanner 2.8+
		def scannerHome = tool 'sonar-scan'
			withSonarQubeEnv('sonar-server') {
				sh "${scannerHome}/bin/sonar-runner"
			}
		timeout(time: 1, unit: 'HOURS') {
			def qg = waitForQualityGate()
				if (qg.status != 'OK') { 
					error "did not work"

				}
		}
	}

}
