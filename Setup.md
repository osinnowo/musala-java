# Dispatch Controller API Documentation

The Dispatch Controller provides RESTful API endpoints for managing drones and dispatch operations. This documentation provides an overview of the available endpoints, their request/response formats, and example usage.

# Project Setup
This project requires Docker and Docker Compose to be installed on your machine. Please make sure you have them installed before proceeding.

## Step 1: Navigate to the root directory
`$ cd [folder-name] `

## Step 2: Start the Docker Containers
Run the following command to start the Docker containers defined in the `docker-compose.yml` file:
`$ docker compose up -d`

This command will start the PostgreSQL database container and the pgAdmin container.

## Step 3: Accessing the Application

### Database

The PostgreSQL database can be accessed using the following credentials:

- Host: `localhost`
- Port: `5439` [`5432` - internally within docker isolated network]
- Username: `root`
- Password: `root`
- Database: `drone-service-database`

You can use any PostgreSQL client to connect to the database or access the pgadmin on `http://localhost:8090`

### pgAdmin

The pgAdmin interface can be accessed using the following URL:
`http://localhost:8090`

Use the following credentials to log in:

- Email: `admin@admin.com`
- Password: `admin`

Once logged in, you can manage the PostgreSQL database using the pgAdmin interface.

## Step 4: Stopping the Containers

To stop the Docker containers, run the following command:
`docker-compose down`

## Base URL

The base URL for accessing the Dispatch Controller API is `/api/dispatch`.
## Endpoints

### Get All Drones

- Endpoint: `GET /drone`
- Description: Retrieves a list of all available drones.

### Get Drone By ID

- Endpoint: `GET /drone/{id}`
- Description: Retrieves a specific drone by its ID.

### Register Drone

- Endpoint: `POST /drone/load`
- Description: Registers a new drone with the provided details.
- Request Body:
  - Example:
    ```json
    {
      "serialNumber": "DRN123",
      "model": "Middleweight",
      "weightLimit": 500.0,
      "batteryCapacity": 80
    }
    ```

### Load Drone with Single Medication

- Endpoint: `PUT /drone/load/{id}/single`
- Description: Loads a specific drone with a single medication.
- Request Body:
  - Example:
    ```json
    {
      "name": "Medication A",
      "weight": 22.00,
      "code": "CD1000001",
      "image": "medication.jpg"
    }
    ```

### Load Drone with Multiple Medications

- Endpoint: `PUT /drone/load/{id}/multiple`
- Description: Loads a specific drone with multiple medications.
- Request Body:
  - Example:
    ```json
    [
      {
          "name": "Medication A",
          "weight": 22.00,
          "code": "CD1000001",
          "image": "medication2.jpg"
      },
      {
          "name": "Medication B",
          "weight": 22.00,
          "code": "CD1000002",
          "image": "medication2.jpg"
      }
    ]
    ```

### Load Drone with Medication by ID

- Endpoint: `PUT /drone/load/{droneId}/{medicationId}`
- Description: Loads a specific drone with a medication identified by its ID.

### Set Drone Status

- Endpoint: `PUT /drone/load/{droneId}/status/{status}`
- Description: Sets the status of a specific drone.


