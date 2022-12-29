ALTER TABLE currency ADD COLUMN created_on TIMESTAMP NOT NULL DEFAULT NOW();
ALTER TABLE currency ADD COLUMN updated_on TIMESTAMP;

ALTER TABLE country ADD COLUMN created_on TIMESTAMP NOT NULL DEFAULT NOW();
ALTER TABLE country ADD COLUMN updated_on TIMESTAMP;

ALTER TABLE state ADD COLUMN created_on TIMESTAMP NOT NULL DEFAULT NOW();
ALTER TABLE state ADD COLUMN updated_on TIMESTAMP;

ALTER TABLE city ADD COLUMN created_on TIMESTAMP NOT NULL DEFAULT NOW();
ALTER TABLE city ADD COLUMN updated_on TIMESTAMP;