﻿
foreach ($arg in $args) {
    if($CASSANDRA_PATH -eq $true) { $CASSANDRA_PATH = $arg }
    if ($arg -eq "-c")
    {
        $RUN_CASSANDRA=$true
        $CASSANDRA_PATH=$true
    }
    if ($arg -eq "-n"){ $RUN_NODE=$true }
    if ($arg -eq "-s"){ $RUN_SERVER=$true }
    if ($arg -eq "-g"){ $RUN_GIT=$true }
}

$CURRENT=Get-Location
$PROJECT_PATH=Split-Path $CURRENT -Parent

function reload ()
{
    #$env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine")
    @(
        $Profile.AllUsersAllHosts,
        $Profile.AllUsersCurrentHost,
        $Profile.CurrentUserAllHosts,
        $Profile.CurrentUserCurrentHost
    ) | % {
        if(Test-Path $_){
            Write-Verbose "Running $_"
            . $_
        }
    }
}

Function error-handler ($lastCommand, $msg)
{
    if ($lastCommand -ne $true)
    {
       $msg
       Exit
    }
}

Function test-chocolatey
{
    $oldPreference = $ErrorActionPreference
    $ErrorActionPreference = 'stop'
    try {if (Get-Command chocolatey){"command chocolatey exists "}}
    Catch
    {
        "chocolatey doesn't exist"
        error-handler $false "Please install chocolatey before continuing... "
    }
    Finally {$ErrorActionPreference=$oldPreference}
}

Function test-maven
{
    $oldPreference = $ErrorActionPreference
    $ErrorActionPreference = 'stop'
    try {if (Get-Command mvn){"maven already installed..."}}
    Catch
    {
        "Installing maven..."
        choco install maven -y
        error-handler $? "Unable to install maven"
        reload
        choco upgrade maven -y
        error-handler $? "Unable to upgrade maven"
        reload
        # add ~/.mavenrc
    }
    Finally {$ErrorActionPreference=$oldPreference}

}

Function test-python
{
    $oldPreference = $ErrorActionPreference
    $ErrorActionPreference = 'stop'
    try {if (Get-Command python){"python already installed..."}}
    Catch
    {
        "Installing python..."
        choco install python2 -y
        error-handler $? "Unable to install python"
        reload
        choco upgrade python2 -y
        error-handler $? "Unable to upgrade python"
        reload
    }
    Finally {$ErrorActionPreference=$oldPreference}

}

Function test-npm
{
    $oldPreference = $ErrorActionPreference
    $ErrorActionPreference = 'stop'
    try {if (Get-Command npm){"node.js already installed..."}}
    Catch
    {
        "Installing node.js... "
        choco install nodejs.install -y
        error-handler $? "Unable to install node.js"
        reload
        choco upgrade nodejs.install -y
        error-handler $? "Unable to upgrade node.js"
        reload
    }
    Finally {$ErrorActionPreference=$oldPreference}
}

Function test-bower
{
    $oldPreference = $ErrorActionPreference
    $ErrorActionPreference = 'stop'
    try {if (Get-Command bower){"bower already installed..."}}
    Catch
    {
        "Installing bower... "
        npm install -g bower
        error-handler $? "Unable to install bower"
        reload
    }
    Finally {$ErrorActionPreference=$oldPreference}
}

Function test-git
{
    $oldPreference = $ErrorActionPreference
    $ErrorActionPreference = 'stop'
    try {if (Get-Command git){"git already installed..."}}
    Catch
    {
        "Installing git... "
        choco install git -y
        error-handler $? "Unable to install git"
        reload
        choco upgrade git -y
        error-handler $? "Unable to update git"
        reload
    }
    Finally {$ErrorActionPreference=$oldPreference}
}

Function test-rubyGem
{
    $oldPreference = $ErrorActionPreference
    $ErrorActionPreference = 'stop'
    try {if (Get-Command gem){"rubygems already installed..."}}
    Catch
    {
        "Installing rubygems..."
        choco install ruby -y
        error-handler $? "Unable to install rubygems"
        reload
        choco upgrade ruby -y
        error-handler $? "Unable to upgrade rubygems"
        reload
    }
    Finally {$ErrorActionPreference=$oldPreference}
}

Function test-asciidoctor
{
    $oldPreference = $ErrorActionPreference
    $ErrorActionPreference = 'stop'
    try {if (Get-Command asciidoctor){"asciidoctor already installed..."}}
    Catch
    {
        "Installing asciidoctor"
        gem install asciidoctor
        error-handler $? "Unable to install asciidoctor"
        reload
    }
    Finally {$ErrorActionPreference=$oldPreference}
}

