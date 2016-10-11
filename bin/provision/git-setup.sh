#!/usr/bin/env bash

UNAME_S=$(uname -s)

RED='\033[0;31m'
ORANGE='\033[0;33m'
PURPLE='\033[0;35m'
NC='\033[0m' # no color

echo -e "\n${PURPLE}INSTALLING GIT...........................${NC}"

function install_homebrew {
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    if [[ $? -ne 0 ]]; then echo -e "${RED}ERROR: no command /usr/bin/ruby${NC}"; exit 1; fi
    brew update
    if [[ $? -ne 0 ]]; then echo -e "${RED}ERROR: unable to brew update${NC}"; exit 1; fi
    echo "${ORANGE}\nSuccessfully installed homeBrew...\n${NC}"
}

if type -p ssh-agent; then eval "$(ssh-agent -s)"; fi
if type -p ssh-add; then ssh-add ./.ssh/id_rsa; fi

if [[ ${UNAME_S} =~ Linux* ]]; then
    sudo apt-get -y update
    sudo apt-get -y upgrade;
    sudo apt-get -y install git-core
  elif [[ ${UNAME_S} =~ Darwin* ]]; then
    command -v brew >/dev/null && echo "brew Found In \$PATH" || install_homebrew
    brew update
    brew install git
  else echo -e "${RED}\nINVALID OS\n${nc}"; exit 1;
fi

if [[ -d ~/.ssh ]]; then sudo chmod 700 ~/.ssh; fi
if [[ -f ~/.ssh/authorized_keys ]]; then sudo chmod 600 ~/.ssh/authorized_keys; fi
if [[ -f ~/.ssh/id_rsa ]]; then sudo chmod 400 ~/.ssh/id_rsa; fi

echo -e "\n${ORANGE}COMPLETED GIT SETUP ${NC}"

exit 0;