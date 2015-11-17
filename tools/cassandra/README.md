Running locally
Step 1: (from the data-loader directory)
  $ mvn clean package
  $ sh target/appassembler/bin/msl '/{Path to}/MillionSongSubset/data'
Step 2: (new tab start Cassandra)
  $ sh /{Path to}/dsc-cassandra-2.1.11/bin/cassandra
Step 3: (from the cassandra directory)
  $ sh /{Path to}/dsc-cassandra-2.1.11/bin/cqlsh
Step 4: (cqlsh)
  cqlsh> SOURCE 'msl_ddl_latest.cql';
  cqlsh> SOURCE 'msl_dat_latest.cql';