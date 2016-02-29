#!/usr/bin/env bash

sudo chmod 400 $HOME/.ssh/id_rsa

echo "alias cassandra_proc='ps -ax | grep cassandra'" >> $HOME/.profile

sudo echo "export CASSANDRA_HOME='$HOME/cassandra/dsc-cassandra-2.1.11'" >> $HOME/.profile
sudo echo "export PATH='$PATH:$CASSANDRA_HOME/bin'" >> $HOME/.profile
source $HOME/.profile
sudo chmod -R 777 $CASSANDRA_HOME
cassandra >> cassandra_log

if [[ ! -d $HOME/kenzan ]]; then
  mkdir $HOME/kenzan
fi
sudo chmod -R 777 $HOME/kenzan
cd kenzan/

git clone https://<USERNAME>:<PASSWORD>@github.com/kenzanmedia/million-song-library.git
sudo chmod -R 777 million-song-library

./setup.sh -g
if [[ $? -ne 0 ]]; then
  git submodule init
  git submodule sync
  git submodule update --init
  sudo chmod -R 777 ~/kenzan
fi

cd $HOME/kenzan/million-song-library/common/
sudo ./setup.sh -c $CASSANDRA_HOME -s -n
sudo chmod -R 777 ~/kenzan

cd ../msl-pages

exit 0;