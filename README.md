# Million Songs Library

## A. Automatic setup

To setup project run the `setup.sh` script under `/common` directory. Full process ETA 45 min

> **Java 1.8** is required. **Maven 3.3.9**, **npm@2.7.x** and **node@v0.12.x** or higher are recommended before continuing with setup. Script doesn't include ssh key setup. If running on windows see about install [chocolatey](https://chocolatey.org/) before continuing.   

1. `chmod +x setup.sh`
2. `sudo ./setup <command>`

where **command** is one of:

+ -s | --server ................................... build server
+ -g | --git ...................................... update and pull git sources and sub-modules
+ -n | --node ..................................... update node and bower resources
+ -c _cassandra-path_ | --cassandra _path_ ........ build cassandra keyspace and load data (start cassandra before running script. See section B. Manual setup 2.4)
+ --default ...................................... runs everything but cassandra

For environment setup testing see [vagrant](https://www.vagrantup.com/downloads.html)

Sample: 
`sudo ./setup.sh -c ~/cassandra/dsc-cassandra-2.1.11/ -n -s -g`

___

## B. Manual setup
 
### 1. Global setup 

Some artifacts need to have been previously installed before continuing with the project setup.

1. **[Maven](http://www.tutorialspoint.com/maven/maven_environment_setup.htm)**
2. **[NPM](https://nodejs.org/en/download/package-manager/)** 
3. **[Bower](https://github.com/bower/bower)**
4. **RubyGem**: see about installing rubygem depending on your OS: [Windows](http://rubyinstaller.org/downloads/), [Linux](https://gorails.com/setup/ubuntu/15.10), [Mac](https://gorails.com/setup/osx/10.10-yosemite)
5. **Asciidoctor**: see official github page to learn how to install asciidoctor for your OS: https://github.com/asciidoctor/asciidoctor
6. **Cassandra**: download [dsc-cassandra-2.1.11](https://downloads.datastax.com/community/) and keep it handy for later use

### 2. Project setup

### 2.1 Repository setup

First make sure to have the latest repository sub-modules by running 

```
git submodule init && git submodule update
```

#### 2.3 MSL-PAGES setup

> **npm@2.7.x** and **node@v0.12.x** or higher are recommended before continuing. Check out [nodesource](https://github.com/nodesource/distributions)

Once npm and bower are installed run `npm install && bower install` under the `/msl-pages` directory to download dependencies

Install protactor and selenium-webdriver globally by typing: `npm install -g -y protractor && npm install -g -y selenium-webdriver`

#### 2.2 Server setup

> **Java 1.8** is required. **Maven 3.3.9** is recommended before installation. npm dependencies need to be resolved before continuing with server setup

Once latest sub-modules have been pulled, run the maven file under the `/server` directory by typing from a terminal:

```
mvn clean compile 
```

#### 2.4 Cassandra setup 

1. On new tab start Cassandra:
  - $ sh /{Path to}/[dsc-cassandra-2.1.11](https://downloads.datastax.com/community/)/bin/cassandra
2. From the `/tools/cassandra` directory:
  - $ sh /{Path to}/[dsc-cassandra-2.1.11](https://downloads.datastax.com/community/)/bin/cqlsh
3. From the `cqlsh` console
  - cqlsh> SOURCE 'msl_ddl_latest.cql';
  - cqlsh> SOURCE 'msl_dat_latest.cql';