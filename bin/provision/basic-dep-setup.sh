#!/usr/bin/env bash

UNAME_S=$(uname -s)

RED='\033[0;31m'
GREEN='\033[1;36m'
ORANGE='\033[0;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
NC='\033[0m' # no color

function error_handler () {
  if [[ $1 -ne 0 ]]; then
    echo -e "\n${RED}ERROR: ${2}${NC}"
    exit 1;
  fi;
}

function install_maven {
  echo -e "\n${GREEN}INFO: mvn Not Found in \$PATH\nInstalling maven...${NC}"
  if [[ ${UNAME_S} =~ Linux* ]]; then
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
  elif [[ ${UNAME_S} =~ Darwin* ]]; then
      command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
      brew update
      error_handler $? "unable to update brew"
      brew install maven
      error_handler $? "unable to install maven"
  else
    echo -e "\n${RED}ERROR: Unsupported OS${NC}"
    exit 1;
  fi
  sudo echo 'export JAVA_HOME=$(/usr/libexec/java_home)' >> ~/.mavenrc
  error_handler $? "unable to create ~/.mavenrc"
  echo -e "\n${ORANGE}Successfully installed mvn${NC}"
}

function install_npm {
  echo -e "\n${GREEN}INFO: npm Not Found in \$PATH\nInstalling npm...${NC}"
  if [[ ${UNAME_S} =~ Linux* ]]; then
    sudo apt-get -y remove --purge node
    error_handler $? "unable to purge node"
    curl -sL https://deb.nodesource.com/setup_4.x | sudo -E bash -
    error_handler $? "unable to add nodesource"
    sudo apt-get install -y nodejs
    error_handler $? "unable to install nodejs"
    sudo apt-get install -y build-essential
    error_handler $? "unable to install build-essential"
  elif [[ ${UNAME_S} =~ Darwin* ]]; then
    command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
    brew update
    error_handler $? "unable to update brew"
    brew install node
    error_handler $? "unable to install node"
  else
    echo -e "\n${RED}ERROR: Unsupported OS${NC}"
    exit 1;
  fi
  echo -e "\n${ORANGE}Successfully installed npm${NC}"
}

function install_nvm {
  echo -e "\n${GREEN} INFO: nvm Not Found in \$PATH\n Installing nvm...\n${NC}"

  function reloadSource {
    if [[ ${UNAME_S} =~ Linux* ]]; then
      echo "source ~/.nvm/nvm.sh" >> $1
      sudo echo -e "\nexport NVM_DIR=~/.nvm \n[ -s \"~/.nvm/nvm.sh\" ] && . \"~/.nvm/nvm.sh\"" >> $1
      source $1
      echo -e "\n${PURPLE}Reloaded source: ${1} ${NC}"
    elif [[ ${UNAME_S} =~ Darwin* ]]; then
      echo "source $(brew --prefix nvm)/nvm.sh" >> $1
      sudo echo -e "\nexport NVM_DIR=~/.nvm \n[ -s \"~/.nvm/nvm.sh\" ] && . \"~/.nvm/nvm.sh\"" >> $1
      source $1
      echo -e "\n${PURPLE}Reloaded source: ${1} ${NC}"
    fi
    error_handler $? "unable to reload source"
  }

  if [[ ${UNAME_S} =~ Linux* ]]; then
    curl https://raw.githubusercontent.com/creationix/nvm/v0.30.2/install.sh | bash
    error_handler $? "unable to curl nvm"
  elif [[ ${UNAME_S} =~ Darwin* ]]; then
    brew install nvm
    error_handler $? "unable to install nvm"
  fi
  
  if [[ -f ~/.profile ]]; then reloadSource ~/.profile ; fi
  if [[ -f ~/.bashrc ]]; then reloadSource ~/.bashrc ; fi
  if [[ -f ~/.zshrc ]]; then reloadSource ~/.zshrc ; fi

  echo -e "\n${ORANGE}Successfully installed nvm${NC}"
}

function install_bower {
  echo -e "\n${GREEN}INFO: bower Not Found in \$PATH\nInstalling bower...${NC}"
  sudo npm install -g bower
  error_handler $? "unable to install bower"
  echo -e "\n${ORANGE}Successfully installed bower...${NC}"
}

function install_homebrew {
  echo -e "\n${GREEN}INFO: Installing Homebrew...${NC}"
  /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
  error_handler $? "no command /usr/bin/ruby"
  brew update
  error_handler $? "unable to brew update"
  echo -e "\n${ORANGE}Successfully installed HomeBrew...${NC}"
}

function install_gem {
  echo -e "\n${GREEN}INFO: gem Not Found in \$PATH\nInstalling gem...${NC}"
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
  echo -e "\n${ORANGE}Successfully installed rubyGem${NC}"
}

function install_asciidoctor {
  echo -e "\n${GREEN}INFO: asciidoctor Not Found in \$PATH \nInstalling asciidoctor...${NC}"
  sudo gem install asciidoctor
  error_handler $? "unable to install asciidoctor"
  echo -e "\n${ORANGE}Successfully installed asciidoctor${NC}"
}

function install_java {
   sh java-setup.sh
}

function install_cassandra {
   sh cassandra-setup.sh
}

## RESOLVE GLOBAL DEP =====================================================
## ========================================================================

function init {
  command -v mvn >/dev/null && echo "mvn Found In \$PATH" || install_maven
  command -v npm >/dev/null && echo "npm Found In \$PATH" || install_npm
  command -v nvm >/dev/null && echo "nvm Found In \$PATH" || install_nvm
  command -v bower >/dev/null && echo "bower Found In \$PATH" || install_bower
  command -v gem >/dev/null && echo "gem Found In \$PATH" || install_gem
  command -v java >/dev/null && echo "java Found In \$PATH" || install_java
  command -v cassandra >/dev/null && echo "cassandra Found In \$PATH" || install_cassandra

  which asciidoctor
  if [[ $? -ne 0 ]]; then
    install_asciidoctor
  else
    echo "asciidoctor Found In \$PATH"
  fi
}

init
exit 0;
