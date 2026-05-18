# Smart Contact Manager

Smart Contact Manager (SCM) is a Spring Boot web application for storing, organizing, searching, and communicating with contacts. It includes a Thymeleaf user interface, Spring Security authentication, Google and GitHub OAuth login, email verification, Cloudinary-backed contact images, feedback, direct messages, and Excel export for saved contacts.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [Setup and Run](#setup-and-run)
- [Application Routes](#application-routes)
- [Data Model](#data-model)
- [Validation Rules](#validation-rules)
- [Development Notes](#development-notes)
- [Testing and Build](#testing-and-build)

## Features

- User registration with email verification link.
- Local login with BCrypt password hashing.
- OAuth login with Google and GitHub.
- Authenticated user area under `/user/**`.
- Dashboard showing total contacts and favorite contacts.
- Contact CRUD with name, email, phone, address, description, website, LinkedIn, favorite flag, and profile image.
- Contact image upload through Cloudinary with generated 500 x 500 cropped image URLs.
- Paginated and sorted contact listing.
- Search contacts by name, email, or phone number.
- Contact detail modal loaded through a REST endpoint.
- SweetAlert confirmation before deleting contacts.
- Export contacts table to `contacts.xlsx`.
- Direct message screen for sending messages to saved contacts.
- Feedback screen for submitting and reviewing user feedback.
- Responsive Thymeleaf UI with Tailwind CSS, Flowbite, Font Awesome, and dark/light theme persistence.

## Tech Stack

### Backend

- Java 21
- Spring Boot 3.2.5
- Spring Web
- Spring Data JPA
- Spring Security
- Spring OAuth2 Client
- Spring Validation
- Spring Mail
- Thymeleaf
- MySQL
- Cloudinary Java SDK
- Lombok

### Frontend

- Thymeleaf templates
- Tailwind CSS 4.2.2
- Flowbite 2.3.0 from CDN
- Font Awesome 6.5.2 from CDN
- SweetAlert2 from CDN
- `@linways/table-to-excel` from CDN
- Vanilla JavaScript

## Project Structure

```text
.
|-- pom.xml
|-- package.json
|-- tailwind.config.js
|-- src
|   |-- main
|   |   |-- java/com/scm/scm
|   |   |   |-- config          # Security, OAuth, Cloudinary beans
|   |   |   |-- controllers     # Page, auth, contact, API, feedback, message controllers
|   |   |   |-- entities        # JPA entities and provider enum
|   |   |   |-- forms           # Form DTOs with validation
|   |   |   |-- helpers         # App constants, messages, session helpers
|   |   |   |-- repsitories     # Spring Data JPA repositories
|   |   |   |-- services        # Service interfaces and implementations
|   |   |   |-- validators      # Contact image file validator
|   |   |   `-- ScmApplication.java
|   |   `-- resources
|   |       |-- application.properties
|   |       |-- application-dev.properties
|   |       |-- application-prod.properties
|   |       |-- static
|   |       |   |-- css
|   |       |   |-- images
|   |       |   `-- js
|   |       `-- templates
|   |           |-- user
|   |           `-- public Thymeleaf pages
|   `-- test
|       `-- java/com/scm/scm/ScmApplicationTests.java
```

Note: the repository package folder is named `repsitories` in the current codebase.

## Prerequisites

- JDK 21
- MySQL 8 or compatible MySQL server
- Node.js and npm, only needed when rebuilding Tailwind CSS assets
- Cloudinary account for contact image uploads
- Google OAuth app and GitHub OAuth app for social login
- SMTP account for registration verification email

## Configuration

The app reads configuration from Spring properties and environment variables. The default active profile is `dev`.

| Variable | Default | Purpose |
| --- | --- | --- |
| `BASE_URL` | `http://localhost:5000` | Used to build email verification links |
| `SERVER_PORT` | `5000` | Spring Boot server port |
| `MYSQL_HOST` | `localhost` | MySQL host |
| `MYSQL_PORT` | `3306` | MySQL port |
| `MYSQL_DB` | `scm` | MySQL database name |
| `MYSQL_USER` | `root` | MySQL username |
| `MYSQL_PASSWORD` | none | MySQL password |
| `SHOW_SQL` | `true` | Enables Hibernate SQL logging |
| `DDL_AUTO` | `update` | Hibernate schema update mode |
| `ACTIVE_PROFILE` | `dev` | Spring profile |
| `MAX_FILE_SIZE` | `10MB` | Spring multipart file limit |
| `MAX_REQUEST_SIZE` | `10MB` | Spring multipart request limit |
| `GOOGLE_CLIENT_ID` | none | Google OAuth client ID |
| `GOOGLE_CLIENT_SECRET` | none | Google OAuth client secret |
| `GITHUB_CLIENT_ID` | none | GitHub OAuth client ID |
| `GITHUB_CLIENT_SECRET` | none | GitHub OAuth client secret |
| `CLOUDINARY_NAME` | none | Cloudinary cloud name |
| `CLOUDINARY_API_KEY` | none | Cloudinary API key |
| `CLOUDINARY_API_SECRET` | none | Cloudinary API secret |
| `EMAIL_HOST` | `smtp.gmail.com` | SMTP host |
| `EMAIL_PORT` | `587` | SMTP port |
| `EMAIL_USERNAME` | `YourGmail@gmail.com` | SMTP username |
| `EMAIL_PASSWORD` | `16 Digit Password` | SMTP password or app password |
| `EMAIL_DOMAIN` | `smtp.gmail.com` | Value used as sender in `EmailServiceImpl` |

For OAuth apps, use Spring Security's default callback pattern:

```text
http://localhost:5000/login/oauth2/code/google
http://localhost:5000/login/oauth2/code/github
```

## Setup and Run

### 1. Create the database

```sql
CREATE DATABASE scm;
```

Tables are created and updated by Hibernate because `spring.jpa.hibernate.ddl-auto` defaults to `update`.

### 2. Set environment variables

PowerShell example:

```powershell
$env:MYSQL_PASSWORD="your_mysql_password"
$env:GOOGLE_CLIENT_ID="your_google_client_id"
$env:GOOGLE_CLIENT_SECRET="your_google_client_secret"
$env:GITHUB_CLIENT_ID="your_github_client_id"
$env:GITHUB_CLIENT_SECRET="your_github_client_secret"
$env:CLOUDINARY_NAME="your_cloudinary_name"
$env:CLOUDINARY_API_KEY="your_cloudinary_api_key"
$env:CLOUDINARY_API_SECRET="your_cloudinary_api_secret"
$env:EMAIL_USERNAME="your_email@example.com"
$env:EMAIL_PASSWORD="your_email_app_password"
$env:EMAIL_DOMAIN="your_email@example.com"
```

Optional defaults can also be overridden:

```powershell
$env:BASE_URL="http://localhost:5000"
$env:SERVER_PORT="5000"
$env:MYSQL_HOST="localhost"
$env:MYSQL_PORT="3306"
$env:MYSQL_DB="scm"
$env:MYSQL_USER="root"
```

### 3. Install frontend dependencies

```powershell
npm install
```

### 4. Rebuild Tailwind CSS when editing styles

```powershell
npm run build
```

This script runs Tailwind in watch mode and writes to:

```text
src/main/resources/static/css/output.css
```

Keep it running in a separate terminal while changing templates, JavaScript, or Tailwind input CSS.

### 5. Start the Spring Boot app

```powershell
.\mvnw.cmd spring-boot:run
```

Then open:

```text
http://localhost:5000
```

On Linux, macOS, or Git Bash:

```bash
./mvnw spring-boot:run
```

## Application Routes

### Public pages

| Route | Purpose |
| --- | --- |
| `/` | Redirects to `/home` |
| `/home` | Landing page |
| `/about` | About page |
| `/services` | Services page |
| `/contact` | Contact page |
| `/login` | Login page |
| `/register` | Registration form |
| `/do-register` | Registration POST handler |
| `/auth/verify-email?token=...` | Email verification |

### Authentication

| Route | Purpose |
| --- | --- |
| `/authenticate` | Spring Security form login processing URL |
| `/oauth2/authorization/google` | Start Google OAuth login |
| `/oauth2/authorization/github` | Start GitHub OAuth login |
| `/do-logout` | Logout |

### Authenticated user pages

| Route | Purpose |
| --- | --- |
| `/user/dashboard` | User dashboard |
| `/user/profile` | User profile |
| `/user/contacts` | Paginated contacts list |
| `/user/contacts/add` | Add contact page and POST handler |
| `/user/contacts/search` | Search contacts |
| `/user/contacts/view/{contactId}` | Update contact form |
| `/user/contacts/update/{contactId}` | Update contact POST handler |
| `/user/contacts/delete/{contactId}` | Delete contact |
| `/user/direct-message` | Direct message page |
| `/user/direct-message/send` | Send direct message |
| `/user/feedback` | Feedback page |
| `/user/feedback/submit` | Submit feedback |

### REST endpoint

| Route | Purpose |
| --- | --- |
| `/api/contacts/{contactId}` | Returns contact JSON for the contact detail modal |

## Data Model

### User

Stores account information, provider details, verification flags, roles, profile picture, and related contacts. It implements Spring Security `UserDetails`.

Important fields:

- `userId`
- `name`
- `email`
- `password`
- `about`
- `profilePic`
- `phoneNumber`
- `enabled`
- `emailVerified`
- `phoneVerified`
- `provider`
- `providerUserId`
- `roleList`
- `emailToken`

### Contact

Stores user-owned contact records.

Important fields:

- `id`
- `name`
- `email`
- `phoneNumber`
- `address`
- `picture`
- `description`
- `favorite`
- `websiteLink`
- `linkedInLink`
- `cloudinaryImagePublicId`
- `user`

### DirectMessage

Stores messages sent by a user to one of their saved contacts.

Important fields:

- `id`
- `subject`
- `content`
- `sentAt`
- `sender`
- `recipient`
- `isRead`

### Feedback

Stores feedback submitted by a user.

Important fields:

- `id`
- `subject`
- `message`
- `submittedAt`
- `user`

## Validation Rules

- User name is required and must have at least 3 characters.
- User email is required and must be a valid email address.
- User password is required and must have at least 6 characters.
- User about text is required.
- User phone number must be 8 to 12 characters when provided.
- Contact name, email, phone number, and address are required.
- Contact phone number must be exactly 10 digits.
- Contact image is optional.
- Contact image uploads are limited by the custom validator to 2 MB.
- Spring multipart limits default to 10 MB unless overridden.
- Direct messages require recipient, subject, and content.
- Feedback requires subject and message.

## Development Notes

- `SecurityConfig` protects `/user/**` and permits all other routes.
- Form login uses `/login` as the login page and `/authenticate` as the processing URL.
- Logout is configured at `/do-logout`.
- CSRF protection is disabled in the current security configuration.
- Registration creates disabled users until they verify email.
- OAuth users are created enabled and email-verified.
- New self-registered users receive role `ROLE_USER`.
- Contact image upload uses Cloudinary and stores the generated URL on the contact.
- Contact pages use `AppConstants.PAGE_SIZE`, currently `10`.
- The contact modal and delete action in `contacts.js` use `http://localhost:5000/` as the frontend base URL. Keep the default port during local development, or update that JavaScript value if running on another port.
- `EmailServiceImpl` currently implements plain text email. HTML and attachment email methods throw `UnsupportedOperationException`.
- `HELP.md` is the default Spring initializer help file; this README is the project-specific guide.

## Testing and Build

Run tests:

```powershell
.\mvnw.cmd test
```

Build the application JAR:

```powershell
.\mvnw.cmd clean package
```

Run the packaged app:

```powershell
java -jar target\scm2.0-0.0.1-SNAPSHOT.jar
```

The current test class exists at `src/test/java/com/scm/scm/ScmApplicationTests.java`, but its sample test methods are commented out.
