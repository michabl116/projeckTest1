pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "michabl/sep01-project"
        DOCKER_IMAGE_TAG = "latest"
        DOCKER_CREDENTIALS_ID = "Docker_Hub"
        FULL_IMAGE_NAME = "${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
        COMPOSE_DIR = "C:/Users/mihu1/IdeaProjects/projectTest1" // ← Ruta donde está tu docker-compose.yml
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}" // ← Asegura que Jenkins encuentre Docker
    }

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Verificar Docker') {
            steps {
                bat 'docker --version'
                bat 'docker-compose --version'
            }
        }

        stage('Clonar proyecto') {
            steps {
                git branch: 'main', url: 'https://github.com/michabl116/projeckTest1.git'
            }
        }

        stage('Compilar y testear') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Generar cobertura') {
            steps {
                bat 'mvn jacoco:report'
            }
        }

        stage('Publicar resultados de test') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publicar reporte de cobertura') {
            steps {
                jacoco execPattern: 'target/jacoco.exec'
            }
        }

        stage('Construir imagen Docker') {
            steps {
                script {
                    echo "Construyendo imagen: ${FULL_IMAGE_NAME}"
                    docker.build(FULL_IMAGE_NAME)
                }
            }
        }

        stage('Subir imagen a Docker Hub') {
            steps {
                script {
                    echo "Subiendo imagen a Docker Hub..."
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_CREDENTIALS_ID) {
                        docker.image(FULL_IMAGE_NAME).push()
                    }
                }
            }
        }

        stage('Desplegar con Docker Compose') {
            steps {
                dir("${COMPOSE_DIR}") {
                    bat 'docker-compose down --remove-orphans --volumes'
                    bat 'docker-compose up -d'
                }
            }
        }

        stage('Esperar a que MariaDB esté lista') {
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

        stage('Verificar contenedores') {
            steps {
                bat 'docker ps'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
