#!/usr/bin/env bash

UNAME_S=$(uname -s)

# Verify OS
while [[ $# > 0 ]]; do
    key="$1"
    case $key in
        -c|--cassandra)
        path_to_cassandra="$2"
        shift
        ;;
        *)
        echo "No params provided";
        exit 1;
        ;;
    esac
shift
done

function validatePorts {
  if lsof -i:3000; then
    echo "MSL Required Port 3000 in use"
  elif lsof -i:3002; then
    echo "MSL Required Port 3002 in use"
  elif lsof -i:3003; then
    echo "MSL Required Port 3003 in use"
  elif lsof -i:3004; then
    echo "MSL Required Port 3004 in use"
  elif lsof -i:9042; then
    echo "MSL Required Port 9042 for Cassandra in use"
  else
    echo "Ports 3000, 3002, 3003, 3004, and 9042 are open for business"
    
  fi
}

function validateOS {
  if [[ ${UNAME_S} =~ Linux* ]] ; then
    echo "Linux OS"
  elif [[ ${UNAME_S} =~ Darwin* ]] ; then
     version_string=$("sw_vers" -productVersion)
     echo Evaluating OS X machine version ${version_string}
     minVersion=10.11

     ## if we have a greater OS X version than 10.11 prompt the user to continue
     if [[  "${version_string:0:2}" -gt "10"  || ("${version_string:0:2}" -eq "11" && "${version_string:3:2}" -gt "10") ]]; then
          echo "OS X version is greater than ${minVersion} (El Capitan)"
          printf "\t** The OS X version installed on this machine (${version_string}) is newer than the version needed for MSL (${minVersion}). MSL has not been tested using this version of OS X, so you may experience problems. "
          if [[ "no" == $(askYesOrNo "Would you like to continue the installation of MSL using the version of OS X that is currently installed?") || \
            "no" == $(askYesOrNo "Are you *really* sure?") ]]
          then
            echo "Quitting MSL installation."
            exit 1
          fi

      else
      ## confirm that we have the minimum version of OS X
         if [[  "${version_string:0:2}" -lt "10"  || ("${version_string:0:2}" -eq "10" && "${version_string:3:2}" -lt "11") ]]; then
          echo "OS X version is less than ${minVersion}"
          exit 1;
          fi
      fi
  else
    echo "Invalid OS"
    exit 1;
  fi
}

function verifyGit {
 minVersion=2.2.x
  if type -p git; then
      echo found git executable in PATH
      _git=git
  else
      echo "please install git ${minVersion}"
      exit;
  fi

  if [[ "$_git" ]]; then
      version_string=$("$_git" --version)
      version="${version_string}"
      echo git version "${version}"

      if [[ "${version:12:1}" -ge "2" && "${version:14:1}" -ge "2" ]]; then
          echo "git version is greater than ${minVersion}"
          printf "\t** The version of git installed on this machine (${version}) is newer than the ${minVersion} version needed for MSL. MSL has not been tested using this version of git, so you may experience problems. "
          if [[ "no" == $(askYesOrNo "Would you like to continue the installation of MSL using the version of git that is currently installed?") || \
            "no" == $(askYesOrNo "Are you *really* sure?") ]]
          then
            echo "Quitting MSL installation."
            exit 1
          fi
      else
          echo "git version is less than ${minVersion}"
          exit 1;
      fi
  fi
}

function verifyNpm {
    minVersion=2.7.x
  if type -p npm; then
      echo found npm executable in PATH
      _npm=npm
  else
      echo "Please install npm ${minVersion} or greater"
      exit 1;
  fi

  if [[ "$_npm" ]]; then

      version_string=$("$_npm" --version)
      echo found npm version "$version_string"

       if [[ "${version_string:0:1}" -ge "2" && "${version_string:2:2}" -ge "12" ]]; then
          printf "\t** The version of npm installed on this machine (${version_string}) is newer than the version needed for MSL (${minVersion}). MSL has not been tested using this version of npm, so you may experience problems. "
          if [[ "no" == $(askYesOrNo "Would you like to continue the installation of MSL using the version of npm that is currently installed?") || \
            "no" == $(askYesOrNo "Are you *really* sure?") ]]
          then
            echo "Quitting MSL installation."
            exit 1
          fi

      else
          echo "found npm version ${version_string} is greater than ${minVersion}"
          exit 1;
      fi
  fi
}

function verifyNode {
  minVersion=0.12.x
  if type -p node; then
      echo found nodejs executable in PATH
      _node=node
  else
      echo "Please install nodejs version ${minVersion} or greater"
      exit 1;
  fi

  if [[ "$_node" ]]; then
      version_string=$("$_node" --version)
      echo found node version "$version_string"
      version="${version_string: -5}"

      if [[ "${version:0:1}" -ge "0" || "${version:2:1}" -ge "12" ]]; then
          echo "node version is greater than ${minVersion}"
          printf "\t** The version of node installed on this machine (${version}) is newer than the ${minVersion} version needed for MSL. MSL has not been tested using this version of Node, so you may experience problems. "
          if [[ "no" == $(askYesOrNo "Would you like to continue the installation of MSL using the version of Node that is currently installed?") || \
            "no" == $(askYesOrNo "Are you *really* sure?") ]]
          then
            echo "Quitting MSL installation."
            exit 1
          fi
      else
          echo "node version is less than ${minVersion}"
          exit 1;
      fi
  fi
}

