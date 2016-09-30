#!/usr/bin/env bash

RUN_GIT=1
BUILD_SERVER=1
BUILD_NODE=1
SKIP_VALIDATION=1
AUTO_YES=1

RED='\033[0;31m'
GREEN='\033[1;36m'
ORANGE='\033[0;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
NC='\033[0m' # no color

CURRENT=`pwd`
PROJECT_PATH=${CURRENT}/..

while [[ $# > 0 ]]; do
    key="$1"
    case $key in
        -c|--cassandra)
        path_to_cassandra="$2"
        shift
        ;;
        -g|--git)
        RUN_GIT=0
        ;;
        -s|--server)
        BUILD_SERVER=0
        ;;
        -n|--node)
        BUILD_NODE=0
        ;;
        -v|--skip-validation)
        SKIP_VALIDATION=0
        ;;
        -y|--auto-yes)
        AUTO_YES=0
        ;;
        --default)
        RUN_GIT=0
        BUILD_SERVER=0
        BUILD_NODE=0
        ;;
        *)
        echo -e "\n\n${GREEN}No valid params provided";
        echo -e "${GREEN}-s|--server ................................... build server"
        echo -e "${GREEN}-g|--git ...................................... update and pull git sources and sub-modules"
        echo -e "${GREEN}-c <cassandra-path>|--cassandra <path> ........ build cassandra keyspace and load data"
        echo -e "${GREEN}-v|--skip-validation .......................... skips validation of required installed software"
        echo -e "${GREEN}-y|--auto-yes....... .......................... automatically accepts bootstrapping${NC}"
        exit 1;
        ;;
    esac
shift
done

function error_handler () {
    if [[ $1 -ne 0 ]]; then
        echo -e "${RED}ERROR: ${2}${NC}"
        exit 1;
    fi;
}

function validateTools {
  echo -e "\n\n${GREEN} RUNNING TOOL VALIDATOR... ${NC}\n\n"
  cd ${PROJECT_PATH}/bin/provision
  bash basic-dep-setup.sh

  chmod +x validate-requirements.sh
  if [[ ${path_to_cassandra} ]]; then
    bash validate-requirements.sh -c $path_to_cassandra
  else
    bash validate-requirements.sh
  fi
  if [[ $? -ne 0 ]]; then exit 1; fi
}

## ADD HOSTNAME ===========================================================
## ========================================================================

function addHostName {
  echo -e "\n\n${GREEN} ADDING HOST NAME... ${NC}\n\n"
  ping -c 1 msl.kenzanlabs.com

  if [[ $? -ne 0 ]]; then
      echo -e "\n${PURPLE}Your HOST file is being modified${NC}\n"
      echo "0.0.0.0 msl.kenzanlabs.com" | sudo tee -a  /etc/hosts
      error_handler $? "unable to add msl.kenzanlabs.com to /etc/hosts file"
      echo -e "\n${ORANGE} COMPLETED ADDING HOST NAME ${NC}\n"
      else echo -e "\n${GRAY}msl.kenzanlabs.com already part of /etc/hosts${NC}"
  fi
}

## PULL AND UPDATE MILLION-SONG-LIBRARY REPO ==============================
## ========================================================================

function runGit {
  if [[ ${RUN_GIT} -eq 0 ]]; then
      echo -e "\n\n${GREEN} PULLING LATEST GIT SUBMODULES... ${NC}\n\n"
      cd ${PROJECT_PATH}
      git submodule init
      git submodule sync
      error_handler $? "unable to git submodule init, please verify ssh"
      sudo git submodule update --init
      error_handler $? "unable to git submodule update, please verify ssh"
      echo -e "\n${ORANGE} DONE PULLING LATEST GIT SUBMODULE ${NC}\n"
      else echo -e "${GRAY}........................ skip git update${NC}"
  fi
}

## INSTALL MSL-PAGES ======================================================
## ========================================================================

