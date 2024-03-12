CREATE TABLE IF NOT EXISTS person
(
    id              SERIAL PRIMARY KEY,
    first_name      TEXT,
    last_name       TEXT,
    email           TEXT,
    age             INT4
);
