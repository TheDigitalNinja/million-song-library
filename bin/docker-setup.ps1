
$CASSANDRA=0
$BUILD_SERVER=0
$BUILD_IMAGES=1
$START_DOCKER_MACHINE=1
$RUN_CONTAINERS=1

$SERVER_IMAGE_TAG="kenzandocker/msl-server"
$SERVER_CONTAINER_NAME="msl-server"

$CASSANDRA_IMAGE_TAG="kenzandocker/msl-cassandra"
$CASSANDRA_CONTAINER_NAME="msl-cassandra"

$docker_machine_ip=$(docker-machine ip dev)

foreach ($arg in $args) {
  if (($arg -eq "-c") -or ($arg -eq "--cassandra")){
    $CASSANDRA=0
    $BUILD_SERVER=1
  }
  if ((($arg -eq "-s") -or ($arg -eq "--server")) -or (($arg -eq "-n") -or ($arg -eq "--node"))){
    $CASSANDRA=1
    $BUILD_SERVER=0
  }
  if (($arg -eq "-b") -or ($arg -eq "--build")){ $BUILD_IMAGES=0 }
  if (($arg -eq "-r") -or ($arg -eq "--run")){ $RUN_CONTAINERS=0 }
  if (($arg -eq "-m") -or ($arg -eq "--machine")){ $START_DOCKER_MACHINE=0 }
  if (($arg -eq "-d") -or ($arg -eq "--default")){
    $CASSANDRA=0
    $BUILD_SERVER=0
    $START_DOCKER_MACHINE=1
    $BUILD_IMAGES=1
    $RUN_CONTAINERS=1
  }
}

Function error-handler ($lastCommand, $msg) {
  if ($lastCommand -ne $true) {
    "`nError: $msg"
    Exit
  }
}

Function progressAnimation ($timeout, $msg) {
  $totalTime = $timeout
  DO {
    $timeout--
    Write-Progress -Activity $msg -PercentComplete (($timeout / $totalTimeout) * 100)
    Start-Sleep -Milliseconds 1000
  } While ($timeout > 0)
}

Function startUpDockerMachine {
  if ( $START_DOCKER_MACHINE -eq 0 ) {
    docker-machine status dev
    if ( $? -ne 0 ) {
      "`nCreating dev machine"
      docker-machine create --driver virtualbox --virtualbox-memory 4000 dev
    }

    docker-machine env dev
    if ( $? -ne 0 ) {
      docker-machine start dev
      docker-machine env dev
    }
    eval $(docker-machine env dev)
  }
}

Function cassandraSetup {
  if ( $CASSANDRA -eq 0 ) {
    # Create the image
    if (((docker images) -match "$CASSANDRA_IMAGE_TAG") -eq 0 ) {
      "`n$CASSANDRA_IMAGE_TAG image exists"
    } else {
      if ($BUILD_IMAGES -eq 0) {
        "`nBuilding $CASSANDRA_IMAGE_TAG image"
        docker build -t $CASSANDRA_IMAGE_TAG -f cassandra.dockerfile .
      } else {
        "`nPulling $CASSANDRA_IMAGE_TAG image"
        docker pull $CASSANDRA_IMAGE_TAG
      }
    }

    if ( $RUN_CONTAINERS -eq 0 ) {
      runCassandraContainer
    } else {
      "`nSkipped running cassandra container`nSee readme to run container and start service"
    }
  }
}

Function runCassandraContainer {
  # Start the container

  if ( ((docker ps -a) -match "$CASSANDRA_IMAGE_TAG") -eq $True ) {
    docker stop $CASSANDRA_CONTAINER_NAME && docker rm $CASSANDRA_CONTAINER_NAME
  }

  docker run -d --name $CASSANDRA_CONTAINER_NAME -p 7000-7001:7000-7001 -p 9042:9042 -p 7199:7199 -p 9160:9160 $CASSANDRA_IMAGE_TAG

  # Start data upload
  "`nUploading csv data into msl/cassandra container"
  $RETRIES=0
  docker exec -it $CASSANDRA_CONTAINER_NAME cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
  DO {
    progressAnimation 10 "Attempting to load csv files"
    echo -e "`n"
    $RETRIES++
    docker exec -it $CASSANDRA_CONTAINER_NAME cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
  } While ( $? -ne 0 && $RETRIES -lt 5 )

  error-handler $? "`nError: unable to load csv data into DB"
  docker exec -it $CASSANDRA_CONTAINER_NAME cqlsh -e "SOURCE 'msl_dat_latest.cql';";
  error-handler $? "`nError: unable to load csv data into DB"
}

Function setupServer {
  if ( $BUILD_SERVER -eq 0 ) {
    # Create the image
    if (((docker images) -match "$SERVER_IMAGE_TAG") -eq $True ) {
      echo -e "`n$SERVER_IMAGE_TAG image already exists"
    } else {
      if ( $BUILD_IMAGES -eq 0 ) {
        # This will take some time - about < 1 hour
        "`nBuilding $SERVER_IMAGE_TAG image"
        "`nETA: ~1hour"
        docker build -t $SERVER_IMAGE_TAG -f Dockerfile .
      } else {
        "`nPulling $SERVER_IMAGE_TAG image"
        docker pull $SERVER_IMAGE_TAG
      }
    }

    if ( $RUN_CONTAINERS -eq 0 ) {
      runServerContainer
    } else {
      "`nSkipped running server container`nSee readme to run container and start service"
    }
  }
}

Function runServerContainer {
  # Start the container
  if ( ((docker ps -a) -match "$SERVER_CONTAINER_NAME") -eq $True ) {
   docker stop $SERVER_CONTAINER_NAME && docker rm $SERVER_CONTAINER_NAME
  }

  docker run -dt -p 3000-3004:3000-3004 -p 9001-9004:9001-9004 --name $SERVER_CONTAINER_NAME --net=host --entrypoint=/bin/bash $SERVER_IMAGE_TAG

  # Start MSL
  docker exec -d $SERVER_CONTAINER_NAME bash -c "npm run catalog-edge-server >> catalog_edge_log"
  progressAnimation 5 "Starting up catalog edge"

  docker exec -d $SERVER_CONTAINER_NAME bash -c "npm run account-edge-server >> account_edge_log"
  progressAnimation 5 "Starting up account edge"

  docker exec -d $SERVER_CONTAINER_NAME bash -c "npm run login-edge-server >> login_edge_log"
  progressAnimation 5 "Starting up login edge"

  docker exec -d $SERVER_CONTAINER_NAME bash -c "npm run ratings-edge-server >> ratings_edge_log"
  progressAnimation 5 "Starting up ratings edge"

  docker exec -d $SERVER_CONTAINER_NAME bash -c "npm rebuild node-sass && npm run deploy-dev"

  "`n" && progressAnimation 120 "Starting MSL"

  "`n`nAll set, go to http://$docker_machine_ip:3000`n"
}

Function init {
  cd ..
  getDockerIp
  startUpDockerMachine
  cassandraSetup
  setupServer
}

init
exit 0;

