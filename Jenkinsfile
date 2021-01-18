pipeline {
    agent {
        kubernetes {
            yamlFile 'jenkins-pod.yaml'
            workspaceVolume emptyDirWorkspaceVolume(memory: false)
            podRetention never()
            activeDeadlineSeconds 600
        }
    }
    environment {
        IMAGE = "registry.jeradmeisner.com/house-music"
    }
    stages {
        stage("Build Frontend") {
            steps {
                container("node") {
                    dir("frontend") {
                        retry(3) {
                            sh "yarn"
                        }
                        sh "yarn build"
                    }
                    sh "rm -rf src/main/resources/static"
                    sh "cp -r frontend/build src/main/resources/static"
                }
            }
        }
        stage("Build Backend") {
            steps {
                container("java") {
                    retry(3) {
                        sh './gradlew build'
                    }
                }
            }
        }
        stage("Build Docker Image") {
            steps {
                container("docker-build") {
                    script {
                        docker.withRegistry("") {
                          def image = docker.build "$IMAGE"
                          image.push "build-$BUILD_NUMBER"
                          image.push "latest"
                        }
                    }
                }
            }
        }
    }
}
