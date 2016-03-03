#!/usr/bin/env bash

eval "$(ssh-agent -s)";
ssh-add ./.ssh/id_rsa
sudo apt-get -y install git-core
sudo chmod 700 ~/.ssh
sudo chmod 600 ~/.ssh/authorized_keys

exit 0;