Function test-java{if ([string]::IsNullOrEmpty($JAVA_HOME)) { "JAVA_HOME is not set. Please see about installing java 1.8"}}

# GLOBAL CONFIGURATION ===========================
test-chocolatey
test-java
test-python
test-maven
test-git
test-npm
test-bower
test-rubyGem
test-asciidoctor

# ADD HOSTNAME ===================================

Function add-hostname
{
    ping msl.kenzanlabs.com
    if ( $? -ne $true)
    {
        $HOST_FILE="C:\Windows\System32\drivers\etc\hosts"
        Add-Content -Path $HOST_FILE -Value "127.0.0.1 msl.kenzanlabs.com"
        error-handler $? "Unable to add hostname"
        "Added msl.kenzanlabs.com"
    } else
    {
        "msl.kenzanlabs.com is already on C:\Windows\system32\drivers\etc\hosts"
    }
}

# GIT SETUP ======================================
Function git-setup
{
    cd $PROJECT_PATH
    git submodule init
    git submodule sync
    error-handler $? "unable to git submodule init, please verify ssh"
    git submodule update --init
}

# MSL-PAGES SETUP ================================

Function msl-pages-setup
{
    cd $PROJECT_PATH\msl-pages
    if (Test-Path .\node_modules)
    {
        Remove-Item .\node_modules -Force -Recurse
        npm cache clean
    }
    if (Test-Path .\bower_components)
    {
        Remove-Item .\bower_components -Force -Recurse
        bower cache clean
    }

    npm -g install npm@latest
    npm install -y
    error-handler $? "Unable to run npm install"
    bower install --allow-root
    error-handler $? "Unable to run bower install"

    if ( Test-Path $PROJECT_PATH\msl-pages\swagger)
    {
        Remove-Item $PROJECT_PATH\msl-pages\swagger
    }
    if (Test-Path $PROJECT_PATH\common\node_modules)
    {
        Remove-Item $PROJECT_PATH\common\node_modules
    }
    cmd /c mklink /J $PROJECT_PATH\msl-pages\swagger $PROJECT_PATH\common\swagger
    cmd /c mklink /J $PROJECT_PATH\common\node_modules $PROJECT_PATH\msl-pages\node_modules

    npm run generate-swagger-html

    npm install -g -y webpack
    error-handler $? "unable to install webpack"
    npm install -g -y protractor
    error-handler $? "Unable to install protractor"
    npm install -g -y selenium-webdriver
    error-handler $? "Unable to install selenium-webdriver"

}

# SERVER SETUP ===================================

Function server-setup
{
    cd $PROJECT_PATH\server
    java -version
    mvn -version
    mvn clean compile
    error-handler $? "failed at running main maven file under /server"
}

# CASSANDRA SETUP ================================

Function cassandra-setup
{
    if (Test-Path $CASSANDRA_PATH\bin)
    {
        "RUNNING CASSANDRA ..."

        cd "$PROJECT_PATH\tools\cassandra"

        python $CASSANDRA_PATH\bin\cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
        if ( $? -ne $true)
        {
            START powershell -WindowStyle Normal "$CASSANDRA_PATH\bin\cassandra.bat"
            Start-Sleep -s 30
            python $CASSANDRA_PATH\bin\cqlsh -e "SOURCE 'msl_ddl_latest.cql';";

            while ($? -ne $true)
            {
                Start-Sleep -s 30
                python $CASSANDRA_PATH\bin\cqlsh -e "SOURCE 'msl_ddl_latest.cql';";
            }
        }
        error-handler $? "unable to run cqlsh -> msl_ddl_lates.cql. Check if cassandra is running and run with admin rights .\setup.ps1 -c $CASSANDRA_PATH"

        python $CASSANDRA_PATH\bin\cqlsh -e "SOURCE 'msl_dat_latest.cql';";
        error-handler $? "unable to run cqlsh -> msl_dat_lates.cql"
    }
    else
    {
        echo "CHECK CASSANDRA FOLDER PROVIDED"
        echo "SKIPPING CASSANDRA SETUP"
        echo "See about downloading it in: https://downloads.datastax.com/community/"
        echo "Suggested version: dsc-cassandra-2.1.11"
    }
}

add-hostname

if ($RUN_GIT -eq $true) { git-setup }
if ($RUN_NODE -eq $true) { msl-pages-setup }
if ($RUN_SERVER -eq $true) { server-setup }
if ($RUN_CASSANDRA -eq $true) { cassandra-setup }
