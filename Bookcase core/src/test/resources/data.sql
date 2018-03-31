INSERT INTO categories (id, category_name, position)
VALUES (1, 'Category 1 Name', 0);
INSERT INTO categories (id, category_name, position)
VALUES (2, 'Category 2 Name', 1);
INSERT INTO categories (id, category_name, position)
VALUES (3, 'Category 3 Name', 2);

INSERT INTO authors (id, first_name, middle_name, last_name, position)
VALUES (1, 'Author 1 First Name', NULL, 'Author 1 Last Name', 0);
INSERT INTO authors (id, first_name, middle_name, last_name, position)
VALUES (2, 'Author 2 First Name', 'Author 2 Middle Name', 'Author 2 Last Name', 1);
INSERT INTO authors (id, first_name, middle_name, last_name, position)
VALUES (3, 'Author 3 First Name', NULL, 'Author 3 Last Name', 2);

INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, electronic, paper, note, position)
VALUES (1, 'Book 1 Czech Name', 'Book 1 Original Name', 'Book 1 ISBN', 2001, 'Book 1 Description', TRUE, TRUE, 'Book 1 Note', 0);
INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, electronic, paper, note, position)
VALUES (2, 'Book 2 Czech Name', 'Book 2 Original Name', NULL, 2002, 'Book 2 Description', TRUE, FALSE, NULL, 1);
INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, electronic, paper, note, position)
VALUES (3, 'Book 3 Czech Name', 'Book 3 Original Name', 'Book 3 ISBN', 2003, 'Book 3 Description', FALSE, TRUE, 'Book 3 Note', 2);

INSERT INTO book_languages (book, book_language)
VALUES (1, 'CZ');
INSERT INTO book_languages (book, book_language)
VALUES (1, 'EN');
INSERT INTO book_languages (book, book_language)
VALUES (2, 'CZ');
INSERT INTO book_languages (book, book_language)
VALUES (3, 'EN');

INSERT INTO book_categories (book, category)
VALUES (1, 1);
INSERT INTO book_categories (book, category)
VALUES (1, 2);
INSERT INTO book_categories (book, category)
VALUES (2, 2);
INSERT INTO book_categories (book, category)
VALUES (3, 3);

INSERT INTO book_authors (book, author)
VALUES (1, 1);
INSERT INTO book_authors (book, author)
VALUES (1, 2);
INSERT INTO book_authors (book, author)
VALUES (2, 2);
INSERT INTO book_authors (book, author)
VALUES (3, 3);

ALTER SEQUENCE categories_sq
RESTART WITH 4;

ALTER SEQUENCE authors_sq
RESTART WITH 4;

ALTER SEQUENCE books_sq
RESTART WITH 4;
