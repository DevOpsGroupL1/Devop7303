pipeline {

    agent any

    environment {
        REPO_NAME = ''
        BRANCH_NAME = ''
        BUILD_NUMBER = ''
    }

    stages {

        stage('Print Environment variables') {
            steps {
                echo '---- Printing Environment variables ---'
                script {
                    echo "BRANCH_NAME: ${env.GIT_BRANCH}"
                    echo "BUILD_NUMBER: ${env.BUILD_NUMBER}"
                    echo "GIT_URL: ${env.GIT_URL}"
                }
            }
        }

        stage('Initialize Environment variables') {
            steps {
                script {
                    def repoName = env.GIT_URL?.tokenize('/').last()?.replace('.git', '')
                    def branchName = env.GIT_BRANCH?.replaceFirst(/^origin\//, '')
                    env.REPO_NAME = "${repoName}"
                    env.BRANCH_NAME = "${branchName}"
                    env.BUILD_NUMBER = "${env.BUILD_NUMBER}"
                }
            }
        }

        stage('Checkout') {
            when {
                expression {
                    env.BRANCH_NAME == 'staging'
                }
            }
            steps {
                checkout scm
                script {
                    echo "Checking out branch: ${env.BRANCH_NAME}"
                    echo "Repository name: ${env.REPO_NAME}"
                    echo "Build number: ${env.BUILD_NUMBER}"
                    echo "Job name: ${env.JOB_NAME}"
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
