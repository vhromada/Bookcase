CREATE TABLE categories (
  id            INTEGER      NOT NULL CONSTRAINT categories_pk PRIMARY KEY,
  category_name VARCHAR(100) NOT NULL CONSTRAINT categories_category_name_ck CHECK (LENGTH(category_name) > 0),
  position      INTEGER      NOT NULL CONSTRAINT categories_position_ck CHECK (position >= 0)
);

CREATE TABLE authors (
  id          INTEGER      NOT NULL CONSTRAINT authors_pk PRIMARY KEY,
  first_name  VARCHAR(100) NOT NULL CONSTRAINT authors_first_name_ck CHECK (LENGTH(first_name) > 0),
  middle_name VARCHAR(100),
  last_name   VARCHAR(100) NOT NULL CONSTRAINT authors_last_name_ck CHECK (LENGTH(last_name) > 0),
  position    INTEGER      NOT NULL CONSTRAINT authors_position_ck CHECK (position >= 0)
);

CREATE TABLE books (
  id            INTEGER       NOT NULL CONSTRAINT books_pk PRIMARY KEY,
  czech_name    VARCHAR(100)  NOT NULL CONSTRAINT books_czech_name_ck CHECK (LENGTH(czech_name) > 0),
  original_name VARCHAR(100)  NOT NULL CONSTRAINT books_original_name_ck CHECK (LENGTH(original_name) > 0),
  isbn          VARCHAR(20),
  issue_year    INTEGER       NOT NULL CONSTRAINT books_issue_year_ck CHECK (issue_year BETWEEN 1900 AND 2100),
  description   VARCHAR(1000) NOT NULL CONSTRAINT books_description_ck CHECK (LENGTH(description) > 0),
  electronic    BOOLEAN       NOT NULL,
  paper         BOOLEAN       NOT NULL,
  note          VARCHAR(100),
  position      INTEGER       NOT NULL CONSTRAINT movies_position_ck CHECK (position >= 0),
  CONSTRAINT books_type_ck CHECK (electronic = TRUE OR paper = TRUE)
);

CREATE TABLE book_languages (
  book          INTEGER    NOT NULL CONSTRAINT book_languages_book_fk REFERENCES books (id),
  book_language VARCHAR(2) NOT NULL CONSTRAINT book_languages_book_language_ck CHECK (book_language IN ('CZ', 'EN'))
);

CREATE TABLE book_categories (
  book     INTEGER NOT NULL CONSTRAINT book_categories_book_fk REFERENCES books (id),
  category INTEGER NOT NULL CONSTRAINT book_categories_category_fk REFERENCES categories (id)
);

CREATE TABLE book_authors (
  book   INTEGER NOT NULL CONSTRAINT book_authors_book_fk REFERENCES books (id),
  author INTEGER NOT NULL CONSTRAINT book_authors_author_fk REFERENCES authors (id)
);

CREATE SEQUENCE categories_sq
  START WITH 1
  INCREMENT BY 1;
CREATE SEQUENCE authors_sq
  START WITH 1
  INCREMENT BY 1;
CREATE SEQUENCE books_sq
  START WITH 1
  INCREMENT BY 1;
