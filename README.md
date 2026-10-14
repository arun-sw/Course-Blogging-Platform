# Course-Blogging-Platform

A Spring Boot microservices-based course blogging platform built with Java, React.js, MySQL, Kafka, and Docker. The project demonstrates modular service design, authentication, blog management, notification flow, and containerized deployment.

## Overview
This project aims to provide a scalable blogging platform where users can create, manage, and search blog posts. It includes authentication, blog categories, tags, and a service-based architecture for better modularity.

## Features
- User authentication using JWT
- Google OAuth2 login
- Blog creation, update, and deletion
- Category and tag management
- Search functionality
- Microservice architecture
- Eureka service discovery
- API Gateway
- Kafka-based notifications
- Docker Compose deployment

## Tech Stack
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- React.js
- Kafka
- Docker
- Docker Compose
- Nginx

## Architecture
This project includes multiple backend services:
- api-gateway
- blog-service
- user-service
- notification-service
- service-registery

```mermaid
flowchart LR
    Client --> Frontend[React App]
    Frontend --> Gateway[API Gateway]
    Gateway --> UserService[User Service]
    Gateway --> BlogService[Blog Service]
    BlogService --> MySQL[(MySQL)]
    BlogService --> Kafka[Kafka]
    NotificationService[Notification Service] --> Kafka
    Gateway --> Registry[Eureka]
