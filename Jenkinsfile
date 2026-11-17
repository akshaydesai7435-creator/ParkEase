
pipeline {
    agent any

    options {
        timestamps()
    }

    triggers {
        pollSCM('H/5 * * * *')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                echo 'ParkEase source code checked out successfully.'
            }
        }

        stage('Build and Test') {
            steps {
                withCredentials([
                    string(
                        credentialsId: 'parkease-db-password',
                        variable: 'PARKEASE_DB_PASSWORD'
                    )
                ]) {
                    sh '''
                        set -eu
                        sed -i 's/\r$//' mvnw
                        chmod +x mvnw
                        ./mvnw -B clean verify
                    '''
                }
            }

            post {
                always {
                    junit(
                        testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: true
                    )
                }
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts(
                    artifacts: 'target/*.jar',
                    fingerprint: true
                )
            }
        }
    }

    post {
        success {
            echo 'ParkEase CI pipeline completed successfully!'
        }

        failure {
            echo 'ParkEase CI pipeline failed. Check the console output.'
        }
    }
}
