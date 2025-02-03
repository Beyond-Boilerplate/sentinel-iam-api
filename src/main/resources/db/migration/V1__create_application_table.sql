CREATE TABLE application (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             owner_email VARCHAR(255) NOT NULL,
                             department VARCHAR(255) NOT NULL
);
