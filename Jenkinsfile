Groovy
def repoName = ''
def branchName = ''

pipeline {

    agent any

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
                    repoName = env.GIT_URL?.tokenize('/').last()?.replace('.git', '')
                    branchName = env.GIT_BRANCH?.replaceFirst(/^origin\//, '')
                    echo "Initiaized repo: ${repoName}, branch: ${branchName}"
                }
            }
        }

        stage('Checkout') {
            when {
                expression {
                    return branchName == 'staging'
                }
            }
            steps {
                checkout scm
                script {
                    echo "Checking out branch: ${branchName}"
                    echo "Repository name: ${repoName}"
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
