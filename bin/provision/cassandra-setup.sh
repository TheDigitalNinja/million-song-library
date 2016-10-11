#!/usr/bin/env bash

ORANGE='\033[0;33m'
PURPLE='\033[0;35m'
NC='\033[0m' # no color

echo -e "\n${PURPLE}INSTALLING CASSANDRA.......................${NC}"

cassandra_proc >> /dev/null
if [[ $? -ne 0 ]]; then echo "alias cassandra_proc='ps -ax | grep cassandra'" >> ~/.profile; fi

if [[ -f casssandra_log ]]; then rm -rf casssandra_log ; fi

if [ -z "$CASSANDRA_HOME" ]; then
  if [[ -d $HOME/cassandra ]]; then rm -rf $HOME/cassandra ; fi
  mkdir $HOME/cassandra

  sudo curl -o $HOME/cassandra/dsc-cassandra-2.1.11-bin.tar.gz "https://downloads.datastax.com/community/dsc-cassandra-2.1.11-bin.tar.gz"
  if [[ $? -ne 0 ]]; then exit 1; fi;

  cd $HOME/cassandra && tar -zxvf $HOME/cassandra/dsc-cassandra-2.1.11-bin.tar.gz
  if [[ $? -ne 0 ]]; then exit 1; fi;

 function reloadSource {
   sudo echo -e "\nexport CASSANDRA_HOME='$HOME/cassandra/dsc-cassandra-2.1.11'" >> $1
   if [[ $? -ne 0 ]]; then exit 1; fi;
   sudo echo -e "\nexport PATH='$PATH:$CASSANDRA_HOME/bin'" >> $1
   if [[ $? -ne 0 ]]; then exit 1; fi;
   source $1
   echo -e "\n${PURPLE} Reloaded source: $1 ${NC}"
  }

  if [[ -f ~/.profile ]]; then
    reloadSource ~/.profile
  fi

  if [[ -f ~/.bashrc ]]; then
    reloadSource ~/.bashrc
  fi

  if [[ -f ~/.zshrc ]]; then
    reloadSource ~/.zshrc
  fi

fi

cd $HOME && sh $HOME/cassandra/dsc-cassandra-2.1.11/bin/cassandra >> cassandra_log

echo -e "\n${ORANGE}COMPLETED CASSANDRA SETUP ${NC}"

exit 0;