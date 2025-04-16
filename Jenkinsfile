pipeline {

    agent any

    environment {
        REPO_NAME = ''
        BRANCH = ''
        REPO_URL = ''
        BUILD_NUMBER = ''
    }

    stages {

        stage('Initialize') {
            steps {
                script {
                    def repoUrl = env.GIT_URL ?: ''
                    def branchName = env.GIT_BRANCH ?: ''
                    def repoName = repoUrl.tokenize('/').last().replace('.git', '')
                    def buildNumber = env.BUILD_NUMBER ?: ''
                    env.REPO_NAME = repoName
                    env.BRANCH = branchName
                    env.REPO_URL = repoUrl
                    env.BUILD_NUMBER = buildNumber
                }
            }
        }

        stage('Checkout') {
            steps {
                script {
                    echo "Checking out branch ${env.BRANCH} from repository ${env.REPO_NAME}"
                    if (env.REPO_NAME == 'Devop7303') {
                        git branch: "${env.BRANCH}", url: "${env.REPO_URL}"
                        echo "Repository URL: ${env.REPO_URL}"
                        echo "Repository Name: ${env.REPO_NAME}"
                        echo "Branch Name: ${env.BRANCH}"
                        echo "Build Number: ${env.BUILD_NUMBER}"
                    } else {
                        error "This is not the correct repository. Exiting build."
                    }
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
