CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
    id BIGINT NOT NULL DEFAULT nextval('users_seq'),
    PRIMARY KEY (id),
    role VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);


services:
  database:
    image: postgres:15
    container_name: dockerbackend
    environment:
      POSTGRES_DB: cursos_db
      POSTGRES_USER: cursos_users
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
          test: ["CMD-SHELL", "pg_isready -U cursos_users -d cursos_db"]
          interval: 5s
          timeout: 5s
          retries: 5
volumes:
    postgres_data: