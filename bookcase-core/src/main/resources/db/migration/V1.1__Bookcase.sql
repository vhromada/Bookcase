DROP TABLE IF EXISTS book_authors;
DROP TABLE IF EXISTS item_languages;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
  id           INTEGER      NOT NULL CONSTRAINT authors_pk PRIMARY KEY,
  first_name   VARCHAR(100) NOT NULL CONSTRAINT authors_first_name_ck CHECK (LENGTH(first_name) > 0),
  middle_name  VARCHAR(100),
  last_name    VARCHAR(100) NOT NULL CONSTRAINT authors_last_name_ck CHECK (LENGTH(last_name) > 0),
  position     INTEGER      NOT NULL CONSTRAINT authors_position_ck CHECK (position >= 0),
  created_user VARCHAR(36)  NOT NULL,
  created_time TIMESTAMP    NOT NULL,
  updated_user VARCHAR(36)  NOT NULL,
  updated_time TIMESTAMP    NOT NULL
);

CREATE TABLE books (
  id            INTEGER       NOT NULL CONSTRAINT books_pk PRIMARY KEY,
  czech_name    VARCHAR(100)  NOT NULL CONSTRAINT books_czech_name_ck CHECK (LENGTH(czech_name) > 0),
  original_name VARCHAR(100)  NOT NULL CONSTRAINT books_original_name_ck CHECK (LENGTH(original_name) > 0),
  isbn          VARCHAR(20),
  issue_year    INTEGER       NOT NULL CONSTRAINT books_issue_year_ck CHECK (issue_year BETWEEN 1900 AND 2100),
  description   VARCHAR(1000) NOT NULL CONSTRAINT books_description_ck CHECK (LENGTH(description) > 0),
  note          VARCHAR(100),
  position      INTEGER       NOT NULL CONSTRAINT books_position_ck CHECK (position >= 0),
  created_user  VARCHAR(36)   NOT NULL,
  created_time  TIMESTAMP     NOT NULL,
  updated_user  VARCHAR(36)   NOT NULL,
  updated_time  TIMESTAMP     NOT NULL
);

CREATE TABLE items (
  id           INTEGER       NOT NULL CONSTRAINT items_pk PRIMARY KEY,
  book         INTEGER       CONSTRAINT TABLE_book_fk REFERENCES books (id),
  format       VARCHAR(10)   NOT NULL CONSTRAINT items_format_ck CHECK (format IN ('PAPER', 'PDF', 'DOC', 'TXT')),
  note         VARCHAR(100),
  position     INTEGER       NOT NULL CONSTRAINT items_position_ck CHECK (position >= 0),
  created_user VARCHAR(36)   NOT NULL,
  created_time TIMESTAMP     NOT NULL,
  updated_user VARCHAR(36)   NOT NULL,
  updated_time TIMESTAMP     NOT NULL
);

CREATE TABLE item_languages (
  item          INTEGER    NOT NULL CONSTRAINT item_languages_item_fk REFERENCES items (id),
  item_language VARCHAR(2) NOT NULL CONSTRAINT item_languages_item_language_ck CHECK (item_language IN ('CZ', 'EN'))
);

CREATE TABLE book_authors (
  book   INTEGER NOT NULL CONSTRAINT book_authors_book_fk REFERENCES books (id),
  author INTEGER NOT NULL CONSTRAINT book_authors_author_fk REFERENCES authors (id)
);

DROP SEQUENCE IF EXISTS authors_sq;
DROP SEQUENCE IF EXISTS books_sq;
DROP SEQUENCE IF EXISTS items_sq;

CREATE SEQUENCE authors_sq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE books_sq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE items_sq START WITH 1 INCREMENT BY 1;
