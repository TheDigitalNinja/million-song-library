Vagrant.configure(2) do |config|

  config.ssh.forward_agent = true

  def provisioning (config, args)
    config.vm.provision "file", source: args[0], destination: "./.ssh/id_rsa.pub"
    config.vm.provision "file", source: args[1], destination: "./.ssh/id_rsa"

    config.vm.synced_folder ".", "/vagrant", disabled: true
    config.vm.provision "file", source: "~/.gitconfig", destination: "./.gitconfig"
    config.vm.provision "file", source: "~/.ssh/known_hosts", destination: "./.ssh/known_hosts"
    config.vm.provision "file", source: "./common/provision/msl-setup.sh", destination: "./msl-setup.sh", run: "always"

    config.vm.provision "shell", path: "./common/provision/git-setup.sh" , privileged: false
    config.vm.provision "shell", path: "./common/provision/java-setup.sh", privileged: false
    config.vm.provision "shell", path: "./common/provision/msl-setup.sh", privileged: false
  end

    ##############################
    #                     UBUNTU #
    ##############################
    config.vm.define "ubuntu" do |ubuntu|
      ubuntu.vm.box = "ubuntu/trusty64"
      ubuntu.vm.hostname = "ubuntutrusty"
      provisioning(ubuntu, [ "~/.ssh/id_rsa.pub",  "~/.ssh/id_rsa"])

      ubuntu.vm.provider "virtualbox" do |v|
        v.memory = 4000
        v.customize ["modifyvm", :id, "--cpuexecutioncap", "90"]
      end
    end

    ##################################################
    #                                            MAC #
    ##################################################
    # https://github.com/AndrewDryga/vagrant-box-osx #
    # ---------------------------------------------- #

    config.vm.define "mac" do |mac|
      mac.vm.box = "AndrewDryga/vagrant-box-osx"
      mac.vm.hostname = "macosx"
      provisioning(mac, ["~/.ssh/id_rsa.pub", "~/.ssh/id_rsa"])

      mac.vm.provider "virtualbox" do |v|
        v.memory = 5000
        v.customize ["modifyvm", :id, "--cpuexecutioncap", "90"]
      end
    end

    #############################################
    #                                AWS-UBUNTU #
    #############################################
    # https://github.com/mitchellh/vagrant-aws  #
    # ----------------------------------------- #

    config.vm.define "awsUbuntu" do |awsUbuntu|
      awsUbuntu.vm.box = "dummy"
      awsUbuntu.vm.hostname="ubuntuAws"

      provisioning(awsUbuntu, [ "<<PATH_TO_SSH_PUBLIC_KEY>>", "<<PATH_TO_SSH_PRIVATE_KEY>>" ])

      awsUbuntu.vm.provider "aws" do |aws, override|
        aws.region_config "us-west-2", :ami => "ami-9abea4fb"
        aws.region = "us-west-2"
        aws.instance_type="m4.xlarge"
        aws.tags = { 'Name': 'Production-aws' }
        aws.keypair_name = "<<KEY_PAIR_NAME>>"

        override.ssh.username = "ubuntu"
        override.ssh.private_key_path = "<<PATH_TO_YOUR_AWS_KEY.pem>>"

        aws.access_key_id = "<<AWS_ACCESS_KEY_ID>>"
        aws.secret_access_key = "<<AWS_SECRET_ACCESS_KEY>>"
        aws.security_groups = [ '<<SECURITY_GROUP_WITH_SSH_AND_HTTP_PORTS_ENABLED>>' ]
      end
    end

end