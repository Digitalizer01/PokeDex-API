# Pokedex API

This project is a REST API designed for managing a Pokedex database, including Pokémon details, types, type relationships, stats, and generations.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Description](#project-description)
- [Setup](#setup)
- [Data Model Overview](#data-model-overview)
- [API Endpoints](#api-endpoints)
  - [Stats Endpoints](endpoints/stats_endpoints.md)
  - [PokemonType Endpoints](endpoints/pokemontype_endpoints.md)
  - [PokemonTypeRelation Endpoints](endpoints/pokemontyperelation_endpoints.md)
  - [Generation Endpoints](endpoints/generation_endpoints.md)
  - [Pokemon Endpoints](endpoints/pokemon_endpoints.md)
- [TODO](#todo)

## Technologies Used

- Java 17
- JUnit
- Spring Boot
- JPA/Hibernate
- Docker
- MariaDB

## Project Description

This API provides a structured way to manage data commonly found in a Pokedex, such as Pokémon attributes, types, and type relationships. The project structure includes controllers for handling requests and services for processing data.

## Setup

### Prerequisites

- Windows or Linux
- Java 17
- Docker

1. **Create a Docker Volume:**
    First, create a volume to persist MariaDB data:
    ```bash
    docker volume create my_mariadb_volume
    ```
2. **Run the MariaDB Container:**
    Start a MariaDB container and map the volume:
    ```bash
    docker run --name my_mariadb_container -e MARIADB_ROOT_PASSWORD=my-secret-pw -v my_mariadb_volume:/var/lib/mysql -p 3306:3306 -d mariadb:11.4.2
    ```
    `my_mariadb_container`: Name for the MariaDB Docker container.

    `my-secret-pw`: The password for accesing the database. It is recommended to change it.

    `my_mariadb_volume`: Name for the volume where the data is going to be hosted.
  
3. **Access the Container:** 
    To access the MariaDB container, run the following command in your terminal:
    ```bash
    docker exec -it my_mariadb_container bash
    ```
    This will open an interactive shell session inside the MariaDB container.

4. **Log into MariaDB:** 
    Once inside the container, log into MariaDB with the following command:
    ```bash
    mariadb -u root -p
    ```
    You will be prompted to enter the password. Use the password set during the container setup (e.g., `my-secret-pw`).

5. **Create the Database:** 
    After successfully logging into MariaDB, create a new database named pokedex by running:
    ```sql
    CREATE DATABASE pokedex;
    ```
    Switch to the newly created database:
    ```sql
    USE pokedex;
    ```

6. **Fill the Database with data:** 
  `pokedex_init.sql` contains the structure of the database and some initial data that is basic for the correct behaviour of the application.
    You can paste all the script in the console to make it work.
    - **[pokedex_init.sql](pokedex_init.sql)**: Initializes the database schema and basic data.
    
    In case you want some additional information, you can do the same with **[pokedex_list.sql](pokedex_list.sql)**, which will add the first 151 Pokémon and their stats to the database.

7. **Java Installed:**  
   Ensure you have a compatible version of Java installed (e.g., Java 17).  
   - To check if Java is installed, run the following command in a terminal:  
     ```bash
     java -version
     ```
   - If Java is not installed, download and install it from [https://www.oracle.com/java/technologies/javase-downloads.html](https://www.oracle.com/java/technologies/javase-downloads.html).

8. **Database Configuration:**  
   Update the `application.properties` file with the correct database `url`, `username`, and `password`.

### Running the Application

1. Place the following files in the same directory:
   - `PokeDex-X.X.X-SNAPSHOT.jar` (the application file)
   - `application.properties` (the configuration file)

2. **Edit the `application.properties` file:**  
   Open the file in a text editor and update the following properties based on your database setup:

    `<your-database-host>`: The host of your database server (e.g., localhost for local or an IP/domain for remote servers).

    `<port>`: The port number your database server is listening on (default for MariaDB is `3306`).

    `<your-username>`: The username used to connect to the database (e.g., `root` for default MariaDB setup).

    `<your-password>`: The password associated with the database username for authentication.
    
   ```properties
    server.address=0.0.0.0
    spring.application.name=PokeDex
    spring.datasource.url=jdbc:mariadb://<your-database-host>:<port>/pokedex
    spring.datasource.username=<your-username>
    spring.datasource.password=<your-password>
    spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
    
3. **Run the Application:**
    Double-click the `PokeDex-X.X.X-SNAPSHOT.jar` file to start the application. Alternatively, you can run it from a terminal:
    ```bash
     java -jar PokeDex-X.X.X-SNAPSHOT.jar
     ```

4. Once the application starts, it will be accessible at:
http://localhost:8080 (or the address configured in `application.properties`).

## Data Model Overview


### **Stats**
  - **Description**: Represents the basic statistical attributes of a Pokémon.
  - **Fields**:
    - `id`: Unique identifier (auto-generated).
    - `hp`: Health points.
    - `attack`: Physical attack power.
    - `defense`: Physical defense power.
    - `specialAttack`: Special attack power.
    - `specialDefense`: Special defense power.
    - `speed`: Pokémon's speed.

  ### **PokemonTypeRelation**
  - **Description**: Defines the relationships between Pokémon types (e.g., Fire is effective against Grass).
  - **Fields**:
    - `id`: Unique identifier (auto-generated).
    - `pokemonType`: The primary Pokémon type (e.g., "Fire").
    - `relatedPokemonType`: The related Pokémon type (e.g., "Grass").
    - `effectivenessPercentage`: Effectiveness of the primary type against the related type (NO_EFFECT(0), NOT_VERY_EFFECTIVE(50), NORMAL(100), SUPER_EFFECTIVE(200)).

  ### **PokemonType**
  - **Description**: Represents the various Pokémon types.
  - **Fields**:
    - `id`: Unique identifier (auto-generated).
    - `name`: Name of the Pokémon type (e.g., "Water").

  ### **Generation**
  - **Description**: Represents Pokémon generations with associated region and release year.
  - **Fields**:
    - `id`: Unique identifier (auto-generated).
    - `number`: The generation number (e.g., 1 for the first generation).
    - `region`: The region associated with the generation (e.g., "Kanto").
    - `year`: The release year of the generation.

  ### **Pokemon**
  - **Description**: Represents individual Pokémon records with their attributes and characteristics.
  - **Fields**:
    - `id`: Unique identifier (auto-generated).
    - `idPokedex`: Pokedex number of the Pokémon.
    - `name`: Name of the Pokémon (e.g., "Bulbasaur").
    - `pokemonType1`: Primary type of the Pokémon.
    - `pokemonType2`: Secondary type of the Pokémon (if any).
    - `description`: A short description of the Pokémon.
    - `stats`: Embedded stats object with the Pokémon's attributes.
    - `generation`: The generation to which the Pokémon belongs.
    - `legendary`: Boolean indicating if the Pokémon is legendary.

## API Endpoints

The following sections describe the API endpoints for different resources:

- [Stats Endpoints](endpoints/stats_endpoints.md)
- [PokemonType Endpoints](endpoints/pokemontype_endpoints.md)
- [PokemonTypeRelation Endpoints](endpoints/pokemontyperelation_endpoints.md)
- [Generation Endpoints](endpoints/generation_endpoints.md)
- [Pokemon Endpoints](endpoints/pokemon_endpoints.md)

## TODO

- 🔳 Pokémon names by language.
- 🔳 Pokémon descriptions by language.
- 🔳 Region names by language.
- 🔳 Pokémon descriptions by game.
- 🔳 Pokémon descriptions by generation.
- 🔳 Link evolutions to Pokémon and their requirements (action, item, etc.).
- 🔳 Add Pokémon abilities and their descriptions by language.