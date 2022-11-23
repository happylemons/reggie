def NODE_IP_ADDRESSES = ['192.168.13.1']
def PROJECT_PORT = "30002"
def SECRET_TOKEN = "65595bb0-f8f5-4097-b67b-fbedc0d806ca"
def PROJECT_GROUP = "pub"
pipeline {

    agent any

    options {
        buildDiscarder(logRotator(daysToKeepStr: '100', numToKeepStr: '300'))
        timeout(time: 5, unit: 'MINUTES')
    }

    environment {
        PROJECT_GROUP_VALUE = "${PROJECT_GROUP}"
        PROJECT_NAME = findProjectName()
        SECRET_TOKEN_VALUE = "${SECRET_TOKEN}"
        PROJECT_PORT_VALUE = "${PROJECT_PORT}"
    }

    stages {

        stage("Init Environment"){
            parallel{
                stage("Build And Push Image"){
                    stages{
                        stage("Build Image"){
                            steps{
                                sh "docker build -qt harbor.happylemon.life/${PROJECT_GROUP_VALUE}/${PROJECT_NAME}:${BUILD_NUMBER} reggie-take-out "
                            }
                        }

                        stage("Push Image"){
                            steps{
                                sh "docker tag harbor.happylemon.life/${PROJECT_GROUP_VALUE}/${PROJECT_NAME}:${BUILD_NUMBER} harbor.happylemon.life/${PROJECT_GROUP_VALUE}/${PROJECT_NAME}:latest"
                                sh "docker push harbor.happylemon.life/${PROJECT_GROUP_VALUE}/${PROJECT_NAME}:${BUILD_NUMBER} > /dev/null"
                                sh "docker push harbor.happylemon.life/${PROJECT_GROUP_VALUE}/${PROJECT_NAME}:latest > /dev/null"
                                sh "docker rmi harbor.happylemon.life/${PROJECT_GROUP_VALUE}/${PROJECT_NAME}:latest "
                                sh "docker rmi harbor.happylemon.life/${PROJECT_GROUP_VALUE}/${PROJECT_NAME}:${BUILD_NUMBER}"
                            }
                        }
                    }
                }
                stage("Stop Exists Containers"){
                    steps{
                        script{
                            NODE_IP_ADDRESSES.each{
                                ipAddress ->
                                shutdownNode(ipAddress)
                            }
                        }

                    }
                }
            }
        }

        stage("Run Containers"){
            steps{
                script{
                    NODE_IP_ADDRESSES.each{
                        ipAddress ->
                        runNode(ipAddress)
                    }
                }
            }
        }
    }
}

def runNode(ipAddress){
    sshagent(['robot']) {
        sh """
            ssh happylemon@${ipAddress} << EOF
                docker run -d \
                    --restart=always  \
                    --net host \
                    --name ${PROJECT_NAME} \
                    -e server.port=${PROJECT_PORT_VALUE} \
                    harbor.happylemon.life/${PROJECT_GROUP_VALUE}/${PROJECT_NAME}:${BUILD_NUMBER};
            EOF
        """.stripIndent()
    }
}

def shutdownNode(ipAddress){
    sshagent(['robot']) {
        sh """
            ssh-keyscan -t rsa,dsa ${ipAddress} >> ~/.ssh/known_hosts
            ssh happylemon@${ipAddress} << EOF
                docker stop ${PROJECT_NAME} || true;
                docker rm ${PROJECT_NAME} || true;
                docker system prune -f || true;
            EOF
        """.stripIndent()
    }
}


def findProjectName(){
    return JOB_NAME.split("/")[0]
}
