pipeline {
    agent {
        kubernetes {
            yaml '''
                apiVersion: v1
                kind: Pod
                spec:
                  containers:
                  - name: maven
                    image: maven:3.9.6-eclipse-temurin-21
                    command:
                    - cat
                    tty: true
                    volumeMounts:
                    - mountPath: /root/.m2
                      name: maven-repo
                  - name: docker
                    image: docker:latest
                    command:
                    - cat
                    tty: true
                    volumeMounts:
                    - mountPath: /var/run/docker.sock
                      name: docker-sock
                  - name: kubectl
                    image: alpine/k8s:1.31.2
                    command:
                    - cat
                    tty: true
                    securityContext:
                      runAsUser: 0
                  serviceAccountName: jenkins
                  volumes:
                  - name: docker-sock
                    hostPath:
                      path: /var/run/docker.sock
                  - name: maven-repo
                    hostPath:
                      path: /app/.m2
            '''
            defaultContainer 'maven'
        }
    }

    environment {
        DOCKER_REGISTRY = 'registrydocker.devto.shop'
        IMAGE_NAME = 'broad-leaf-store'
        IMAGE_TAG = "latest"
        KUBERNETES_NAMESPACE = 'default'
    }

    stages {

        stage('Build & Test') {
            steps {
                container('maven') {
                    sh '''
                        mvn clean package -DskipTests
                    '''
                }
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                container('docker') {
                    script {
                        def dockerImage = "registrydocker.devto.shop/${IMAGE_NAME}:${IMAGE_TAG}"
                        sh """
                            docker build -t ${dockerImage} .
                            docker push ${dockerImage}
                        """
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                container('kubectl') {
                    script {
                          kubeconfig(credentialsId: 'jenkins-k8s-credentials') {
                              sh '''
                              echo "Step: Kubectl apply config"
                              kubectl apply -f k8s/deployment.yaml -n default

                              echo "Step: Force rolling restart deployment"
                              kubectl rollout restart deployment broad-leaf-store -n default

                              echo "Step: Waiting for rollout to complete"
                              kubectl rollout status deployment broad-leaf-store -n default
                              '''
                         }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline successfully completed'
        }
        failure {
            echo 'Pipeline failed'
        }
        always {
            echo 'Cleaning up workspace'
            cleanWs()
        }
    }
}