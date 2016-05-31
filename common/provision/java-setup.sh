#!/usr/bin/env bash

function install_homebrew {
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    error_handler $? "no command /usr/bin/ruby"
    brew update
    error_handler $? "unable to brew update"
    echo "Successfully installed homeBrew..."
}

UNAME_S=$(uname -s)

echo "Setting Java 8 "

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

exit 0;