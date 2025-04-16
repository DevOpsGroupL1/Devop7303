pipeline {

    agent any

    environment {
        REPO_NAME = "${env.JOB_NAME}".tokenize('/')[-1]
        BRANCH_NAME = "${env.JOB_NAME}".tokenize('/').get(1)
    }

    options {
        skipDefaultCheckout(true)
    }

    stages {

        stage('Checkout') {
            when {
                // branch "${env.BRANCH_NAME}"
                branch 'staging'
            }
            steps {
                checkout scm
                script {
                    echo "Checking out branch: ${env.BRANCH_NAME}"
                    echo "Repository name: ${env.REPO_NAME}"
                }
            }

        }

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
