CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,
                       application_id BIGINT REFERENCES application(id) ON DELETE CASCADE
);