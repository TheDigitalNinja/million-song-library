#!/usr/bin/env bash

if [[ ! -d $HOME/kenzan ]]; then
  mkdir $HOME/kenzan
fi

cd $HOME/kenzan/

git clone https://<USERNAME>:<PASSWORD>@github.com/kenzanmedia/million-song-library.git
cd $HOME/kenzan/million-song-library/bin
./setup.sh -y -g -h -n -s -c $CASSANDRA_HOME
exit 0;