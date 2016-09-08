#!/usr/bin/env bash

if [[ ! -d $HOME/kenzan ]]; then
  mkdir $HOME/kenzan
fi

cd $HOME/kenzan/

# 9.2 clone the MSL repository
git clone https://<USERNAME>:<PASSWORD>@github.com/kenzanmedia/million-song-library.git
cd million-song-library/
git submodule init && git submodule update

# 9.3 Set up the Client
cd $HOME/kenzan/million-song-library/msl-pages
npm install && bower install
npm install -g -y protractor && npm install -g -y selenium-webdriver

# 9.4 Set up the Server
cd $HOME/kenzan/million-song-library/server
mvn clean compile

# 9.5 Set up the Cassandra
sh $CASSANDRA_HOME/bin/cassandra >> cassandra_log &
sleep 40s

cd $HOME/kenzan/million-song-library/tools/cassandra
sh $CASSANDRA_HOME/bin/cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
sh $CASSANDRA_HOME/bin/cqlsh -e "SOURCE 'msl_dat_latest.cql';";

# 9.6 add dns mapping
echo "0.0.0.0 msl.kenzanlabs.com" | sudo tee -a  /etc/hosts

exit 0;