Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.hostname = "msl.kenzanlabs.com"
  
  # Provisions
  config.vm.provision "file", source: "~/.gitconfig", destination: "./.gitconfig"
  config.vm.provision "file", source: "~/.ssh/id_rsa", destination: "./.ssh/id_rsa"
  config.vm.provision "file", source: "~/.ssh/id_rsa.pub", destination: "./.ssh/id_rsa.pub"
  config.vm.provision "file", source: "/Applications/dsc-cassandra-2.2.3", destination: "./cassandra/dsc-cassandra-2.2.3", run: "always"

  # Inline script
  $script = <<SCRIPT
  PROJECT_PATH = /vagrant
  eval "$(ssh-agent -s)";
  ssh-add ./.ssh/id_rsa
  ssh-keyscan github.com >> ./.ssh/known_hosts
  sudo apt-get -y install git-core
  cd $PROJECT_PATH
SCRIPT

  config.vm.provision "shell", inline: $script
  config.vm.provision "shell", inline: "cd /vagrant/common/ && ./setup.sh"
  config.vm.provider "virtualbox" do |v|
    v.name = "facturacion_project"
    v.memory = 512
    v.customize ["modifyvm", :id, "--cpuexecutioncap", "90"]
  end
end