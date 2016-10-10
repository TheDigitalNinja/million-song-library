#!/usr/bin/env bash

RED='\033[0;31m'
ORANGE='\033[0;33m'
PURPLE='\033[0;35m'
NC='\033[0m' # no color

echo -e "\n${PURPLE}INSTALLING JAVA...........................${NC}"

UNAME_S=$(uname -s)

function install_homebrew {
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    if [[ $? -ne 0 ]]; then echo -e "${RED}ERROR: no command /usr/bin/ruby${NC}"; exit 1; fi
    brew update
    if [[ $? -ne 0 ]]; then echo -e "${RED}ERROR: unable to brew update${NC}"; exit 1; fi
    echo "${ORANGE}\nSuccessfully installed homeBrew...${NC}"
}

if [[ ${UNAME_S} =~ Linux* ]]; then
    sudo apt-get -y update
    sudo apt-get -y upgrade;
    sudo apt-get install -y software-properties-common python-software-properties
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
    sudo add-apt-repository ppa:webupd8team/java -y
    sudo apt-get update
    sudo apt-get install -y oracle-java8-installer
    echo "Setting environment variables for Java 8.."
    sudo apt-get install -y oracle-java8-set-default
  else
    command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
    brew unlink brew-cask
    brew update
    brew tap caskroom/cask
    brew install brew-cask
    brew cask install java
fi;

echo -e "${ORANGE}\nCOMPLETED JAVA SETUP${NC}"

exit 0;