# ShortLink

A full-stack URL shortener built with Spring Boot, Spring Data JPA, and a vanilla HTML/CSS/JS frontend. Generate short links for long URLs, track click counts, and redirect via custom short codes.

## Tech Stack

- **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security (CORS configured)
- **Database:** Configurable via `application.yaml` (MySQL/PostgreSQL/H2)
- **Frontend:** HTML, CSS, JavaScript (no frameworks)

## Features

- Shorten any valid URL with a single click
- Auto-refreshing list of all shortened links
- Click count tracking per link
- Redirect from short code to original URL
- Copy-to-clipboard for generated links

## Getting Started

### Prerequisites

- Java 17+
- Maven
- A database (configure connection in `application.yaml`)

### Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/ShortLink.git
   cd ShortLink
   ```

2. Configure your database connection in `src/main/resources/application.yaml`, or create `application-local.yaml` for local-only credentials (gitignored).

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

   The backend starts on `http://localhost:8080`.

### Frontend Setup

1. Open `index.html` using a local server (e.g. VS Code "Live Server" extension on port `5500`) — this matches the CORS configuration.

2. The frontend will automatically call the backend at `http://localhost:8080/api/url`.

## API Reference

Base URL: `http://localhost:8080/api/url`

### Create a short URL

```
POST /short
```

**Request body:**
```json
{
    "originalUrl": "https://www.youtube.com/watch?v=jMeg30MNYwk&list=RDjMeg30MNYwk&start_radio=1"
}
```

**Response body:**
```json
{
    "shortUrl": "http://localhost:8080/SL1",
    "originalUrl": "https://www.youtube.com/watch?v=jMeg30MNYwk&list=RDjMeg30MNYwk&start_radio=1",
    "dateTime": "2026-06-15 12:34:12",
    "clickCount": 0
}
```

### Get all URLs

```
GET /getAllUrl
```

**Response body:**
```json
[
    {
        "shortUrl": "https://www.youtube.com/watch?v=jMeg30MNYwk&list=RDjMeg30MNYwk&start_radio=1",
        "clickCount": 1,
        "createdAt": "2026-06-15 12:34:12",
        "shortCode": "http://localhost:8080/SL1"
    }
]
```

### Redirect to original URL

```
GET http://localhost:8080/{shortCode}
```

Redirects to the original URL and increments the click count.

**Example:**
```
http://localhost:8080/SL1 → https://www.youtube.com/watch?v=jMeg30MNYwk&list=RDjMeg30MNYwk&start_radio=1
```

## Project Structure

```
ShortLink/
├── src/main/java/com/shortLink/ShortLink/
│   ├── config/        # CORS and Security configuration
│   ├── controller/     # REST controllers
│   ├── service/        # Business logic
│   └── repository/     # JPA repositories
├── src/main/resources/
│   └── application.yaml
└── index.html          # Frontend
```

## License

This project is open source and available for personal and educational use.
