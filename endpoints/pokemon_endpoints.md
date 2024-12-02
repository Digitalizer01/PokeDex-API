### Pokemon Endpoints

- **POST /pokemon**: Add a new Pokémon.
  - **Request Body**:
    ```json
    {
      "idPokedex": 1,
      "name": "Bulbasaur",
      "namePokemonType1": "Grass",
      "namePokemonType2": "Poison",
      "description": "A strange seed was planted on its back at birth.",
      "stats":
      {
        "hp": 45,
        "attack": 49,
        "defense": 49,
        "specialAttack": 65,
        "specialDefense": 65,
        "speed": 45
      },
      "idGeneration": 1,
      "legendary": false
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "idPokedex": 1,
      "name": "Bulbasaur",
      "pokemonType1":    {
          "id": 5,
          "name": "Grass"
      },
      "pokemonType2": null,
      "description": "A strange seed was planted on its back at birth.",
      "stats":
      {
          "id": 3,
          "hp": 45,
          "attack": 49,
          "defense": 49,
          "specialAttack": 65,
          "specialDefense": 65,
          "speed": 45
      },
      "generation":
      {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
      },
      "legendary": false
    }
    ```
- **POST /pokemon/batch**: Add multiple Pokémon records.
  - **Request Body**:
    ```json
    [
      {
        "idPokedex": 1,
        "name": "Bulbasaur",
        "namePokemonType1": "Grass",
        "namePokemonType2": "Poison",
        "description": "A strange seed was planted on its back at birth.",
        "stats":
        {
          "hp": 45,
          "attack": 49,
          "defense": 49,
          "specialAttack": 65,
          "specialDefense": 65,
          "speed": 45
        },
        "idGeneration": 1,
        "legendary": false
      },
      {
        "idPokedex": 2,
        "name": "Ivysaur",
        "namePokemonType1": "Grass",
        "namePokemonType2": "Poison",
        "description": "When the bulb on its back grows large, it appears to be ready to bloom.",
        "stats":
        {
          "hp": 60,
          "attack": 62,
          "defense": 63,
          "specialAttack": 80,
          "specialDefense": 80,
          "speed": 60
        },
        "idGeneration": 1,
        "legendary": false
      }
    ]

    ```
  - **Response**:
    ```json
    [
      {
        "id": 4,
        "idPokedex": 1,
        "name": "Bulbasaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "A strange seed was planted on its back at birth.",
        "stats":
        {
          "id": 4,
          "hp": 45,
          "attack": 49,
          "defense": 49,
          "specialAttack": 65,
          "specialDefense": 65,
          "speed": 45
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      },
      {
        "id": 5,
        "idPokedex": 2,
        "name": "Ivysaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "When the bulb on its back grows large, it appears to be ready to bloom.",
        "stats":
        {
          "id": 5,
          "hp": 60,
          "attack": 62,
          "defense": 63,
          "specialAttack": 80,
          "specialDefense": 80,
          "speed": 60
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      }
    ]
    ```
- **GET /pokemon/{id}**: Retrieve a Pokémon by ID.
  - **Response**:
    ```json
    {
      "id": 1,
      "idPokedex": 1,
      "name": "Bulbasaur",
      "pokemonType1":
      {
          "id": 5,
          "name": "Grass"
      },
      "pokemonType2":
      {
          "id": 8,
          "name": "Poison"
      },
      "description": "A strange seed was planted on its back at birth.",
      "stats":
      {
          "id": 4,
          "hp": 45,
          "attack": 49,
          "defense": 49,
          "specialAttack": 65,
          "specialDefense": 65,
          "speed": 45
      },
      "generation":
      {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
      },
      "legendary": false
    }
    ```
- **GET /pokemon/pokedex/{idpokedex}**: Retrieve a Pokémon by Pokedex ID.
  - **Response**:
    ```json
    {
      "id": 2,
      "idPokedex": 2,
      "name": "Ivysaur",
      "pokemonType1":
      {
          "id": 5,
          "name": "Grass"
      },
      "pokemonType2":
      {
          "id": 8,
          "name": "Poison"
      },
      "description": "When the bulb on its back grows large, it appears to be ready to bloom.",
      "stats":
      {
          "id": 5,
          "hp": 60,
          "attack": 62,
          "defense": 63,
          "specialAttack": 80,
          "specialDefense": 80,
          "speed": 60
      },
      "generation":
      {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
      },
      "legendary": false
    }
    ```
