# Kenzan Million Song Library Setup

The setup script automates the process of running the Million Song Library demonstration locally on a Mac, Linux, or Windows system. For detailed prerequisites as well as step-by-step instructions, see the [Million Song Library Project Documentation](https://github.com/kenzanmedia/million-song-library/tree/develop/docs) (located in the `../docs` directory).

- [Java 1.8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) is required.
- Windows users must install [Chocolatey](https://chocolatey.org/).
- Mac users must install [Xcode](https://developer.apple.com/xcode/download/) and [enable the root user](https://support.apple.com/en-us/HT204012).
- Install [Cassandra 2.1.11](https://downloads.datastax.com/community/) and [start it](https://github.com/kenzanmedia/million-song-library/tree/develop/tools/cassandra) before running setup.
- [Maven 3.3.9](http://www.tutorialspoint.com/maven/maven_environment_setup.htm), [Node 0.12.x](https://nodejs.org/en/download/), and [npm 2.7.x](https://nodejs.org/en/download/) or higher are recommended.

## Running Setup

To run setup:

1. Change to the `/million-song-library/common` directory.
2. *(Mac and Linux only)* Make the setup script executable:
   
   ```
   chmod +x setup.sh
   ```
   
3. Run the setup script:

   - *Linux:* `sudo ./setup.sh <options>`
   
   - *Mac:* `./setup.sh <options>`
   
   - *Windows:* `.\setup.ps1 <options>`
   
   Where the possible options are:

   - `-s | --server` builds the server
   - `-g | --git` updates and pull Git sources and sub-modules
   - `-h | --host` updates /etc/hosts file with msl.kenzanlab.com mapping
   - `-v | --skip-validation` updates Node and Bower resources
   - `-n | --node` updates Node and Bower resources
   - `-c cassandra-path | --cassandra path` builds the Cassandra keyspace and loads data (Cassandra must be running)
   - `-y | --auto-yes ` defaults a 'Yes' response
   - `--default` runs everything except Cassandra
   - *For example:* `sudo ./setup.sh -c ~/cassandra/dsc-cassandra-2.1.11 -n -s -g`

4. Change to the the `/million-song-library/msl-pages` directory.
5. Start the application front end:
   
   ```
   npm run full-dev
   ```
   
6. Start the server instances:

   - *Linux:* `sudo npm run serve-all`
   
   - *Mac or Windows:* `npm run serve-all`
   
7. Open a Web browser and point it to: `msl.kenzanlabs.com:3000`
