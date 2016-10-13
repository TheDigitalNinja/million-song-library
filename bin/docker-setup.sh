#!/usr/bin/env bash

PURPLE='\033[0;35m'
RED='\033[0;31m'
GREEN='\033[1;36m'
NC='\033[0m' # no color

CASSANDRA=0
BUILD_SERVER=0
BUILD_IMAGES=1
START_DOCKER_MACHINE=1
RUN_CONTAINERS=1

SERVER_IMAGE_TAG=anram88/msl-server
SERVER_CONTAINER_NAME=msl-server

CASSANDRA_IMAGE_TAG=anram88/msl-cassandra
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

function updateHostFile {
  cat /etc/hosts | grep -E "\b(^|\s*)?$(docker-machine ip dev)\s+msl.kenzanlabs.com\b"
  if [[ $? -ne 0 ]]; then
    matches=$(cat /etc/hosts | grep -E -c "\b(^|\s*)?([0-9]{1,3}.){3}[0-9]{1,3}\s+msl.kenzanlabs.com\b")
    sed --version | grep -i 'gnu'
    if [[ ${matches} -gt 1 || $? -ne 0 ]]; then
      cat /etc/hosts
      echo -e "${GREEN}\nPlease update your /etc/hosts file with $(docker-machine ip dev) msl.kenzanlabs.com\n${NC}"
      if ! confirm "Continue ?" ; then
        echo -e "\n${RED}DONE${NC}"
        exit 1;
      fi
    elif [[  ${matches} -eq 1 ]]; then
      echo -e "${PURPLE}Attempting to edit host file::::::::::::::::::::::${NC}"
      echo -e "${PURPLE}Creating /etc/hosts.bak file${NC}"
      sudo sed -i.bak -E "s/^([[:digit:]]{1,3}.?){4}\s+msl.kenzanlabs.com/$(docker-machine ip dev)  msl.kenzanlabs.com/g" /etc/hosts
      cat /etc/hosts
    elif [[  ${matches} -eq 0 ]]; then
      echo -e "\n${PURPLE}Attempting to edit host file${NC}\n"
      sudo echo "$(docker-machine ip dev) msl.kenzanlabs.com" >> /etc/hosts
    fi
  else
    echo -e "\n${PURPLE}host is already part of /etc/hosts${NC}"
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
}

# SETUP CASSANDRA
function cassandraSetup {
  if [[ ${CASSANDRA} -eq 0 ]]; then
    # Create the image
    docker images | grep 'msl/cassandra'
    if [[ $? -eq 0 ]]; then
      echo -e "\n${PURPLE}msl/cassandra image exists${NC}"
    elif [[ ${BUILD_IMAGES} -eq 0 ]]; then
      echo -e "\n${PURPLE}Building msl/cassandra image${NC}"
      docker build -t ${CASSANDRA_IMAGE_TAG} -f cassandra.dockerfile .
    else
      echo -e "\n${PURPLE}Pulling anram88/msl-cassandra image${NC}"
      docker pull ${CASSANDRA_IMAGE_TAG}
    fi
  fi
}

# SETUP NODE & SERVER
function setupServer {
  if [[ ${BUILD_SERVER} -eq 0 ]]; then
    # Create the image
    docker images | grep 'msl/node-server'
    if [[ $? -eq 0 ]]; then
      echo -e "\n${PURPLE}msl/node-server image already exists${NC}"
    elif [[ ${BUILD_IMAGES} -eq 0 ]]; then
      # This will take some time - about < 1 hour
      echo -e "\n${PURPLE}Building msl/node-server image${NC}"
      echo -e "\n${PURPLE}ETA: ~1hour${NC}"
      docker build -t ${SERVER_IMAGE_TAG} -f Dockerfile .
    else
      echo -e "\n${PURPLE}Pulling anram88/msl-server image${NC}"
      docker pull ${SERVER_IMAGE_TAG}
    fi
  fi
}

function runContainer {
  if [[ ${RUN_CONTAINERS} -eq 0 ]]; then
    if [[ ${CASSANDRA} -eq 0 ]]; then
      # Start the container
      docker ps -a | grep 'msl/cassandra'
      if [[ $? -eq 0 ]]; then
       docker stop msl-cassandra && docker rm msl-cassandra
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
    fi

    if [[ ${BUILD_SERVER} -eq 0 ]]; then
      # Start the container
      docker ps -a | grep 'msl/node-server'
      if [[ $? -eq 0 ]]; then
       docker stop msl-node-server && docker rm msl-node-server
      fi
      docker run \
            -d -p 3000:3000 \
            -p 3002:3002 \
            -p 3003:3003 \
            -p 3004:3004 \
            -p 9004:9004 \
            -p 9003:9003 \
            -p 9002:9002 \
            -p 9001:9001 \
            --name ${SERVER_CONTAINER_NAME} \
            --net=host -ti \
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
        bash -c "cd ../server/msl-login-edge && java -jar target/msl-login-edge-1.1.0-jar-with-dependencies.jar"
      echo -e "\n" && progressAnimation 5 "Starting up login edge"

      docker exec -d ${SERVER_CONTAINER_NAME} \
        bash -c "cd ../server/msl-ratings-edge && java -jar target/msl-ratings-edge-1.1.0-jar-with-dependencies.jar"
      echo -e "\n" && progressAnimation 5 "Starting up ratings edge"

      docker exec -d ${SERVER_CONTAINER_NAME} \
        bash -c "bash ../bin/setup.sh -h -y -v && npm rebuild node-sass && npm run deploy"

      echo -e "\n" && progressAnimation 120 "Starting msl.kenzanlabs.com"

      echo -e "\nAll set, go to ${GREEN}http://msl.kenzanlabs.com:3000${NC}"
    fi

  else
    echo -e "\n${GREEN}Skipped running containers\nSee readme to run containers and start services"
  fi
}

function init {
  cd ..
  updateHostFile
  startUpDockerMachine
  cassandraSetup
  setupServer
  runContainer
}

init


exit 0;