function buildMslPages {
  if [[ ${BUILD_NODE} -eq 0 ]]; then
      echo -e "\n\n${GREEN} INSTALLING NPM AND BOWER DEP's... ${NC}\n\n"
      cd ${PROJECT_PATH}/msl-pages
      if [[ -d node_modules ]]; then
          sudo rm -rf node_modules
          sudo npm cache clean
      fi
      if [[ -d bower_components ]]; then
          sudo rm -rf bower_components
          bower cache clean
      fi

      . ~/.nvm/nvm.sh
      if [[ -d ~/.profile ]]; then . ~/.profile ; fi
      if [[ -d ~/.bashrc ]]; then . ~/.bashrc ; fi
      if [[ -d ~/.zshrc ]]; then . ~/.zshrc ; fi

      nvm install v6.0.0
      error_handler $? "unable to nvm install v6.0.0"
      nvm alias default v6.0.0
      error_handler $? "unable to nvm alias default v6.0.0"
      nvm use default
      error_handler $? "unable to nvm use default"

      sudo npm install -y
      error_handler $? "unable to run npm install "
      bower install --allow-root
      error_handler $? "unable to run bower install"

      # Generate swagger html docs
      sudo npm run generate-swagger-html

      sudo npm install webpack -g
      error_handler $? "unable to install webpack"
      sudo npm install -g -y protractor
      error_handler $? "unable to install protractor"
      sudo npm install -g -y selenium-webdriver
      error_handler $? "unable to install selenium-webdriver"

      echo -e "\n${ORANGE} DONE INSTALLING NPM AND BOWER DEPS ${NC}\n"

      else echo -e "${GRAY}........................ skip node update${NC}"
  fi
}

## INSTALL AND BUILD SERVER ===============================================
## ========================================================================

function buildServer {
  if [[ ${BUILD_SERVER} -eq 0 ]]; then
    echo -e "\n\n${GREEN} BUILDING SERVER SUBMODULES... ${NC}\n\n"
    cd ${PROJECT_PATH}/server
    mvn clean compile
    error_handler $? "failed at running main maven file under /server"
    echo -e "\n${ORANGE} DONE SERVER SUBMODULES ${NC}\n"
    else echo -e  "${GRAY}........................ skip server build${NC}"
  fi
}

## CREATE CASSANDRA AND LOAD DATA =========================================
## ========================================================================

function buildCassandra {
  if [[ ${path_to_cassandra} ]]
    then
        echo -e "\n\n${GREEN} SETTING UP CASSANDRA DB... ${NC}\n\n"
        if [[ ! -d "${path_to_cassandra}/bin" ]]; then
          if [[ ! -d "${path_to_cassandra}bin" ]]; then
            echo -e  "\n${RED}Wrong cassandra directory provided${NC}"
            exit 1
          else
            CASSANDRA_BIN="${path_to_cassandra}bin";
          fi
        else
          CASSANDRA_BIN="${path_to_cassandra}/bin"
        fi

        cd ${PROJECT_PATH}/tools/cassandra

        ${CASSANDRA_BIN}/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";

        if [[ $? -ne 0 ]]; then
            ${CASSANDRA_BIN}/cassandra >> /dev/null;
            sleep 30s
            ${CASSANDRA_BIN}/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
            while [[ $? -ne 0 ]]; do
                sleep 30s
                ${CASSANDRA_BIN}/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
            done
        fi
        error_handler $? "unable to run cqlsh -> msl_ddl_lates.cql. Check if cassandra is running and run sudo ./setup.sh -c ${path_to_cassandra}"

        ${CASSANDRA_BIN}/cqlsh -e "SOURCE 'msl_dat_latest.cql';";
        error_handler $? "unable to run cqlsh -> msl_dat_lates.cql"
        echo -e  "\n${ORANGE} DONE SETTING UP CASSANDRA ${NC}\n"
    else
        echo -e "\n\n${GRAY}NO CASSANDRA FOLDER PROVIDED${NC}"
        echo -e "${GRAY}SKIPPING CASSANDRA SETUP${NC}"
        echo -e "${GRAY}See about downloading it in: https://downloads.datastax.com/community/${NC}"
        echo -e "${GRAY}Suggested version: dsc-cassandra-2.1.11${NC}"
  fi
}

if [[ ${SKIP_VALIDATION} -ne 0 ]]; then
  validateTools
fi

function bootstrap () {
  echo -e "\n\n${GREEN} BOOTSTRAPPING... ${NC}\n\n"
  addHostName
  runGit
  buildMslPages
  buildServer
  buildCassandra
  echo -e "\n${YELLOW} COMPLETED BOOTSTRAPPING ${NC}\n"
}

function confirmBootstrap {
  echo -e "\n\n${GREEN}Ready to bootstrap your dev environment. Continue? [y/n] ${NC} "
  read -p ""
  if [[ $REPLY =~ [yY](es){0,1}$ ]]
  then
    return 0
  else
    echo -e "\n\n${RED}SKIPPED BOOTSTRAP\nDONE${NC}"
    return 1
  fi
}

if [[ AUTO_YES -eq 0 ]]; then
  bootstrap
elif confirmBootstrap ; then
  bootstrap
fi

exit 0;
