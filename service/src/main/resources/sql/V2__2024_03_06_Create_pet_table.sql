CREATE TABLE IF NOT EXISTS pet
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    owner_id    BIGINT REFERENCES person(id)
);
