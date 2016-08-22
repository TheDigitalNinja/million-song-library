#!/usr/bin/env bash

function install_homebrew {
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    error_handler $? "no command /usr/bin/ruby"
    brew update
    error_handler $? "unable to brew update"
    echo "Successfully installed homeBrew..."
}

UNAME_S=$(uname -s)

eval "$(ssh-agent -s)";
ssh-add ./.ssh/id_rsa

if [[ ${UNAME_S} =~ Linux* ]]; then
    sudo apt-get -y update
    sudo apt-get -y upgrade;

    sudo apt-get -y install git-core
  else
    command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
    brew update
    brew install git
fi

sudo chmod 700 ~/.ssh
sudo chmod 600 ~/.ssh/authorized_keys
sudo chmod 400 ~/.ssh/id_rsa

exit 0;