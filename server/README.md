# MSL Microservices

The Million Song Library demonstration uses the following microservices:

- [msl-account-data-client](https://github.com/kenzanmedia/msl-account-data-client)
- [msl-account-edge](https://github.com/kenzanmedia/msl-account-edge)
- [msl-catalog-data-client](https://github.com/kenzanmedia/msl-catalog-data-client)
- [msl-catalog-edge](https://github.com/kenzanmedia/msl-catalog-edge)
- [msl-login-edge](https://github.com/kenzanmedia/msl-login-edge)
- [msl-ratings-data-client](https://github.com/kenzanmedia/msl-ratings-data-client)
- [msl-ratings-edge](https://github.com/kenzanmedia/msl-ratings-edge)

Each microservice is maintained in a separate sub-repository. Configuration for each service (except for **msl-ratings-edge**) is also maintained in a separate sub-repository.

> **NOTE:** Common components used across all microservices are maintained in the [msl-server-common](https://github.com/kenzanmedia/msl-server-common) sub-repository.

## Build Script

You can build all of the MSL microservices at once by running the Maven build script from the `/server` directory of the main [million-song-library](https://github.com/kenzanmedia/million-song-library/tree/develop/server) repository:

```
mvn clean compile 
```

The build script generates server source code from the Swagger definition, packages local jars, and installs the services in the local repository.

To build, run, and test a single microservice, see the README in each microservice sub-repository.

> **NOTE:** If you receive an error when running the build script, try using `sudo` (Mac and Linux) or run PowerShell as an administrator (Windows).
