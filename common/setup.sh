#!/usr/bin/env bash

RUN_GIT=1
BUILD_SERVER=1
BUILD_NODE=1

CURRENT=`pwd`
PROJECT_PATH=${CURRENT}/..
UNAME_S=$(uname -s)

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
        --default)
        RUN_GIT=0
        BUILD_SERVER=0
        BUILD_NODE=0
        ;;
        *)
        echo "No params provided";
        echo "-s|--server ................................... build server"
        echo "-g|--git ...................................... update and pull git sources and sub-modules"
        echo "-c <cassandra-path>|--cassandra <path> ........ build cassandra keyspace and load data"
        exit 1;
        ;;
    esac
shift
done

function error_handler () {
    if [[ $1 -ne 0 ]]; then
        echo $2
        exit 1;
    fi;
}

function install_maven {
    echo "mvn Not Found in \$PATH"
    echo "Installing maven..."
    if [[ ${UNAME_S} =~ Linux* ]] ;
        then
            sudo apt-get -y install maven
        elif [[ ${UNAME_S} =~ Darwin* ]]
            then
                command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
                brew install maven
        else
            echo "Unsupported OS"
            exit 1;
    fi
    error_handler $? "unable to install maven"
    echo "Successfully installed mvn"
}

function install_npm {
    echo "npm Not Found in \$PATH"
    echo "Installing npm..."
    if [[ ${UNAME_S} =~ Linux* ]] ;
        then
            sudo apt-get -y remove --purge node
            error_handler $? "unable to purge node"
            sudo apt-get -y install nodejs
            error_handler $? "unable to install nodejs"
            sudo apt-get -y install npm
            error_handler $? "unable to install npm"
            sudo apt-get -y install nodejs-legacy
            error_handler $? "unable to install nodejs-legacy"
        elif [[ ${UNAME_S} =~ Darwin* ]]
            then
                command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
                brew install node
                error_handler $? "unable to install node"
        else
            echo "Unsupported OS"
            exit 1;
    fi
    npm install -y -g --force npm@3.5.6
    error_handler $? "unable to update node"
    echo "Successfully installed npm"
}

function install_bower {
    echo "bower Not Found in \$PATH"
    echo "Installing bower..."
    sudo npm install -g bower
    error_handler $? "unable to install bower"
    echo "Successfully installed bower..."
}

function install_homebrew {
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    error_handler $? "no command /usr/bin/ruby"
    brew update
    error_handler $? "unable to brew update"
}

## ADD HOST ===============================================================
## ========================================================================

ping -c 1 msl.kenzanlabs.com

if [[ $? -ne 0 ]]; then
    echo "\n 127.0.0.1 msl.kenzanlabs.com" | sudo tee -a  /etc/hosts
    error_handler $? "\n unable to add msl.kenzanlabs.com to /etc/hosts file \n"
    else echo "msl.kenzanlabs.com already part of /etc/hosts"
fi

## RESOLVE GLOBAL DEP =====================================================
## ========================================================================

command -v mvn >/dev/null && echo "mvn Found In \$PATH" || install_maven
command -v npm >/dev/null && echo "npm Found In \$PATH" || install_npm
command -v bower >/dev/null && echo "bower Found In \$PATH" || install_bower

## PULL AND UPDATE MILLION-SONG-LIBRARY REPO ==============================
## ========================================================================

if [[ ${RUN_GIT} -eq 0 ]]; then
    echo "RUNNING GIT ..."
    cd ${PROJECT_PATH}
    git checkout develop
    error_handler $? "unable to check out to develop"
    git pull origin develop
    error_handler $? "unable to git pull sources"
    git submodule init
    error_handler $? "unable to git submodule init, please verify ssh"
    git submodule update
    error_handler $? "unable to git submodule update, please verify ssh"
    else echo "........................ skip git update"
fi

## INSTALL AND BUILD SERVER ===============================================
## ========================================================================


if [[ ${BUILD_SERVER} -eq 0 ]]; then
    echo "BUILDING SERVER ..."
    cd ${PROJECT_PATH}/server
    mvn pom.xml clean compile
    error_handler $? "\n failed at running main maven file under /server \n"
    else echo "........................ skip server build"
fi

## INSTALL MSL-PAGES ======================================================
## ========================================================================

if [[ ${BUILD_NODE} -eq 0 ]]; then
    echo "RUNNING NODE ..."
    cd ${PROJECT_PATH}/msl-pages
    if [[ -d node_modules ]]; then
        rm -rf node_modules
        npm cache clean
    fi
    if [[ -d bower_components ]]; then
        rm -rf bower_components
        bower cache clean
    fi

    npm -y install
    error_handler $? "unable to run npm install "
    bower install --allow-root
    error_handler $? "unable to run bower install"

    # Generate swagger html docs
    npm run generate-swagger-html

    # Protractor
    npm install -g -y protractor
    error_handler $? "unable to install protractor"
    npm install -g -y selenium-webdriver
    error_handler $? "unable to install selenium-webdriver"

    else echo "........................ skip node update"
fi

## CREATE CASSANDRA AND LOAD DATA =========================================
## ========================================================================

if [[ ${path_to_cassandra} ]]
    then
        echo "RUNNING CASSANDRA ..."
        if [[ -d ${path_to_cassandra}/bin ]]; then echo "wrong cassandra directory provided"; exit 1; fi

        cd ${PROJECT_PATH}/tools/cassandra

        sh ${path_to_cassandra}/bin/cassandra
        error_handler $? "unable to start cassandra"

        ${path_to_cassandra}/bin/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
        error_handler $? "unable to run cqlsh -> msl_ddl_lates.cql"

        ${path_to_cassandra}/bin/cqlsh -e "SOURCE 'msl_dat_latest.cql';";
        error_handler $? "unable to run cqlsh -> msl_dat_lates.cql"
    else
        echo "NO CASSANDRA FOLDER PROVIDED"
        echo "SKIPPING CASSANDRA SETUP"
        echo "See about downloading it in: https://downloads.datastax.com/community/"
        echo "Suggested version: dsc-cassandra-2.1.11"
fi

exit 0;