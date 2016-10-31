#!/usr/bin/env bash

RUN_GIT=1
ADD_HOST=1
BUILD_CASSANDRA=1
BUILD_SERVER=1
BUILD_NODE=1
SKIP_VALIDATION=1
AUTO_YES=1

RED='\033[0;31m'
GREEN='\033[1;36m'
ORANGE='\033[0;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
GRAY='\033[0;37m'
NC='\033[0m' # no color

CURRENT=`pwd`
PROJECT_PATH=${CURRENT}/..

while [[ $# > 0 ]]; do
  key="$1"
  case $key in
    -c|--cassandra)
    BUILD_CASSANDRA=0
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
    \0|-d|--default)
    ADD_HOST=0
    RUN_GIT=0
    BUILD_SERVER=0
    BUILD_NODE=0
    ;;
    *)
    echo -e "\n${GREEN}No valid params provided";
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
  echo -e "\n${GREEN} RUNNING TOOL VALIDATOR... ${NC}\n"
  cd ${PROJECT_PATH}/bin/provision
  chmod +x validate-requirements.sh
  if [[ ${path_to_cassandra} ]]; then
    bash validate-requirements.sh -c $path_to_cassandra
  else
    bash validate-requirements.sh
  fi
  if [[ $? -ne 0 ]]; then exit 1; fi
}

## PULL AND UPDATE MILLION-SONG-LIBRARY REPO ==============================
## ========================================================================

function runGit {
  if [[ ${RUN_GIT} -eq 0 ]]; then
    echo -e "\n${GREEN} PULLING LATEST GIT SUBMODULES... ${NC}\n"
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
    echo -e "\n${GREEN} INSTALLING NPM AND BOWER DEP's... ${NC}\n"
    cd ${PROJECT_PATH}/msl-pages
    if [[ -d node_modules ]]; then
        sudo rm -rf node_modules
        sudo npm cache clean
    fi
    if [[ -d bower_components ]]; then
        sudo rm -rf bower_components
        bower cache clean
    fi

    if [[ -f ~/.nvm/nvm.sh ]]; then . ~/.nvm/nvm.sh ; fi
    if [[ -f ~/.bashrc ]]; then . ~/.bashrc ; fi
    if [[ -f ~/.profile ]]; then . ~/.profile ; fi
    if [[ -f ~/.zshrc ]]; then . ~/.zshrc ; fi

    nvm install v6.0.0
    error_handler $? "unable to nvm install v6.0.0"
    nvm alias default v6.0.0
    error_handler $? "unable to nvm alias default v6.0.0"
    nvm use default
    error_handler $? "unable to nvm use default"

    npm install -y
    error_handler $? "unable to run npm install "
    bower install --allow-root
    error_handler $? "unable to run bower install"

    # Generate swagger html docs
    npm run generate-swagger-html

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
    echo -e "\n${GREEN} BUILDING SERVER SUB-MODULES... ${NC}\n"
    cd ${PROJECT_PATH}/server
    mvn clean compile
    error_handler $? "failed at running main maven file under /server"
    echo -e "\n${ORANGE} DONE BUILDING SERVER SUB-MODULES ${NC}\n"
    else echo -e  "${GRAY}........................ skip server build${NC}"
  fi
}

## CREATE CASSANDRA AND LOAD DATA =========================================
## ========================================================================

function buildCassandra {
  if [[ ${BUILD_CASSANDRA} -eq 0 ]]; then
    if type -p cassandra; then
      echo -e "\n${GREEN} SETTING UP CASSANDRA DB... ${NC}"
      cd ${PROJECT_PATH}/tools/cassandra
      echo found cassandra executable in PATH
      cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
      if [[ $? -ne 0 ]]; then cassandra >> /dev/null; fi

      COUNT=0
      # Attempts 3 times to start cassandra and load ddl
      cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
      if [[ $? -ne 0 && ${COUNT} -lt 3 ]]; then
        sleep 40s
        COUNT=$((COUNT + 1))
        cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
      fi
      error_handler $? "unable to run cqlsh -> msl_ddl_lates.cql. Check if cassandra is running and run sudo ./setup.sh -c ${path_to_cassandra}"

      cqlsh -e "SOURCE 'msl_dat_latest.cql';";
      error_handler $? "unable to run cqlsh -> msl_dat_lates.cql"

      echo -e "\n${ORANGE} DONE SETTING UP CASSANDRA ${NC}"

    elif [[ ${path_to_cassandra} ]]; then

      echo -e "\n${GREEN} SETTING UP CASSANDRA DB... ${NC}"
      cd ${PROJECT_PATH}/tools/cassandra
      if [[ -d "${path_to_cassandra}/bin" ]]; then
        CASSANDRA_BIN="${path_to_cassandra}/bin";
      elif [[ -d "${path_to_cassandra}bin" ]]; then
        CASSANDRA_BIN="${path_to_cassandra}bin";
      else
        echo -e  "\n${RED}Wrong cassandra directory provided${NC}"
        exit 1
      fi

      ${CASSANDRA_BIN}/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
      if [[ $? -ne 0 ]]; then ${CASSANDRA_BIN}/cassandra >> /dev/null; fi

      COUNT=0
      ${CASSANDRA_BIN}/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
      if [[ $? -ne 0 && ${COUNT} -lt 3 ]]; then
          sleep 30s
          COUNT=$((COUNT + 1))
          ${CASSANDRA_BIN}/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
      fi
      error_handler $? "unable to run cqlsh -> msl_ddl_lates.cql. Check if cassandra is running and run sudo ./setup.sh -c ${path_to_cassandra}"

      ${CASSANDRA_BIN}/cqlsh -e "SOURCE 'msl_dat_latest.cql';";
      error_handler $? "unable to run cqlsh -> msl_dat_lates.cql"
      echo -e "\n${ORANGE} DONE SETTING UP CASSANDRA ${NC}"
    fi
  else
    echo -e "${GRAY}SKIPPING CASSANDRA SETUP${NC}"
    echo -e "${GRAY}See about downloading it in: https://downloads.datastax.com/community/${NC}"
    echo -e "${GRAY}Suggested version: dsc-cassandra-2.1.11${NC}"
  fi
}

function bootstrap () {
  echo -e "\n${GREEN} BOOTSTRAPPING... ${NC}\n"
  runGit
  buildMslPages
  buildServer
  buildCassandra
  echo -e "\n${ORANGE} COMPLETED BOOTSTRAPPING ${NC}\n"
}

function confirmBootstrap {
  echo -e "\n${GREEN}Ready to bootstrap your dev environment. Continue? [y/n] ${NC} "
  read -p ""
  if [[ $REPLY =~ [yY](es){0,1}$ ]]
  then
    return 0
  else
    echo -e "\n${RED}SKIPPED BOOTSTRAP\nDONE${NC}"
    return 1
  fi
}

function init {
  if [[ ${SKIP_VALIDATION} -ne 0 ]]; then
    validateTools
  fi

  if [[ AUTO_YES -eq 0 ]]; then
    bootstrap
  elif confirmBootstrap ; then
    bootstrap
  fi
}

init

exit 0;
