# Million Songs Library

This directory and its subdirectories contain artifacts that can be used by both front-end (client) and back-end (server) components.

To run setup script 
1. `chmod +x setup.sh`
2. `sudo ./setup <command>`

where <command> is one of:
-s|--server ................................... build server
-g|--git ...................................... update and pull git sources and sub-modules
-n|--node ..................................... update node and bower resources
-c <cassandra-path>|--cassandra <path> ........ build cassandra keyspace and load data
--default...................................... runs everything but cassandra

To install vagrant refer to `https://www.vagrantup.com/downloads.html`