function verifyJava {
  minVersion=1.8.x
  if type -p java; then
      echo found java executable in PATH
      _java=java
  elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
      echo found java executable in JAVA_HOME
      _java="$JAVA_HOME/bin/java"
  else
      echo "please install Java version ${minVersion} or greater"
      exit 1;
  fi

  if [[ "$_java" ]]; then
      version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')

      echo java version "$version"
      if [[ "${version:0:1}" -eq "1" && "${version:2:1}" -gt "8" ]]; then
          echo "java version is greater than ${minVersion}"
          printf "\t** The version of Java installed on this machine (${version}) is newer than the ${minVersion} version needed for MSL. MSL has not been tested using this version of Java, so you may experience problems. "
          if [[ "no" == $(askYesOrNo "Would you like to continue the installation of MSL using the version of Java that is currently installed?") || \
            "no" == $(askYesOrNo "Are you *really* sure?") ]]
          then
            echo "Quitting MSL installation."
            exit 1
          fi
      else
      ## confirm that we have the minimum version of Java
        if [[ "${version:0:1}" -eq "1" && "${version:2:1}" -lt "8" ]]; then
          echo "Java version is less than ${minVersion}"
          exit 1;
          fi
    fi
  fi
}

function verifyCassandra {
  echo "cassandra directory: " ${path_to_cassandra}
  if [[ ${path_to_cassandra} ]]; then
      if [[ ! -d "${path_to_cassandra}/bin" ]]; then
          if [[ ! -d "${path_to_cassandra}bin" ]]; then
            echo "wrong cassandra directory provided"
            exit 1
          else
            CASSANDRA_BIN="${path_to_cassandra}bin";
          fi
      else
        CASSANDRA_BIN="${path_to_cassandra}/bin";
      fi
  fi

  if type -p cassandra; then
      echo found cassandra executable in PATH
      _cassandra=cassandra
  elif [[ -n "$CASSANDRA_BIN" ]] && [[ -x "$CASSANDRA_BIN/cassandra" ]];  then
      echo found cassandra executable in CASSANDRA_HOME
      _cassandra="$CASSANDRA_BIN/cassandra"
  else
      echo "Please download/install cassandra version 2.1.11"
      exit 1;
  fi

  if [[ "$_cassandra" ]]; then
      version=$("$_cassandra" -v)
      echo cassandra version "$version"
      if [[ "${version:0:1}" -eq "2" && "${version:2:1}" -eq "1" &&  "${version:4:2}" -eq "11" ]]; then
          echo "cassandra version found 2.1.11"
      else
          echo "Please see about using cassandra version 2.1.11"
          exit 1;
      fi
  fi
}

function verifyMaven {
    minVersion=3.3.9
  if type -p mvn; then
      echo found mvn executable in PATH
      _mvn=mvn
  else
      echo "please install maven version ${minVersion} or greater"
      exit 1;
  fi

  version=$(mvn --version | grep -e 'Maven\s[3-9]\.[0-9]\.[0-9]*')
  mvn --version | grep -e 'Maven\s[3-9]\.[0-9]\.[0-9]*'
  if [[ $? -eq 0 ]]; then
    mvn --version | grep -e 'Maven\s[3-9]\.[4-9]\.[0-9]*'
    if [[ $? -eq 0 ]]; then
      echo "Maven 3.4.x found"
        echo "Maven version is greater than ${minVersion}"
          printf "\t** The version of Maven installed on this machine (${version}) is newer than the ${mvn} version needed for MSL. MSL has not been tested using this version of Maven, so you may experience problems. "
          if [[ "no" == $(askYesOrNo "Would you like to continue the installation of MSL using the version of Maven that is currently installed?") || \
            "no" == $(askYesOrNo "Are you *really* sure?") ]]
          then
            echo "Quitting MSL installation."
            exit 1
          fi

    else
      mvn --version | grep -e 'Maven\s3\.3\.9*'
      if [[ $? -eq 0 ]]; then
        echo "Maven ${minVersion} found"
      else
        echo "No maven ${minVersion} or greater was found"
        exit 1;
      fi
    fi
  else
    echo "No maven ${minVersion} or greater was found"
    exit 1;
  fi
}



function verifyNvm {
  if [[ -f ~/.nvm/nvm.sh ]]; then . ~/.nvm/nvm.sh ; fi
  if [[ -f ~/.bashrc ]]; then . ~/.bashrc ; fi
  if [[ -f ~/.profile ]]; then . ~/.profile ; fi
  if [[ -f ~/.zshrc ]]; then . ~/.zshrc ; fi

  if type -p nvm; then
      echo found nvm executable in PATH
  else
    echo "Please install nvm"
    exit 1;
  fi
}

function askYesOrNo() {
    read -p "$1 ([y]es or [N]o): "
    case $(echo $REPLY | tr '[A-Z]' '[a-z]') in
        y|yes) echo "yes" ;;
        *)     echo "no" ;;
    esac
}

init() {
verifyNvm
verifyMaven
verifyNpm
validateOS
verifyJava
verifyNode
verifyGit
validatePorts
verifyCassandra

exit 0;
}

echo "Start of Million Song Library validation of required installations script..."
 init
echo "finished Million Song Library validation of required installations."
