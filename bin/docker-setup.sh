#!/usr/bin/env bash

UNAME_S=$(uname -s)

PURPLE='\033[0;35m'
RED='\033[0;31m'
GREEN='\033[1;36m'
NC='\033[0m' # no color

CASSANDRA=0
BUILD_SERVER=0
BUILD_IMAGES=1
START_DOCKER_MACHINE=1
RUN_CONTAINERS=1

SERVER_IMAGE_TAG=kenzandocker/msl-server
SERVER_CONTAINER_NAME=msl-server

CASSANDRA_IMAGE_TAG=kenzandocker/msl-cassandra
CASSANDRA_CONTAINER_NAME=msl-cassandra

while [[ $# > 0 ]]; do
  key="$1"
  case $key in
    -c|--cassandra)
    CASSANDRA=0
    BUILD_SERVER=1
    ;;
    -s|-n|--server|--node)
    CASSANDRA=1
    BUILD_SERVER=0
    ;;
    -b|--build)
    BUILD_IMAGES=0
    ;;
    -r|--run)
    RUN_CONTAINERS=0
    ;;
    -m|--machine)
    START_DOCKER_MACHINE=0
    ;;
    \0|-d|--default)
    CASSANDRA=0
    BUILD_SERVER=0
    START_DOCKER_MACHINE=1
    BUILD_IMAGES=1
    RUN_CONTAINERS=1
    ;;
    *)
    echo -e "${GREEN}No valid params provided";
    echo -e "${GREEN}-r|--run .........................................  start up containers off by default"
    echo -e "${GREEN}-c|--cassandra .................................... build only cassandra image and container "
    echo -e "${GREEN}-m|--machine ...................................... docker machine startup (skipped by default) "
    echo -e "${GREEN}-d|--default ...................................... build everything"
    echo -e "${GREEN}-s|--server|-n|--node ............................. build only server image and container "
    echo -e "${GREEN}-b|--build ........................................ build images. Default will attempt to pull images from dockerhub${NC}"
    exit 1;
    ;;
  esac
shift
done

function confirm {
  message=$1
  echo -e "\n${GREEN}${message} [y/n] ${NC} "
  read -p ""
  if [[ $REPLY =~ [yY](es){0,1}$ ]]
  then
    return 0
  else
    return 1
  fi
}

function getDockerIp {
  if [[ ${UNAME_S} =~ Linux* ]]; then
    docker_machine_ip=127.0.0.1
  else
    docker_machine_ip=$(docker-machine ip dev)
  fi
}

function progressAnimation {
  timeout=$1
  message=$2
  spin='-\|/'
  i=0
  while [[ ${timeout} -gt 0 ]]; do
    i=$(( (i+1) %4 ))
    printf "\r${message} ................... [${spin:$i:1}]"
    timeout=$((timeout - 1))
    sleep 1s
  done
}

function startUpDockerMachine {
  if [[ ${UNAME_S} =~ Linux* ]]; then
    echo -e "\n${PURPLE}No docker-machine command. Docker ip is: 127.0.0.1${NC}"
  else
    if [[ ${START_DOCKER_MACHINE} -eq 0 ]]; then
      docker-machine status dev
      if [[ $? -ne 0 ]]; then
        echo -e "\n${PURPLE}Creating dev machine${NC}"
        docker-machine create --driver virtualbox --virtualbox-memory 4000 dev
      fi

      docker-machine env dev
      if [[ $? -ne 0 ]]; then
        docker-machine start dev
        docker-machine env dev
      fi
      eval $(docker-machine env dev)
    fi
  fi
}

function cassandraSetup {
  if [[ ${CASSANDRA} -eq 0 ]]; then
    # Create the image
    docker images | grep "${CASSANDRA_IMAGE_TAG}"
    if [[ $? -eq 0 ]]; then
      echo -e "\n${PURPLE}${CASSANDRA_IMAGE_TAG} image exists${NC}"
    elif [[ ${BUILD_IMAGES} -eq 0 ]]; then
      echo -e "\n${PURPLE}Building ${CASSANDRA_IMAGE_TAG} image${NC}"
      docker build -t ${CASSANDRA_IMAGE_TAG} -f cassandra.dockerfile .
    else
      echo -e "\n${PURPLE}Pulling ${CASSANDRA_IMAGE_TAG} image${NC}"
      docker pull ${CASSANDRA_IMAGE_TAG}
    fi

    if [[ ${RUN_CONTAINERS} -eq 0 ]]; then
      runCassandraContainer
    else
      echo -e "\n${GREEN}Skipped running cassandra container\nSee readme to run container and start service"
    fi
  fi
}