- **GET /pokemon/name/{name}**: Retrieve a Pokémon by name.
  - **Response**:
    ```json
    {
      "id": 2,
      "idPokedex": 2,
      "name": "Ivysaur",
      "pokemonType1":
      {
          "id": 5,
          "name": "Grass"
      },
      "pokemonType2":
      {
          "id": 8,
          "name": "Poison"
      },
      "description": "When the bulb on its back grows large, it appears to be ready to bloom.",
      "stats":
      {
          "id": 5,
          "hp": 60,
          "attack": 62,
          "defense": 63,
          "specialAttack": 80,
          "specialDefense": 80,
          "speed": 60
      },
      "generation":
      {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
      },
      "legendary": false
    }
    ```
- **GET /pokemon**: Retrieve all Pokémon.
  - **Response**:
    ```json
    [
      {
        "id": 1,
        "idPokedex": 1,
        "name": "Bulbasaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "A strange seed was planted on its back at birth.",
        "stats":
        {
          "id": 4,
          "hp": 45,
          "attack": 49,
          "defense": 49,
          "specialAttack": 65,
          "specialDefense": 65,
          "speed": 45
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      },
      {
        "id": 2,
        "idPokedex": 2,
        "name": "Ivysaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "When the bulb on its back grows large, it appears to be ready to bloom.",
        "stats":
        {
          "id": 5,
          "hp": 60,
          "attack": 62,
          "defense": 63,
          "specialAttack": 80,
          "specialDefense": 80,
          "speed": 60
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      }
    ]
    ```
- **GET /pokemon/type1/name/{namePokemonType1}**: Retrieve all Pokémon by primary type.
  - **Response**:
    ```json
    [
      {
        "id": 1,
        "idPokedex": 1,
        "name": "Bulbasaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "A strange seed was planted on its back at birth.",
        "stats":
        {
          "id": 4,
          "hp": 45,
          "attack": 49,
          "defense": 49,
          "specialAttack": 65,
          "specialDefense": 65,
          "speed": 45
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      },
      {
        "id": 2,
        "idPokedex": 2,
        "name": "Ivysaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "When the bulb on its back grows large, it appears to be ready to bloom.",
        "stats":
        {
          "id": 5,
          "hp": 60,
          "attack": 62,
          "defense": 63,
          "specialAttack": 80,
          "specialDefense": 80,
          "speed": 60
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      }
    ]
    ```
- **GET /pokemon/type2/name/{namePokemonType2}**: Retrieve all Pokémon by secondary type.
  - **Response**:
    ```json
    [
      {
        "id": 1,
        "idPokedex": 1,
        "name": "Bulbasaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "A strange seed was planted on its back at birth.",
        "stats":
        {
          "id": 4,
          "hp": 45,
          "attack": 49,
          "defense": 49,
          "specialAttack": 65,
          "specialDefense": 65,
          "speed": 45
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      },
      {
        "id": 2,
        "idPokedex": 2,
        "name": "Ivysaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "When the bulb on its back grows large, it appears to be ready to bloom.",
        "stats":
        {
          "id": 5,
          "hp": 60,
          "attack": 62,
          "defense": 63,
          "specialAttack": 80,
          "specialDefense": 80,
          "speed": 60
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      }
    ]
    ```
- **GET /pokemon/type1andtype2?namePokemonType1={namePokemonType1}&namePokemonType2={namePokemonType2}**: Retrieve all Pokémon by primary and optional secondary types. `namePokemonType2` can be null as well.
  - **Response**:
    ```json
    [{
      "id": 149,
      "idPokedex": 149,
      "name": "Dragonite",
      "pokemonType1":
      {
          "id": 15,
          "name": "Dragon"
      },
      "pokemonType2":
      {
          "id": 10,
          "name": "Flying"
      },
      "description": "An extremely rarely seen marine POKÃ©MON. Its intelligence is said to match that of humans.",
      "stats":
      {
          "id": 149,
          "hp": 91,
          "attack": 134,
          "defense": 95,
          "specialAttack": 100,
          "specialDefense": 100,
          "speed": 80
      },
      "generation":
      {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
      },
      "legendary": false
    }]
    ```
