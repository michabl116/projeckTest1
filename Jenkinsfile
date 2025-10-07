pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "michabl/sep01-project"
        DOCKER_IMAGE_TAG = "latest"
        DOCKER_CREDENTIALS_ID = "Docker_Hub"
        FULL_IMAGE_NAME = "${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
        COMPOSE_DIR = "C:/Users/mihul/IdeaProjects/projectTest1" // ← Asegúrate que esta ruta sea correcta
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}" // ← Incluye Docker en el PATH
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
                script {
                    echo "Entrando a carpeta de Docker Compose: ${COMPOSE_DIR}"
                }
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
