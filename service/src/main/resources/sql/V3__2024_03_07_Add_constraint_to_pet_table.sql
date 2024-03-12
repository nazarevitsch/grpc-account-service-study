ALTER TABLE pet
DROP CONSTRAINT pet_owner_id_fkey;

ALTER TABLE pet
ADD CONSTRAINT pet_owner_id_fkey
    FOREIGN KEY (owner_id)
    REFERENCES person(id)
    ON DELETE CASCADE;
