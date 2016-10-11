FROM        cassandra:2.1.11
MAINTAINER  aramirez@kenzan.com

COPY    ./tools                   ~/million-song-library/tools
COPY    ./bin                     ~/million-song-library/bin

WORKDIR ~/million-song-library/tools/cassandra