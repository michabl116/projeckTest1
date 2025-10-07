pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Clonar proyecto') {
            steps {
                git branch: 'main', url: 'https://github.com/michabl116/projeckTest1.git'
            }
        }

        stage('Compilar') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Levantar contenedores') {
            steps {
                dir('C:/Users/mihul/IdeaProjects/projectTest1') {
                    bat 'docker-compose up -d'
                }
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
