# Plant Growth Identity Service

This project implements a custom Keycloak SPI (Service Provider Interface) for permission mapping. It extends Keycloak's functionality by providing a custom protocol mapper that can be used to add specific permissions to user tokens.

## Features

* Custom Protocol Mapper for user permissions
* Integration with Keycloak's token service
* Flexible permission mapping configuration

## Prerequisites

* Java 11 or higher
* Maven 3.6 or higher
* Keycloak server (tested with version 22.0.0 or higher)

## Building the Project

To build the project, run:

```bash
mvn clean package
```

This will create a JAR file in the `target` directory.

## Installation

1. Stop your Keycloak server if it's running
2. Copy the generated JAR file from `target/plant-growth-identity-service-{version}.jar` to Keycloak's `providers` directory
3. Start Keycloak server
4. The new protocol mapper will be available in the Keycloak admin console

## Configuration

1. Log into the Keycloak Admin Console
2. Select your realm
3. Go to Client Scopes
4. Create or edit a client scope
5. Add the Permission Protocol Mapper to the client scope
6. Configure the mapper according to your requirements

## Development

The project contains the following main components:

* `PermissionProtocolMapper.java` - The main protocol mapper implementation
* `PermissionMapperFactory.java` - Factory class for creating protocol mapper instances

## Contributing

Please feel free to submit issues and pull requests.

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.