function runCassandraContainer {
  # Start the container
  docker ps -a | grep "${CASSANDRA_IMAGE_TAG}"
  if [[ $? -eq 0 ]]; then
   docker stop ${CASSANDRA_CONTAINER_NAME} && docker rm ${CASSANDRA_CONTAINER_NAME}
  fi
  docker run \
        -d \
        --name ${CASSANDRA_CONTAINER_NAME} \
        -p 7000-7001:7000-7001 \
        -p 9042:9042 \
        -p 7199:7199 \
        -p 9160:9160 \
        ${CASSANDRA_IMAGE_TAG}

  # Start data upload
  echo -e "\n${PURPLE}Uploading csv data into msl/cassandra container${NC}"
  RETRIES=0
  docker exec -it ${CASSANDRA_CONTAINER_NAME} cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
  while [[ $? -ne 0 && ${RETRIES} -lt 5 ]]; do
    progressAnimation 10 "Attempting to load csv files"
    echo -e "\n"
    RETRIES=$((RETRIES + 1))
    docker exec -it ${CASSANDRA_CONTAINER_NAME} cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
  done
  if [[ $? -ne 0 ]]; then echo -e "\n${RED}Error: unable to load csv data into DB${NC}" && exit 1; fi
  docker exec -it ${CASSANDRA_CONTAINER_NAME} cqlsh -e "SOURCE 'msl_dat_latest.cql';";
  if [[ $? -ne 0 ]]; then echo -e "\n${RED}Error: unable to load csv data into DB${NC}" && exit 1; fi
}

# SETUP NODE & SERVER
function setupServer {
  if [[ ${BUILD_SERVER} -eq 0 ]]; then
    # Create the image
    docker images | grep "${SERVER_IMAGE_TAG}"
    if [[ $? -eq 0 ]]; then
      echo -e "\n${PURPLE}${SERVER_IMAGE_TAG} image already exists${NC}"
    elif [[ ${BUILD_IMAGES} -eq 0 ]]; then
      # This will take some time - about < 1 hour
      echo -e "\n${PURPLE}Building ${SERVER_IMAGE_TAG} image${NC}"
      echo -e "\n${PURPLE}ETA: ~1hour${NC}"
      docker build -t ${SERVER_IMAGE_TAG} -f Dockerfile .
    else
      echo -e "\n${PURPLE}Pulling ${SERVER_IMAGE_TAG} image${NC}"
      docker pull ${SERVER_IMAGE_TAG}
    fi

    if [[ ${RUN_CONTAINERS} -eq 0 ]]; then
      runServerContainer
    else
      echo -e "\n${GREEN}Skipped running server container\nSee readme to run container and start service"
    fi
  fi
}

function runServerContainer {
  # Start the container
  docker ps -a | grep "${SERVER_CONTAINER_NAME}"
  if [[ $? -eq 0 ]]; then
   docker stop ${SERVER_CONTAINER_NAME} && docker rm ${SERVER_CONTAINER_NAME}
  fi
  docker run \
        -dt -p 3000-3004:3000-3004 -p 9001-9004:9001-9004 \
        --name ${SERVER_CONTAINER_NAME} \
        --net=host \
        --entrypoint=/bin/bash \
        ${SERVER_IMAGE_TAG}                       #image to run

  # Start MSL
  docker exec -d ${SERVER_CONTAINER_NAME} \
    bash -c "npm run catalog-edge-server >> catalog_edge_log"
  echo -e "\n" && progressAnimation 5 "Starting up catalog edge"

  docker exec -d ${SERVER_CONTAINER_NAME} \
    bash -c "npm run account-edge-server >> account_edge_log"
  echo -e "\n" && progressAnimation 5 "Starting up account edge"

  docker exec -d ${SERVER_CONTAINER_NAME} \
    bash -c "npm run login-edge-server >> login_edge_log"
  echo -e "\n" && progressAnimation 5 "Starting up login edge"

  docker exec -d ${SERVER_CONTAINER_NAME} \
    bash -c "npm run ratings-edge-server >> ratings_edge_log"
  echo -e "\n" && progressAnimation 5 "Starting up ratings edge"

  docker exec -d ${SERVER_CONTAINER_NAME} \
    bash -c "npm rebuild node-sass && npm run deploy-dev"

  echo -e "\n" && progressAnimation 120 "Starting MSL"

  echo -e "\n\nAll set, go to ${GREEN}http://${docker_machine_ip}:3000${NC}\n"
}

function init {
  cd ..
  getDockerIp
  startUpDockerMachine
  cassandraSetup
  setupServer
}

init
exit 0;