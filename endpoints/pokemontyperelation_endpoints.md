### **PokemonTypeRelation Endpoints**

- **POST /pokemontyperelation**: Add a new type relation.
  - **Request Body**:
    ```json
    {
      "namePokemonType": "Fire",
      "nameRelatedPokemonType": "Grass",
      "effectivenessPercentage": 200
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "namePokemonType": "Fire",
      "nameRelatedPokemonType": "Grass",
      "effectivenessPercentage": 200
    }
    ```

- **POST /pokemontyperelation/batch**: Add multiple type relations.
  - **Request Body**:
    ```json
    [
      {
        "namePokemonType": "Water",
        "nameRelatedPokemonType": "Fire",
        "effectivenessPercentage": 200
      },
      {
        "namePokemonType": "Fire",
        "nameRelatedPokemonType": "Grass",
        "effectivenessPercentage": 200
      }
    ]
    ```
  - **Response**:
    ```json
    [
      {
      "id": 326,
      "pokemonType":
      {
        "id": 3,
        "name": "Water"
      },
      "relatedPokemonType":
      {
        "id": 2,
        "name": "Fire"
      },
      "effectivenessPercentage": 200
      },
      {
        "id": 327,
        "pokemonType":
        {
          "id": 2,
          "name": "Fire"
        },
        "relatedPokemonType":
        {
          "id": 5,
          "name": "Grass"
        },
        "effectivenessPercentage": 200
      }
    ]
    ```

- **GET /pokemontyperelation/{id}**: Retrieve a specific type relation by ID.
  - **Response**:
    ```json
    {
      "id": 1,
      "pokemonType":
      {
          "id": 1,
          "name": "Normal"
      },
      "relatedPokemonType":
      {
          "id": 1,
          "name": "Normal"
      },
      "effectivenessPercentage": 100
    }
    ```

- **GET /pokemontyperelation/effectiveness/pair/{namePokemonType}/{nameRelatedPokemonType}**: Retrieve effectiveness percentage between two types.
  - **Response**:
    ```json
    50
    ```

- **GET /pokemontyperelation/effectiveness/list/{namePokemonType}/{effectivenessPercentage}**: Retrieve type relations for a type with a specific effectiveness.
  - **Response**:
    ```json
    [
      {
        "id": 13,
        "pokemonType":
        {
          "id": 1,
          "name": "Normal"
        },
        "relatedPokemonType":
        {
          "id": 13,
          "name": "Rock"
        },
        "effectivenessPercentage": 50
      },
      {
        "id": 17,
        "pokemonType":
        {
          "id": 1,
          "name": "Normal"
        },
        "relatedPokemonType":
        {
          "id": 17,
          "name": "Steel"
        },
        "effectivenessPercentage": 50
      }
  ]
  ```

- **GET /pokemontyperelation**: Retrieve all type relations.
  - **Response**:
    ```json
    [
      {
        "id": 13,
        "pokemonType":
        {
          "id": 1,
          "name": "Normal"
        },
        "relatedPokemonType":
        {
          "id": 13,
          "name": "Rock"
        },
        "effectivenessPercentage": 50
      },
      {
        "id": 17,
        "pokemonType":
        {
          "id": 1,
          "name": "Normal"
        },
        "relatedPokemonType":
        {
          "id": 17,
          "name": "Steel"
        },
        "effectivenessPercentage": 50
      }
  ]
  ```

- **PUT /pokemontyperelation/{id}**: Update a type relation by ID.
  - **Request Body**:
    ```json
    {
      "namePokemonType": "Normal",
      "nameRelatedPokemonType": "Normal",
      "effectivenessPercentage": 200
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "pokemonType":
      {
          "id": 1,
          "name": "Normal"
      },
      "relatedPokemonType":
      {
          "id": 1,
          "name": "Normal"
      },
      "effectivenessPercentage": 200
    }
    ```

- **DELETE /pokemontyperelation/{id}**: Delete a type relation by ID.

---
