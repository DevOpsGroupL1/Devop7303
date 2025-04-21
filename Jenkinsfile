def repoName = ''
def branchName = ''

pipeline {

    agent any

    environment {
        SCANNER_HOME = tool 'sonar-scanner'
    }
    
    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {

        stage('Initialize variables') {
            steps {
                script {
                    repoName = env.GIT_URL?.tokenize('/').last()?.replace('.git', '')
                    branchName = env.GIT_BRANCH?.replaceFirst(/^origin\//, '')
                }
            }
        }

        stage('Checkout Repositories') {
            when {
                anyOf {
                    branch 'PR-*'
                    expression {
                        return branchName == 'staging'
                    }
                }
            }
            steps {
                script {
                    echo "Checking out the source code from the repository: ${repoName} - branch: ${branchName}"
                    dir('DEVOP7303') {
                        checkout scm
                    }  
                }
            }
        }

        stage('SonarQube analysis') {
            when {
                branch 'PR-*'
            }
            steps {
                script {
                    dir('DEVOP7303') {
                        echo "Running SonarQube analysis for ${repoName} repository."
                        withSonarQubeEnv('sonar-server') {
                            sh "${SCANNER_HOME}/bin/sonar-scanner -Dsonar.projectName=${repoName} -Dsonar.projectKey=${repoName} -Dsonar.projectVersion=${BUILD_NUMBER}"
                        }
                    }
                }
            }
        }

        stage('SonarQube quality gate') {
            when {
                branch 'PR-*'
            }
            steps {
                script {
                    echo "Waiting for SonarQube quality gate to pass for ${repoName} repository."                  
                    waitForQualityGate abortPipeline: true, credentialsId: 'Sonar-token'                   
                }
            }
        }

        stage('Build and Install dependencies') {
            when {
                branch 'PR-*'
            }
            steps {
                script {
                    dir('DEVOP7303') {
                        echo "Building the project for ${repoName} on branch ${branchName}."
                        sh 'mvn clean install -DskipTests=true'
                    }
                }              
            }
        }

        stage('Test') {
            when {
                branch 'PR-*'
            }
            steps {
                script {
                    dir('DEVOP7303') {
                        echo "Running tests for ${repoName} repository."
                    }
                }
            }
        }

        stage('Build docker image') {
            when {
                expression {
                    return branchName == 'staging'
                }
            }
            steps {
                script {
                    dir('DEVOP7303') {
                        echo "Building docker image for ${repoName} repository."
                        sh 'docker build -t grouponebe -f Docker/Dockerfile.staging .'
                    }
                }
            }
        }

        stage('Deploy docker image to docker hub registry') {
            when {
                expression {
                    return branchName == 'staging'
                }
            }
            steps {
                script {
                    dir('DEVOP7303') {
                        echo "Deploying docker image to docker hub registry for ${repoName} repository."
                        withCredentials([usernamePassword(credentialsId: "${DOCKER_REGISTRY_CREDS}", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {

                            sh 'echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin docker.io'

                            sh "docker tag grouponebe hardarmyyy/grouponebe-${BUILD_NUMBER}:latest"
                            sh "docker push hardarmyyy/grouponebe-${BUILD_NUMBER}:latest"

                        }
                    }
                }               
            }
        }

        stage('Clean up docker images') {
            when {
                expression {
                    return branchName == 'staging'
                }
            }
            steps {
                script {
                    echo "Cleaning up docker images for ${repoName} repository."
                    sh "docker rmi -f hardarmyyy/grouponebe-${BUILD_NUMBER}:latest"
                    sh "docker rmi -f grouponebe"
                }
            }
        }

        stage('Logout from docker hub') {
            when {
                expression {
                    return branchName == 'staging'
                }
            }
            steps {
                script {
                    echo 'Logging out of docker hub.'
                    sh 'docker logout'
                }
            }
        }

        stage('Store build artifacts') {
            when {
                expression {
                    return branchName == 'staging'
                }
            }
            steps {
                script {
                    echo 'Storing build artifacts in the Jenkins workspace.'
                    dir('DEVOP7303') {
                        archiveArtifacts artifacts: '**', allowEmptyArchive: true
                    }
                }
            }
        }

    }

    post {
        
        success {
            script {
                echo 'Build completed successfully.'
                cleanWs()
            }           
        }

        failure {
            echo 'Build failed. Check the logs for details.'
            cleanWs()
        }

        aborted {
            echo 'Build was aborted. Check the logs for details.'
            cleanWs()
        }
    }
}
