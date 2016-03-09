Vagrant.configure(2) do |config|

  config.ssh.forward_agent = true

  def provisioning (config, args)
    config.vm.synced_folder ".", "/vagrant", disabled: true
    config.vm.provision "file", source: "~/.gitconfig", destination: "./.gitconfig"
    config.vm.provision "file", source: "~/.ssh/known_hosts", destination: "./.ssh/known_hosts"
    config.vm.provision "file", source: "./common/provision/msl-setup.sh", destination: "./msl-setup.sh", run: "always"

    config.vm.provision "shell", path: "./common/provision/git-setup.sh"
    config.vm.provision "shell", path: "./common/provision/java-setup.sh"
    config.vm.provision "shell", path: "./common/provision/msl-setup.sh", privileged: false
  end

    config.vm.define "ubuntu" do |ubuntu|
      ubuntu.vm.box = "ubuntu/trusty64"
      ubuntu.vm.hostname = "web-dev"
      ubuntu.vm.provision "file", source: "~/.ssh/id_rsa", destination: "./.ssh/id_rsa"
      ubuntu.vm.provision "file", source: "~/.ssh/id_rsa.pub", destination: "./.ssh/id_rsa.pub"

      provisioning(ubuntu, [])

      ubuntu.vm.provider "virtualbox" do |v|
        v.memory = 2000
        v.customize ["modifyvm", :id, "--cpuexecutioncap", "90"]
      end
    end

    config.vm.define "prod" do |prod|
      prod.vm.box = "dummy"
      prod.vm.hostname="web-prod"
      prod.vm.provision "file", source: "<<PATH_TO_SSH_PRIVATE_KEY>>", destination: "./.ssh/id_rsa"
      prod.vm.provision "file", source: "<<PATH_TO_SSH_PUBLIC_KEY>>", destination: "./.ssh/id_rsa.pub"

      provisioning(prod, [])

      prod.vm.provider "aws" do |aws, override|
        aws.region_config "us-west-2", :ami => "ami-9abea4fb"
        aws.region = "us-west-2"
        aws.instance_type="t2.small"
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