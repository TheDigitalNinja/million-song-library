#!/usr/bin/env bash

cassandra_proc >> /dev/null
if [[ $? -ne 0 ]]; then echo "alias cassandra_proc='ps -ax | grep cassandra'" >> ~/.profile; fi
rm -rf casssandra_log

if [ -z "$CASSANDRA_HOME" ]; then
  sudo rm -rf $HOME/cassandra
  mkdir $HOME/cassandra
  sudo curl -o $HOME/cassandra/dsc-cassandra-2.1.11-bin.tar.gz "https://downloads.datastax.com/community/dsc-cassandra-2.1.11-bin.tar.gz"
  if [[ $? -ne 0 ]]; then exit 1; fi;
  cd $HOME/cassandra
  tar -zxvf $HOME/cassandra/dsc-cassandra-2.1.11-bin.tar.gz
  if [[ $? -ne 0 ]]; then exit 1; fi;

  if [[ -d ~/.profile ]]; then
    sudo echo "export CASSANDRA_HOME='$HOME/cassandra/dsc-cassandra-2.1.11'\n" >> ~/.profile
    if [[ $? -ne 0 ]]; then exit 1; fi;
    sudo echo "export PATH='$PATH:$CASSANDRA_HOME/bin'\n" >> ~/.profile
    if [[ $? -ne 0 ]]; then exit 1; fi;
    source ~/.profile
  fi

  if [[ -d ~/.bash_profile ]]; then
    sudo echo "export CASSANDRA_HOME='$HOME/cassandra/dsc-cassandra-2.1.11'\n" >> ~/.bash_profile
    if [[ $? -ne 0 ]]; then exit 1; fi;
    sudo echo "export PATH='$PATH:$CASSANDRA_HOME/bin'\n" >> ~/.bash_profile
    if [[ $? -ne 0 ]]; then exit 1; fi;
    source ~/.bash_profile
  fi

  if [[ -d ~/.bashrc ]]; then
    sudo echo "export CASSANDRA_HOME='$HOME/cassandra/dsc-cassandra-2.1.11'\n" >> ~/.bashrc
    if [[ $? -ne 0 ]]; then exit 1; fi;
    sudo echo "export PATH='$PATH:$CASSANDRA_HOME/bin'\n" >> ~/.bashrc
    if [[ $? -ne 0 ]]; then exit 1; fi;
    source ~/.bashrc
  fi

  if [[ -d ~/.zshrc ]]; then
    sudo echo "export CASSANDRA_HOME='$HOME/cassandra/dsc-cassandra-2.1.11'\n" >> ~/.zshrc
    if [[ $? -ne 0 ]]; then exit 1; fi;
    sudo echo "export PATH='$PATH:$CASSANDRA_HOME/bin'\n" >> ~/.zshrc
    if [[ $? -ne 0 ]]; then exit 1; fi;
    source ~/.zshrc
  fi

fi

cd $HOME
sh $HOME/cassandra/dsc-cassandra-2.1.11/bin/cassandra >> cassandra_log

exit 0;