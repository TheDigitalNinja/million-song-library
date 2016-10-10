FROM        ubuntu:latest
MAINTAINER  aramirez@kenzan.com
EXPOSE      3000

COPY    ./bin                    ~/million-song-library/bin
COPY    ./msl-pages              ~/million-song-library/msl-pages
COPY    ./server                 ~/million-song-library/server
COPY    ./docs                   ~/million-song-library/docs

WORKDIR ~/million-song-library/msl-pages
RUN     echo -e "\n\n\033[0;35mCOMPLETED LOADING CONTENTS..................\033[0m\n\n"

# INSTALLING SOME OS BASIC DEPS
RUN     echo -e "\n\n\033[0;35mINSTALLING BASIC OS ........................\033[0m\n\n"
RUN     apt-get update && apt-get -y upgrade
RUN     apt-get install -y apt-utils software-properties-common inetutils-tools inetutils-ping libpng-dev sudo build-essential python vim
RUN     sudo apt-get -y install curl && apt-get -y install wget git-core

# INSTALL JAVA
RUN     cd ../bin/provision && sudo chmod +x java-setup.sh && bash java-setup.sh

# RUNNING SETUP
RUN     echo -e "\n\n\033[0;35mRUNNING SETUP SCRIPT........................\033[0m\n\n"
RUN     cd ../bin/provision && sudo chmod +x validate-requirements.sh
RUN     cd ../bin/provision && sudo chmod +x basic-dep-setup.sh && bash ./basic-dep-setup.sh
RUN     cd ../bin && sudo chmod +x setup.sh && bash ./setup.sh -n -h -s -v -y

ENTRYPOINT ["/bin/bash"]