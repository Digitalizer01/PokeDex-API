### Generation Endpoints

- **POST /generation**: Add a new generation.
  - **Request Body**:
    ```json
    {
      "number": 1,
      "region": "Kanto",
      "year": 1996
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "number": 1,
      "region": "Kanto",
      "year": 1996
    }
    ```

- **POST /generation/batch**: Add multiple generations.
  - **Request Body**:
    ```json
    [
      {
        "number": 1,
        "region": "Kanto",
        "year": 1996
      },
      {
        "number": 2,
        "region": "Johto",
        "year": 1999
      }
    ]
    ```
  - **Response**:
    ```json
    [
      {
        "id": 1,
        "number": 1,
        "region": "Kanto",
        "year": 1996
      },
      {
        "id": 2,
        "number": 2,
        "region": "Johto",
        "year": 1999
      }
    ]
    ```

- **GET /generation/{id}**: Retrieve a specific generation by ID.
  - **Response**:
    ```json
    {
      "id": 1,
      "number": 1,
      "region": "Kanto",
      "year": 1996
    }
    ```

- **GET /generation**: Retrieve all generations.
  - **Response**:
    ```json
    [
      {
        "id": 15,
        "number": 1,
        "region": "Kanto",
        "year": 1996
      },
      {
        "id": 16,
        "number": 2,
        "region": "Johto",
        "year": 1999
      }
  ]
  ```

- **GET /generation/regions**: Retrieve all generation regions.
  - **Response**:
    ```json
    [
      "Kanto",
      "Johto"
    ]
    ```

- **PUT /generation/{id}**: Update a generation by ID.
  - **Request Body**:
    ```json
    {
      "number": 1,
      "region": "Kanto",
      "year": 1996
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "number": 1,
      "region": "Kanto",
      "year": 1996
    }
    ```

- **DELETE /generation/{id}**: Delete a generation by ID.

---
