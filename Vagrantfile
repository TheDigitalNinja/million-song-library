Vagrant.configure(2) do |config|

  # Inline script ------------------------------
  $git_setup = <<SCRIPT
  PROJECT_PATH = /vagrant
  eval "$(ssh-agent -s)";
  ssh-add ./.ssh/id_rsa
  ssh-keyscan github.com >> ./.ssh/known_hosts
  sudo apt-get -y install git-core
  cd $PROJECT_PATH
SCRIPT

  $java_setup = <<SCRIPT
  if [[ $(uname -s) =~ Linux* ]]; then sudo apt-get -y update && sudo apt-get -y upgrade; fi

  sudo apt-get install -y software-properties-common python-software-properties
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
  sudo add-apt-repository ppa:webupd8team/java -y
  sudo apt-get update
  sudo apt-get install oracle-java8-installer
  echo "Setting environment variables for Java 8.."
  sudo apt-get install -y oracle-java8-set-default
SCRIPT

  $main_setup = <<SCRIPT
    cd /Users/anram88/Documents/Pernix/KenzanMedia/million-song-library/common
    sudo ./setup.sh -c ~/cassandra/dsc-cassandra-2.2.3/ -s -n
SCRIPT

    config.vm.define "ubuntu" do |ubuntu|
      ubuntu.vm.box = "ubuntu/trusty64"
      ubuntu.vm.hostname = "web-dev"

      ubuntu.vm.synced_folder ".", "/Users/anram88/Documents/Pernix/KenzanMedia/million-song-library/"

      # Provisions
      ubuntu.vm.provision "file", source: "~/.gitconfig", destination: "./.gitconfig"
      ubuntu.vm.provision "file", source: "~/.ssh/id_rsa", destination: "./.ssh/id_rsa"
      ubuntu.vm.provision "file", source: "~/.ssh/id_rsa.pub", destination: "./.ssh/id_rsa.pub"
      ubuntu.vm.provision "file", source: "/Applications/dsc-cassandra-2.2.3", destination: "./cassandra/dsc-cassandra-2.2.3", run: "always"

      ubuntu.vm.provision "shell", inline: $git_setup
      ubuntu.vm.provision "shell", inline: $java_setup
      ubuntu.vm.provision "shell", inline: $main_setup

      ubuntu.vm.provider "virtualbox" do |v|
        v.memory = 2000
        v.customize ["modifyvm", :id, "--cpuexecutioncap", "90"]
      end
    end

end