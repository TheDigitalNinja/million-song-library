#!/usr/bin/env bash

UNAME_S=$(uname -s)

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

exit 0;