# Awesome Pizza Order Manager

## Overview

**Awesome Pizza Order Manager** is a RESTful API built with Spring Boot, RabbitMQ, and PostgreSQL to manage pizza orders efficiently. It provides endpoints for placing orders, updating order status, and tracking orders in real-time.

The app uses:
- **RabbitMQ** to handle and queue order messages for asynchronous processing.
- **PostgreSQL** for persistent storage of order details and their statuses.
- **Docker Compose** to simplify setup by orchestrating application services.

This project is designed for rapid order management, specifically tailored to streamline the pizzeria workflow.

## Features

- **Place Order**: Allows customers to order pizzas without user registration.
- **View Pending Orders**: Enables the pizzaiolo to see the queue of pending orders.
- **Order Status Tracking**: Lets customers track the status of their orders.
- **Update Order Status**: The pizzaiolo can update the status of an order to "in progress," "completed," or "canceled."

## Technologies Used

- **Java 17**
- **Spring Boot**
- **RabbitMQ** (for messaging)
- **PostgreSQL** (for database storage)
- **Docker** & **Docker Compose**

## Prerequisites

Ensure you have the following installed:
- Docker
- Docker Compose

## Project Structure

- **`controller/`**: Contains RESTful API controllers to handle HTTP requests.
- **`service/`**: Contains the business logic for handling orders and updating statuses.
- **`repository/`**: Interfaces with the PostgreSQL database.
- **`rabbit/`**: Manages RabbitMQ message consumption and order processing.
- **`config/`**: Contains configurations for RabbitMQ, PostgreSQL, and application properties.

## Endpoints

1. **Get Pending Orders**: `GET /api/v1/orders/pending`
2. **Place Order**: `POST /api/v1/orders/place`
3. **Update Order Status**: `PATCH /api/v1/orders/{code}/status`
4. **Get Order Status**: `GET /api/v1/orders/{code}/status`

## Getting Started

### Step 1: Clone the Repository

```bash
git clone https://github.com/maheeshan/pizza-order-manager.git
cd pizza-order-manager
docker compose up --build
