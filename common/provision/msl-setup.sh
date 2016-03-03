#!/usr/bin/env bash

sudo chmod 400 ~/.ssh/id_rsa

echo "alias cassandra_proc='ps -ax | grep cassandra'" >> ~/.profile

sudo echo "export CASSANDRA_HOME='$HOME/cassandra/dsc-cassandra-2.1.11'" >> ~/.profile
sudo echo "export PATH='$PATH:$CASSANDRA_HOME/bin'" >> ~/.profile
source ~/.profile
sudo chmod -R 777 $CASSANDRA_HOME
cassandra >> cassandra_log

if [[ ! -d $HOME/kenzan ]]; then
  mkdir $HOME/kenzan
fi
sudo chmod -R 777 $HOME/kenzan
cd kenzan/

git clone https://<USERNAME>:<PASSWORD>@github.com/kenzanmedia/million-song-library.git
sudo chmod -R 777 million-song-library

cd $HOME/kenzan/million-song-library
git submodule init
git submodule sync
git submodule update --init

sudo chmod -R 777 $HOME/kenzan

cd $HOME/kenzan/million-song-library/common
./setup.sh -c $CASSANDRA_HOME -s -n
sudo chmod -R 777 ~/kenzan

cd $HOME/kenzan/million-song-library/msl-pages

exit 0;