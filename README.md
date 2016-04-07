# Kenzan Million Song Library

The Million Song Library (MSL) project is a microservices-based Web application built using [AngularJS](https://angularjs.org/), a [Cassandra](http://cassandra.apache.org/) NoSQL database, and several [Netflix OSS](http://netflix.github.io/) tools such as Karyon, Zuul, and Eureka.

At [Kenzan](http://kenzan.com/), we created the Million Song Library project to demonstrate the advantages of a microservices architecture, as well as the flexibility and capability offered by the Netflix OSS components when paired with a Cassandra database. However, the MSL project is more than just a demonstration. It also provides a foundation on which database-driven applications can be rapidly developed, tested, and deployed to the cloud.

To learn more about the MSL microservices architecture and the tools we used to build it, see the [Million Song Library Project Documentation](https://github.com/kenzanmedia/million-song-library/tree/develop/docs).

## Getting Started

You can run the Million Song Library demonstration locally on a Mac, Linux, or Windows computer. Or deploy it to [Amazon Web Services](https://aws.amazon.com/) (AWS) and run it on an EC2 instance.

There are three ways to run the Million Song Library demonstration:

- **Automated Setup** – Uses the `setup.sh` script (located in the `/common` directory) to automate much of the setup process. This is the quickest method for running the MSL demonstration locally on a Mac, Linux, or Windows system.

- **Manual Setup** – Also runs the MSL demonstration locally on a Mac, Linux, or Windows system. This method takes more time but lets you control how the various tools are installed on your system.

- **AWS Setup** – Deploys the MSL demonstration to an EC2 instance on AWS. This method uses [Vagrant](https://www.vagrantup.com/) to automate the cloud deployment process.

For step-by-step instructions for each setup method, see the [Million Song Library Project Documentation](https://github.com/kenzanmedia/million-song-library/tree/develop/docs).

## Documentation

Use the following resources (located in the `/docs` directory) to learn more about the Kenzan Million Song Library:

- [**Million Song Library Project Documentation**](https://github.com/kenzanmedia/million-song-library/tree/develop/docs) – Overview of the Million Song Library microservices-based architecture as well as step-by-step instructions for running the MSL demonstration locally or deploying it to AWS.

- [**API Documentation**](https://github.com/kenzanmedia/million-song-library/blob/develop/docs/swagger/index.html) – Million Song Library API documentation, generated using Swagger.

- [**Service Documentation**](https://github.com/kenzanmedia/million-song-library/tree/develop/docs) – Description of the classes and methods for each Million Song Library microservice, generated using Javadoc.

- [**Client/UI Documentation**](https://github.com/kenzanmedia/million-song-library/tree/develop/docs) – Classes, functions, and variables for the Million Song Library client/UI, generated using ESDoc.

- [**CSS Style Guide**](https://github.com/kenzanmedia/million-song-library/tree/develop/docs) – CSS styles used in the Million Song Library client/UI, generated using KSS.

## License

© 2016 Kenzan Media, LLC.

The Kenzan Million Song Library project is licensed under the **TBD License**.
<!---
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
--->

## Support

If you have questions about the Million Song Library demonstration, feel free to drop us a line at <support_msl@kenzan.com>.
