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
            wget http://apache.mirror.anlx.net/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
            error_handler $? "unable wget maven"
            tar -zxf apache-maven-3.3.9-bin.tar.gz
            error_handler $? "unable to unzip maven tar.gz"
            sudo cp -R apache-maven-3.3.9 /usr/local
            error_handler $? "unable to copy maven to /usr/local"
            sudo rm -rf apache-maven-3.3.9
            sudo rm -rf apache-maven-3.3.9-bin.tar.gz
            sudo ln -s /usr/local/apache-maven-3.3.9/bin/mvn /usr/bin/mvn
            error_handler $? "unable to create maven symlink"
            sudo ln -s /usr/local/apache-maven-3.3.9/bin/mvnDebug /usr/bin/mvnDebug
            error_handler $? "unable to create maven symlink"
            error_handler $? "unable to create maven maven"
        elif [[ ${UNAME_S} =~ Darwin* ]]
            then
                command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
                brew update
                error_handler $? "unable to update brew"
                brew install maven
                error_handler $? "unable to install maven"
        else
            echo "Unsupported OS"
            exit 1;
    fi
    sudo echo 'export JAVA_HOME=$(/usr/libexec/java_home)' >> ~/.mavenrc
    error_handler $? "unable to create ~/.mavenrc"
    echo "Successfully installed mvn"
}

function install_npm {
    echo "npm Not Found in \$PATH"
    echo "Installing npm..."
    if [[ ${UNAME_S} =~ Linux* ]] ;
        then
            sudo apt-get -y remove --purge node
            error_handler $? "unable to purge node"
            curl -sL https://deb.nodesource.com/setup_4.x | sudo -E bash -
            error_handler $? "unable to add nodesource"
            sudo apt-get install -y nodejs
            error_handler $? "unable to install nodejs"
            sudo apt-get install -y build-essential
            error_handler $? "unable to install build-essential"
        elif [[ ${UNAME_S} =~ Darwin* ]]
            then
                command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
                brew update
                error_handler $? "unable to update brew"
                brew install node
                error_handler $? "unable to install node"
        else
            echo "Unsupported OS"
            exit 1;
    fi
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
    echo "Successfully installed homeBrew..."
}

function install_gem {
    echo "gem Not Found in \$PATH"
    echo "Installing gem..."
    if [[ ${UNAME_S} =~ Linux* ]] ; then
        sudo apt-get install -y rubygems
        if [[ $? -ne 0 ]]; then sudo apt-get install -y ruby; fi
    else
        command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
        brew update
        error_handler $? "unable to update brew"
        brew install rbenv ruby-build
    fi
    error_handler $? "unable to install rubyGem"
    echo "Successfully installed rubyGem"
}

function install_asciidoctor {
    echo "asciidoctor Not Found in \$PATH"
    echo "Installing asciidoctor..."
    sudo gem install asciidoctor
    error_handler $? "unable to install asciidoctor"
}

## ADD HOSTNAME ===========================================================
## ========================================================================

ping -c 1 msl.kenzanlabs.com

if [[ $? -ne 0 ]]; then
    echo "127.0.0.1 msl.kenzanlabs.com" | sudo tee -a  /etc/hosts
    error_handler $? "unable to add msl.kenzanlabs.com to /etc/hosts file"
    else echo "msl.kenzanlabs.com already part of /etc/hosts"
fi

## RESOLVE GLOBAL DEP =====================================================
## ========================================================================

command -v mvn >/dev/null && echo "mvn Found In \$PATH" || install_maven
command -v npm >/dev/null && echo "npm Found In \$PATH" || install_npm
command -v bower >/dev/null && echo "bower Found In \$PATH" || install_bower
command -v gem >/dev/null && echo "gem Found In \$PATH" || install_gem

which asciidoctor
if [[ $? -ne 0 ]]; then
    install_asciidoctor
  else
    echo "asciidoctor Found In \$PATH"
fi

## PULL AND UPDATE MILLION-SONG-LIBRARY REPO ==============================
## ========================================================================

if [[ ${RUN_GIT} -eq 0 ]]; then
    echo "RUNNING GIT ..."
    cd ${PROJECT_PATH}
    sudo git submodule init
    git submodule init
    error_handler $? "unable to git submodule init, please verify ssh"
    sudo git submodule update
    error_handler $? "unable to git submodule update, please verify ssh"
    else echo "........................ skip git update"
fi

## INSTALL MSL-PAGES ======================================================
## ========================================================================

if [[ ${BUILD_NODE} -eq 0 ]]; then
    echo "RUNNING NODE ..."
    cd ${PROJECT_PATH}/msl-pages
    if [[ -d node_modules ]]; then
        sudo rm -rf node_modules
        sudo npm cache clean
    fi
    if [[ -d bower_components ]]; then
        sudo rm -rf bower_components
        bower cache clean
    fi

    sudo npm -g install npm@latest
    sudo npm install -y
    error_handler $? "unable to run npm install "
    bower install --allow-root
    error_handler $? "unable to run bower install"

    # Generate swagger html docs
    sudo npm run generate-swagger-html

    # Protractor
    sudo npm install -g -y protractor
    error_handler $? "unable to install protractor"
    sudo npm install -g -y selenium-webdriver
    error_handler $? "unable to install selenium-webdriver"

    else echo "........................ skip node update"
fi

## INSTALL AND BUILD SERVER ===============================================
## ========================================================================

if [[ ${BUILD_SERVER} -eq 0 ]]; then
    echo "BUILDING SERVER ..."
    cd ${PROJECT_PATH}/server
    java -version
    mvn -version
    mvn clean compile
    error_handler $? "failed at running main maven file under /server"
    else echo "........................ skip server build"
fi

## CREATE CASSANDRA AND LOAD DATA =========================================
## ========================================================================

if [[ ${path_to_cassandra} ]]
    then
        echo "RUNNING CASSANDRA ..."
        if [[ ! -d "${path_to_cassandra}bin" ]]; then echo "wrong cassandra directory provided"; exit 1; fi

        cd ${PROJECT_PATH}/tools/cassandra

        ${path_to_cassandra}bin/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
        if [[ $? -ne 0 ]]; then
          ${path_to_cassandra}bin/cassandra >> /dev/null
          sleep 30s
          ${path_to_cassandra}bin/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
        fi
        error_handler $? "unable to run cqlsh -> msl_ddl_lates.cql. Check if cassandra is running and run sudo ./setup.sh -c ${path_to_cassandra}"

        ${path_to_cassandra}bin/cqlsh -e "SOURCE 'msl_dat_latest.cql';";
        error_handler $? "unable to run cqlsh -> msl_dat_lates.cql"
    else
        echo "NO CASSANDRA FOLDER PROVIDED"
        echo "SKIPPING CASSANDRA SETUP"
        echo "See about downloading it in: https://downloads.datastax.com/community/"
        echo "Suggested version: dsc-cassandra-2.1.11"
fi

exit 0;