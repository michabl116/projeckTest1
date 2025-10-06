pipeline {
    agent any

   environment {
       DOCKER_IMAGE_NAME = "michabl/sep01-project"
       DOCKER_IMAGE_TAG = "latest"
       DOCKER_CREDENTIALS_ID = "Docker_Hub"
       FULL_IMAGE_NAME = "michabl/sep01-project:latest"
       PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
   }

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Verify Docker') {
            steps {
                bat 'docker --version'
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/michabl116/projeckTest1.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Code Coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                jacoco execPattern: 'target/jacoco.exec'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image: ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                    docker.build(env.FULL_IMAGE_NAME)

                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "Pushing Docker image to Docker Hub..."
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_CREDENTIALS_ID) {
                        docker.image(env.FULL_IMAGE_NAME).push()
                    }
                }
            }
        }
        stage('Run with Docker Compose') {
            steps {
                bat 'docker-compose -f docker-compose.yml up --build -d'
            }
        }
        stage('Wait for MariaDB') {
            steps {
                bat '''
                for /l %%x in (1, 1, 10) do (
                    docker exec mariadb-container mariadb-admin ping -h localhost && exit /b 0
                    timeout /t 5 >nul
                )
                echo MariaDB no respondió a tiempo
                exit /b 1
                '''
            }
        }


    }

    post {
        always {
            cleanWs()
        }
    }
}
