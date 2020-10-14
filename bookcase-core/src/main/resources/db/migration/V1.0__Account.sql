DROP TABLE IF EXISTS account_roles;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
  id        INTEGER      NOT NULL CONSTRAINT roles_pk PRIMARY KEY,
  role_name VARCHAR(200) NOT NULL CONSTRAINT roles_name_ck CHECK (LENGTH(role_name) > 0)
);

CREATE TABLE accounts (
  id       INTEGER      NOT NULL CONSTRAINT accounts_pk PRIMARY KEY,
  uuid     VARCHAR(36)  NOT NULL CONSTRAINT accounts_uuid_ck CHECK (LENGTH(uuid) > 0),
  username VARCHAR(200) NOT NULL CONSTRAINT accounts_username_ck CHECK (LENGTH(username) > 0),
  password VARCHAR(200) NOT NULL CONSTRAINT accounts_password_ck CHECK (LENGTH(password) > 0)
);

CREATE TABLE account_roles (
  account INTEGER CONSTRAINT account_roles_account_fk REFERENCES accounts (id),
  role    INTEGER CONSTRAINT account_roles_role_fk REFERENCES roles (id)
);

DROP SEQUENCE IF EXISTS accounts_sq;
DROP SEQUENCE IF EXISTS roles_sq;

CREATE SEQUENCE accounts_sq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE roles_sq START WITH 1 INCREMENT BY 1;

INSERT INTO roles (id, role_name) VALUES (roles_sq.nextval, 'ROLE_ADMIN');
INSERT INTO roles (id, role_name) VALUES (roles_sq.nextval, 'ROLE_USER');
INSERT INTO accounts (id, uuid, username, password) VALUES (accounts_sq.nextval, 'dc0d73bc-e19e-4c91-b818-192907def7ec', 'admin', '$2a$10$.cmRYarPyPMOGNk9Qj2zI.TjasuXvmxOEgzgD5mSd/HjoumUZzcIS');
INSERT INTO account_roles (account, role) VALUES ((SELECT id FROM accounts WHERE uuid = 'dc0d73bc-e19e-4c91-b818-192907def7ec'), (SELECT id FROM roles WHERE role_name = 'ROLE_ADMIN'));
