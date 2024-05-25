CREATE SEQUENCE customer_id_seq;

CREATE TABLE customer (
    id BIGINT DEFAULT nextval('customer_id_seq') PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    age INT NOT NULL
);