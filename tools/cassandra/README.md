# Starting Cassandra

Start the Cassandra database before you start the Million Song Library application front end and server instances:

1. Change to the the `/million-song-library/server` directory.
2. Run the Maven file to set up the server:

   ```
   mvn clean compile
   ```
   
3. Open a new terminal window and start Cassandra:

   ```
   sh /<path_to>/dsc-cassandra-2.1.11/bin/cassandra
   ```
   
4. Enter the Cassandra console:

   ```
   sh /<path_to>/dsc-cassandra-2.1.11/bin/cqlsh
   ```
   
5. Import the Million Song Library data:

   ```
   SOURCE 'msl_ddl_latest.cql';
   SOURCE 'msl_dat_latest.cql';
   ```
   