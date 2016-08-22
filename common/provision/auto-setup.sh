#!/usr/bin/env bash

if [[ ! -d $HOME/kenzan ]]; then
  mkdir $HOME/kenzan
fi

cd $HOME/kenzan/

git clone https://<USERNAME>:<PASSWORD>@github.com/kenzanmedia/million-song-library.git
cd $HOME/kenzan/million-song-library/common
./setup.sh -g -n -s -c $CASSANDRA_HOME
exit 0;