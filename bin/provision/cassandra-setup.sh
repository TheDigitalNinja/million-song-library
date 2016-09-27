#!/usr/bin/env bash

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
fi

cd $HOME
cassandra >> cassandra_log

exit 0;