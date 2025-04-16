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
                    REPO_NAME = repoName
                    BRANCH = branchName
                    REPO_URL = repoUrl
                    BUILD_NUMBER = buildNumber
                }
            }
        }

        stage('Checkout') {
            steps {
                script {
                    echo "Checking out branch ${BRANCH} from repository ${REPO_NAME}"
                    if (REPO_NAME == 'Devop7303') {
                        git branch: "${BRANCH}", url: "${REPO_URL}"
                        echo "Repository URL: ${REPO_URL}"
                        echo "Repository Name: ${REPO_NAME}"
                        echo "Branch Name: ${BRANCH}"
                        echo "Build Number: ${BUILD_NUMBER}"
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
