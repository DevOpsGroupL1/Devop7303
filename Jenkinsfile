pipeline {

    agent any

    stages {

        stage('Print Environment variables') {
            steps {
                script {
                    echo '---- Printing Environment variables ---'
                    env.each { key, value ->
                        echo "${key} = ${value}"
                    }
                }
            }
        }

        // stage('Checkout') {
        //     when {
        //         expression {
        //             env.BRANCH_NAME == 'staging'
        //         }
        //     }
        //     steps {
        //         checkout scm
        //         script {
        //             echo "Checking out branch: ${env.BRANCH_NAME}"
        //             echo "Repository name: ${env.REPO_NAME}"
        //             echo "Build number: ${env.BUILD_NUMBER}"
        //             echo "Job name: ${env.JOB_NAME}"
        //         }
        //     }

        // }

        stage('Build and Install dependencies') {
            steps {
                echo 'Installing dependencies'
            }
        }

        stage('Test') {
            steps {
                echo 'Performing test ...'
            }
        }

        stage('Quality Assurance gate') {
            steps {
                echo 'Checking dependencies quality'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deployment'
            }
        }

    }

    post {

        success {
            echo 'Build successful! Artifacts archived.'
            cleanWs()
        }

        failure {
            echo 'Build failed. Check the logs for details.'
            cleanWs()
        }

    }

}
