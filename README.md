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

> On windows use the `setup.ps1` script instead of the `setup.sh`. Make sure to run with administrator rights.

Sample: 
`sudo ./setup.sh -c ~/cassandra/dsc-cassandra-2.1.11 -n -s -g`

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
  
## C. AWS - Vagrant

Download and install vagrant [vagrant](https://www.vagrantup.com/downloads.html)

1. Replace the `<<PATH_TO_dsc-cassandra-2.1.11>>` in the provision function of the Vagrant file with the path to your local cassandra directory

2. Generate a public and private ssh key to use on the AWS EC2 instance (Not the ones from your local copy). Edit the `prod` box in the Vagrant file with the corresponding keys. Such as: 
  - `prod.vm.provision "file", source: "~/.ssh/awsVagrantUbuntu", destination: "./.ssh/id_rsa"`
  - `prod.vm.provision "file", source: "~/.ssh/awsVagrantUbuntu.pub", destination: "./.ssh/id_rsa.pub"`
 
3. Head to the AWS console -> EC2 -> Network & Security tab -> Key pairs, and create a new key pair. Once created point `override.ssh.private_key_path` value on the Vagrantfile to the location of your keyPair.pem file. Change `aws.keypair_name` to the name of the corresponding keypair.

4. Head to the AWS console and look for Security Credentials, create a new "Access Key" and replace the `aws.access_key_id` and the `aws.secret_access_key` values in the Vagrantfile correspondingly.

5. Make sure the security group pointed by the key `aws.security_groups` in your Vagrantfile points to a security group with both ssh and http inbound rules enabled. You can do this through the AWS console -> EC2 -> Network & Security -> Security Groups

6. Head to `/common/provision/msl-setup.sh` and add your username and password to the git clone command

7. Once all necessary fiels have been updated run `vagrant up prod`. This will start the cassandra provisioning script and create the AWS EC2 instance

8. Once all provisioning is done, you can enter the AWS instance hitting `vagrant ssh prod`. You can then head to ~/kenzan/million-song-library/msl-pages and run the `npm run deploy` and `npm run serve-all` tasks to start the app

9. Make sure to access it in your local browser using the hostname `msl.kenzanlabs.com:3000`. Make sure to add the public ip for the AWS EC2 instance to your hosts file

10. To destroy the AWS EC2 instance run `vagrant destroy prod -f`
