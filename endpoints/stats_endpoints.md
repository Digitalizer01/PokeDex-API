### **Stats Endpoints**

- **POST /stats**: Add new stats.
  - **Request Body**:
    ```json
    {
      "hp": 45,
      "attack": 49,
      "defense": 49,
      "specialAttack": 65,
      "specialDefense": 65,
      "speed": 45
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "hp": 45,
      "attack": 49,
      "defense": 49,
      "specialAttack": 65,
      "specialDefense": 65,
      "speed": 45
    }
    ```

- **GET /stats/{id}**: Retrieve stats by ID.
  - **Response**:
    ```json
    {
      "id": 1,
      "hp": 45,
      "attack": 49,
      "defense": 49,
      "specialAttack": 65,
      "specialDefense": 65,
      "speed": 45
    }
    ```

- **PUT /stats/{id}**: Update stats by ID.
  - **Request Body**:
    ```json
    {
      "hp": 50,
      "attack": 55,
      "defense": 60,
      "specialAttack": 70,
      "specialDefense": 75,
      "speed": 50
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "hp": 50,
      "attack": 55,
      "defense": 60,
      "specialAttack": 70,
      "specialDefense": 75,
      "speed": 50
    }
    ```

- **DELETE /stats/{id}**: Delete stats by ID.

---


