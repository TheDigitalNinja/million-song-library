Before running msl.cql in Cassandra 2.1:
Edit the hdf5_to_csv.py file to point to the million song data directory.
Run the hdf5_to_csv.py file, it will run for approximatly 9 mins producing the csv data files needed for import.
Edit the msl.cql to point to the newly created csv data files.
Run the msl.cql file from cqlsh with the SOURCE command.
