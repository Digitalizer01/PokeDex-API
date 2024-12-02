### **PokemonType Endpoints**

- **POST /pokemontype**: Add a new Pokémon type.
  - **Request Body**:
    ```json
    {
      "name": "Electric"
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "name": "Electric"
    }
    ```

- **POST /pokemontype/batch**: Add multiple Pokémon types.
  - **Request Body**:
    ```json
    [
      { "name": "Grass" },
      { "name": "Poison" }
    ]
    ```
  - **Response**:
    ```json
    [
      { "id": 1, "name": "Grass" },
      { "id": 2, "name": "Poison" }
    ]
    ```

- **GET /pokemontype/{id}**: Retrieve a specific Pokémon type by ID.
  - **Response**:
    ```json
    {
      "id": 1,
      "name": "Electric"
    }
    ```

- **GET /pokemontype**: Retrieve all Pokémon types.
  - **Response**:
    ```json
    [
      { "id": 1, "name": "Electric" },
      { "id": 2, "name": "Grass" }
    ]
    ```

- **PUT /pokemontype/{id}**: Update a Pokémon type by ID.
  - **Request Body**:
    ```json
    {
      "name": "Fire"
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "name": "Fire"
    }
    ```

- **DELETE /pokemontype/{id}**: Delete a Pokémon type by ID.

---
