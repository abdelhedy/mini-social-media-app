# Postify — Mini Social Media API

A RESTful backend for a lightweight social media platform, built with **Spring Boot 3** and **MongoDB**.

Users sign up, publish posts combining text, images and videos, browse a shared feed, and like each other's posts. Media files are uploaded with the post, stored on the server and exposed as ready-to-use URLs.

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-6DB33F?logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-47A248?logo=mongodb&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?logo=apachemaven&logoColor=white)
![Angular](https://img.shields.io/badge/Frontend-Angular%2019-DD0031?logo=angular&logoColor=white)

> **Frontend:** the Angular client for this API lives in a separate repository: [postify_material](https://github.com/MariemAbdennadher11/postify_material).
>
> Some identifiers are in French.

---

## Table of Contents

- [Features](#features)
- [How It Works](#how-it-works)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Reference](#api-reference)
- [Database](#database)
- [Related Projects](#related-projects)
- [Author](#author)

---

## Features

### Accounts

- Sign up with name, email, password, phone number and date of birth
- Sign in with email and password

### Posts

- Create posts with text and **multiple images and videos** in a single multipart request
- Edit a post's content, with its `updatedAt` timestamp refreshed automatically
- Delete a post together with its media files on disk

### Feed

- Browse all posts, a single post, or every post written by a given author
- Each post is returned with its author's first and last name and direct links to its media

### Interactions

- Like and unlike posts, with at most one like per user per post

### Media

- Uploaded files are saved under collision-free names (`UUID_originalName`)
- Files are served back through a dedicated `/files` endpoint with the right content type
- Uploads of up to **100 MB** per file

---

## How It Works

```
 Client (Angular)                      Spring Boot API                                MongoDB / Disk
 ────────────────                      ───────────────                                ──────────────
 POST /post/create  ──multipart──►  PostController
 (authorId, content,                       │
  images[], videos[])                      ▼
                                      PostService
                                           │
                                           ▼
                                      PostMapper.toEntity()
                                           ├── each file ──► UUID_name.ext ───────────►  upload folder
                                           │
                                           ▼
                                      PostRepository.save() ────────────────────────►  posts collection
                                           │
                                           ▼
                                      PostMapper.toDto()
                                           ├── looks up the author ◄────────────────── users collection
                                           └── builds imageURLs / videoURLs
                                           │
 PostResponseDto  ◄────── JSON ────────────┘

 GET /files/{name}  ──────────────►  FileController ── streams the file ◄──────────  upload folder
```

The API never exposes internal storage paths: the database stores file paths, and `PostMapper` turns them into public URLs (`http://localhost:8080/files/<name>`) when building responses.

---

## Tech Stack

| Layer          | Technology                                                       |
| -------------- | ---------------------------------------------------------------- |
| Language       | Java 17                                                          |
| Framework      | Spring Boot 3.4 (Spring Web, Spring Data MongoDB)                |
| Database       | MongoDB (MongoDB Atlas or a local instance)                      |
| Boilerplate    | Lombok                                                           |
| Build          | Maven, with the Maven Wrapper included                           |
| Frontend       | Angular 19, Angular Material ([postify_material](https://github.com/MariemAbdennadher11/postify_material)) |

---

## Architecture

The application follows a layered **Controller → Service → Repository** design, with DTOs and a mapper separating the public API from the MongoDB documents.

| Layer        | Package         | Responsibility                                                                |
| ------------ | --------------- | ----------------------------------------------------------------------------- |
| Controllers  | `controllers/`  | REST endpoints for users, posts and media files                               |
| Services     | `services/`     | Business logic: authentication, post lifecycle, likes, media cleanup          |
| Repositories | `repositories/` | Spring Data MongoDB repositories with derived queries (`findByEmail`, `findByAuthorId`) |
| Models       | `models/`       | MongoDB documents: `User` (with an embedded `Profile`) and `Post`             |
| DTOs         | `dtos/`         | Request payloads (`createInputs/`, `updateInputs/`) and responses (`responseDtos/`) |
| Mappers      | `mappers/`      | Entity ↔ DTO conversion, media storage and URL generation                     |
| Config       | `config/`       | Embedded Tomcat tuning for large uploads                                      |
| Exceptions   | `exception/`    | Custom exceptions mapped to HTTP statuses                                     |

---

## Project Structure

```
mini-social-media-app/
├── src/main/java/fsegs/nosql/minisocialmediaapp/
│   ├── MiniSocialMediaAppApplication.java   # Entry point
│   ├── config/
│   │   └── TomcatConfig.java                # Raises the max POST size for media uploads
│   ├── controllers/
│   │   ├── UserController.java              # /user  — sign up, sign in
│   │   ├── PostController.java              # /post  — posts, feed, likes
│   │   └── FileController.java              # /files — serves uploaded media
│   ├── dtos/
│   │   ├── createInputs/                    # RegisterRequest, AuthenticationRequest, PostCreateInput
│   │   ├── updateInputs/                    # PostUpdateInput
│   │   └── responseDtos/                    # UserDto, PostResponseDto
│   ├── exception/
│   │   └── ResourceNotFoundException.java   # → HTTP 404
│   ├── mappers/
│   │   └── PostMapper.java                  # Entity ↔ DTO, file storage, media URLs
│   ├── models/
│   │   ├── User.java                        # "users" collection
│   │   ├── Profile.java                     # Embedded user profile
│   │   └── Post.java                        # "posts" collection
│   ├── repositories/
│   │   ├── UserRepository.java
│   │   └── PostRepository.java
│   └── services/
│       ├── UserService.java
│       └── PostService.java
├── src/main/resources/
│   └── application.properties               # App config (reads secrets from .env)
├── .env.example                             # Environment variables template
├── mvnw · mvnw.cmd                          # Maven Wrapper
└── pom.xml
```

---

## Getting Started

### Prerequisites

- JDK 17 or newer
- A MongoDB database: a free [MongoDB Atlas](https://www.mongodb.com/atlas) cluster or a local [MongoDB Community Server](https://www.mongodb.com/try/download/community)
- Node.js and the Angular CLI, to run the frontend

Maven does not need to be installed: the project ships with the Maven Wrapper.

### 1. Clone the repository

```bash
git clone https://github.com/abdelhedy/mini-social-media-app.git
cd mini-social-media-app
```

### 2. Configure the environment

```bash
cp .env.example .env          # Windows: copy .env.example .env
```

Then edit `.env` with your MongoDB connection string (see [Configuration](#configuration)). The file must stay in the project root, next to `pom.xml`. If you use MongoDB Atlas, also add your IP address under **Network Access**.

### 3. Create the upload folder

Uploaded media is stored in `E:/postify_files`. Make sure this location exists and is writable.

### 4. Run the API

```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

The API is available at **http://localhost:8080**.

To build a standalone JAR instead:

```bash
./mvnw clean package -DskipTests
java -jar target/mini-social-media-app-0.0.1-SNAPSHOT.jar
```

### 5. Run the frontend

```bash
git clone https://github.com/MariemAbdennadher11/postify_material.git
cd postify_material
npm install
ng serve
```

Open **http://localhost:4200**.

---

## Configuration

Environment variables (`.env`):

| Variable           | Description                          | Example                                                        |
| ------------------ | ------------------------------------ | -------------------------------------------------------------- |
| `MONGODB_URI`      | MongoDB connection string (required) | `mongodb+srv://<user>:<password>@<cluster>.mongodb.net/postify` |
| `MONGODB_DATABASE` | Database name                        | `postify` *(default)*                                          |

`.env` is loaded through `spring.config.import` and is excluded from version control.

---

## API Reference

Base URL: `http://localhost:8080`

### Users — `/user`

| Method | Endpoint             | Description       | Body                           |
| ------ | -------------------- | ----------------- | ------------------------------ |
| `POST` | `/user/subscribe`    | Sign up           | `RegisterRequest` (JSON)       |
| `POST` | `/user/authenticate` | Sign in           | `AuthenticationRequest` (JSON) |

Both return a `UserDto`: `{ id, nom, prenom }`.

### Posts — `/post`

| Method   | Endpoint                          | Description                       | Body                      |
| -------- | --------------------------------- | --------------------------------- | ------------------------- |
| `GET`    | `/post/all`                       | Get all posts                     | —                         |
| `GET`    | `/post/{postId}`                  | Get one post                      | —                         |
| `GET`    | `/post/author/{authorId}`         | Get an author's posts             | —                         |
| `POST`   | `/post/create`                    | Create a post with optional media | `multipart/form-data`     |
| `PUT`    | `/post/{postId}`                  | Edit a post's content             | `PostUpdateInput` (JSON)  |
| `DELETE` | `/post/delete/{id}`               | Delete a post and its media       | —                         |
| `POST`   | `/post/{postId}/like/{userId}`    | Like a post                       | —                         |
| `DELETE` | `/post/{postId}/dislike/{userId}` | Unlike a post                     | —                         |

`/post/create` form fields: `authorId` (required), `content`, `images` (multiple files), `videos` (multiple files).

Post endpoints return a `PostResponseDto`:

```json
{
   "id": "6750b2e3d4c5b6a7f8e9d0c1",
   "authorNom": "Abdelhedi",
   "authorPrenom": "Yasmine",
   "content": "Hello Postify!",
   "imageURLs": ["http://localhost:8080/files/3f9c1a..._photo.jpg"],
   "videoURLs": [],
   "likes": ["6750a1f2c3d4e5f6a7b8c9d0"]
}
```

### Files — `/files`

| Method | Endpoint            | Description                         |
| ------ | ------------------- | ----------------------------------- |
| `GET`  | `/files/{fileName}` | Download an uploaded image or video |

---

## Database

Database: **`postify`** (MongoDB). Collections are created automatically on first write.

| Collection | Purpose          | Key fields |
| ---------- | ---------------- | ---------- |
| `users`    | User accounts    | `nom`, `prenom`, `email`, `password`, `phone`, `date_naiss`, `profile` (embedded: `bio`, `profilePicture`, `coverPhoto`, `location`, `website`) |
| `posts`    | Published posts  | `authorId` (→ `users._id`), `content`, `imagePaths`, `videoPaths`, `likes` (array of user IDs), `createdAt`, `updatedAt` |

The schema uses MongoDB's document model: the profile is **embedded** in the user document, and likes are stored as an **array of user IDs** directly on each post, so a post and its likes are read in a single query.

---

## Related Projects

- **[postify_material](https://github.com/MariemAbdennadher11/postify_material)**: Angular 19 + Angular Material frontend for this API, by [@MariemAbdennadher11](https://github.com/MariemAbdennadher11)

---

## Author

**Yasmine Abdelhedi** · [@abdelhedy](https://github.com/abdelhedy)

Academic project (NoSQL databases), FSEGS.