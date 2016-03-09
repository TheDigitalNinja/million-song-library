#!/usr/bin/env bash

sudo chmod 400 ~/.ssh/id_rsa

cassandra_proc >> /dev/null
if [[ $? -ne 0 ]]; then echo "alias cassandra_proc='ps -ax | grep cassandra'" >> ~/.profile; fi
rm -rf casssandra_log

if [ -z "$CASSANDRA_HOME" ]; then
  sudo rm -rf $HOME/cassandra
  mkdir $HOME/cassandra
  sudo curl -o $HOME/cassandra/dsc-cassandra-2.1.11-bin.tar.gz "https://downloads.datastax.com/community/dsc-cassandra-2.1.11-bin.tar.gz"
  cd $HOME/cassandra
  tar -zxvf $HOME/cassandra/dsc-cassandra-2.1.11-bin.tar.gz
  sudo echo "export CASSANDRA_HOME='$HOME/cassandra/dsc-cassandra-2.1.11'" >> ~/.profile
  sudo echo "export PATH='$PATH:$CASSANDRA_HOME/bin'" >> ~/.profile
  source ~/.profile
  sudo chmod -R 777 $CASSANDRA_HOME
fi

cd $HOME
cassandra >> cassandra_log

if [[ ! -d $HOME/kenzan ]]; then
  mkdir $HOME/kenzan
fi
sudo chmod -R 777 $HOME/kenzan
cd $HOME/kenzan/

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