# AUTO SETUP

Use `docker-setup.sh` under `/bin`

`bash docker-setup.sh`

>**NOTE** If terminal isn't hooked to docker-machine use the -m option otherwise see manual setup "Hook up terminal to docker machine and then run the script" 
`bash docker-setup.sh -m`

Options:

```
-c|--cassandra ................................... build only cassandra image and container
-b|--build ................................... build images. Absence of this param will default to pull images from dockerhub
-m|--machine ...................................... docker machine startup (skipped by default)
-d|--default ...................................... build everything
-s|--server|-n|--node ...................................... build only server image and container 
```

>**NOTE** Once script is done one can view the logs to verify everything went well by typing
`docker exec -it msl-node-server bash -c "tail -f deploy_log"` 
and in a different terminal 
`docker exec -it msl-node-server bash -c "tail -f serve_all_log"`


# MANUAL SETUP


## Hook up terminal to docker machine

1. Create machine with at least 4g of memory
`docker-machine create --driver virtualbox --virtualbox-memory 4000 dev`
2. Make sure machine is up and running
`docker-machine status dev` | `docker-machine start dev`
3. Log into machine before running any of the following commands
`docker-machine env dev`
`eval $(docker-machine env dev)`
4. Update your `/etc/hosts` file with dev docker-machine ip
`sudo echo "$(docker-machine ip dev) msl.kenzanlabs.com" >> /etc/hosts`


## CASSANDRA

1. Build cassandra image

`docker build -t msl/cassandra -f cassandra.dockerfile .`

2. Start cassandra/msl container

`docker run -d --name msl-cassandra -p 7000-7001:7000-7001 -p 9042:9042 -p 7199:7199 -p 9160:9160 msl/cassandra`

3. Upload data into db running container

`docker exec -it msl-cassandra cqlsh -e "SOURCE 'msl_ddl_latest.cql';";`
`docker exec -it msl-cassandra cqlsh -e "SOURCE 'msl_dat_latest.cql';";`


## SERVER

1. Build msl container

`docker build -t msl/node-server -f Dockerfile .`

2. Start msl container

```
docker run \
         -d -p 3000:3000 \
         -p 9004:9004 \
         -p 9003:9003 \
         -p 9002:9002 \
         -p 9001:9001 \
         --name msl-node-server \
         --net=host -ti \
         --entrypoint=/bin/bash \
         msl/node-server
```

3. Start Up Edger Services

`docker exec -it msl-node-server  npm run serve-all`

4. Start FE

```
docker exec \
      -it \
      msl-node-server \
      bash -c "bash npm rebuild node-sass && npm run serve-prod"
```

>**NOTE**: -it - interactive | -d - dameon


## USEFUL COMMANDS

# Delete all images
  `docker rmi $(docker images -q)`

# Stop and delete all containers
  `docker stop $(docker ps -a -q) && docker rm $(docker ps -a -q)`

# Run bash inside running container
  `docker exec -it <running container ID> bash`

>**Remember to clean up running containers**