- **GET /pokemon/generation/region/{region}**: Retrieve all Pokémon by generation region.
  - **Response**:
    ```json
    [
      {
        "id": 1,
        "idPokedex": 1,
        "name": "Bulbasaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "A strange seed was planted on its back at birth.",
        "stats":
        {
          "id": 4,
          "hp": 45,
          "attack": 49,
          "defense": 49,
          "specialAttack": 65,
          "specialDefense": 65,
          "speed": 45
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      },
      {
        "id": 2,
        "idPokedex": 2,
        "name": "Ivysaur",
        "pokemonType1":
        {
          "id": 5,
          "name": "Grass"
        },
        "pokemonType2":
        {
          "id": 8,
          "name": "Poison"
        },
        "description": "When the bulb on its back grows large, it appears to be ready to bloom.",
        "stats":
        {
          "id": 5,
          "hp": 60,
          "attack": 62,
          "defense": 63,
          "specialAttack": 80,
          "specialDefense": 80,
          "speed": 60
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": false
      }
    ]
    ```
- **GET /pokemon/legendary?legendary={legendary}**: Retrieve all Pokémon based on legendary status. `legendary` can be `true` or `false`.
  - **Response**:
    ```json
    [
      {
        "id": 150,
        "idPokedex": 150,
        "name": "Mewtwo",
        "pokemonType1":
        {
          "id": 11,
          "name": "Psychic"
        },
        "pokemonType2": null,
        "description": "It was created by a scientist after years of horrific gene splicing and DNA engineering experiments.",
        "stats":
        {
          "id": 150,
          "hp": 106,
          "attack": 110,
          "defense": 90,
          "specialAttack": 154,
          "specialDefense": 90,
          "speed": 130
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": true
      },
      {
        "id": 151,
        "idPokedex": 151,
        "name": "Mew",
        "pokemonType1":
        {
          "id": 11,
          "name": "Psychic"
        },
        "pokemonType2": null,
        "description": "So rare that it is still said to be a mirage by many experts. Only a few people have seen it worldwide.",
        "stats":
        {
          "id": 151,
          "hp": 100,
          "attack": 100,
          "defense": 100,
          "specialAttack": 100,
          "specialDefense": 100,
          "speed": 100
        },
        "generation":
        {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
        },
        "legendary": true
      }
    ]
    ```
- **PUT /pokemon/{id}**: Update a Pokémon by ID.
  - **Request Body**:
    ```json
    {
      "idPokedex": 1,
      "name": "Bulbasaur",
      "namePokemonType1": "Grass",
      "namePokemonType2": "Poison",
      "description": "A strange seed was planted on its back at birth.",
      "stats":
      {
        "hp": 99,
        "attack": 100,
        "defense": 149,
        "specialAttack": 165,
        "specialDefense": 165,
        "speed": 145
      },
      "idGeneration": 1,
      "legendary": true
    }
    ```
  - **Response**:
    ```json
    {
      "id": 4,
      "idPokedex": 1,
      "name": "Bulbasaur",
      "pokemonType1":
      {
          "id": 5,
          "name": "Grass"
      },
      "pokemonType2":
      {
          "id": 8,
          "name": "Poison"
      },
      "description": "A strange seed was planted on its back at birth.",
      "stats":
      {
          "id": 4,
          "hp": 99,
          "attack": 100,
          "defense": 149,
          "specialAttack": 165,
          "specialDefense": 165,
          "speed": 145
      },
      "generation":
      {
          "id": 1,
          "number": 1,
          "region": "Kanto",
          "year": 1996
      },
      "legendary": true
    }
    ```
- **DELETE /pokemon/{id}**: Delete a Pokémon by ID.

---
