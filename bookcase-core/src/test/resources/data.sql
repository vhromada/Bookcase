INSERT INTO categories (id, category_name, position, created_user, created_time, updated_user, updated_time)
VALUES (1, 'Category 1 Name', 0, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO categories (id, category_name, position, created_user, created_time, updated_user, updated_time)
VALUES (2, 'Category 2 Name', 1, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO categories (id, category_name, position, created_user, created_time, updated_user, updated_time)
VALUES (3, 'Category 3 Name', 2, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});

INSERT INTO authors (id, first_name, middle_name, last_name, position, created_user, created_time, updated_user, updated_time)
VALUES (1, 'Author 1 First Name', '', 'Author 1 Last Name', 0, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO authors (id, first_name, middle_name, last_name, position, created_user, created_time, updated_user, updated_time)
VALUES (2, 'Author 2 First Name', 'Author 2 Middle Name', 'Author 2 Last Name', 1, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO authors (id, first_name, middle_name, last_name, position, created_user, created_time, updated_user, updated_time)
VALUES (3, 'Author 3 First Name', '', 'Author 3 Last Name', 2, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});

INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, note, position, created_user, created_time, updated_user, updated_time)
VALUES (1, 'Book 1 Czech Name', 'Book 1 Original Name', 'Book 1 ISBN', 2001, 'Book 1 Description', 'Book 1 Note', 0, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, note, position, created_user, created_time, updated_user, updated_time)
VALUES (2, 'Book 2 Czech Name', 'Book 2 Original Name', '', 2002, 'Book 2 Description', '', 1, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, note, position, created_user, created_time, updated_user, updated_time)
VALUES (3, 'Book 3 Czech Name', 'Book 3 Original Name', 'Book 3 ISBN', 2003, 'Book 3 Description', 'Book 3 Note', 2, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});

INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (1, 1, 'PAPER', 'Book 1 Item 1 Note', 0, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (2, 1, 'PDF', '', 1, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (3, 1, 'TXT', 'Book 1 Item 3 Note', 2, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (4, 2, 'PAPER', 'Book 2 Item 1 Note', 0, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (5, 2, 'PDF', '', 1, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (6, 2, 'TXT', 'Book 2 Item 3 Note', 2, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (7, 3, 'PAPER', 'Book 3 Item 1 Note', 0, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (8, 3, 'PDF', '', 1, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time)
VALUES (9, 3, 'TXT', 'Book 3 Item 3 Note', 2, 10, {ts '2020-01-01 00:00:00'}, 2, {ts '2020-01-02 00:00:00'});

INSERT INTO item_languages (item, item_language)
VALUES (1, 'CZ');
INSERT INTO item_languages (item, item_language)
VALUES (1, 'EN');
INSERT INTO item_languages (item, item_language)
VALUES (2, 'CZ');
INSERT INTO item_languages (item, item_language)
VALUES (3, 'EN');
INSERT INTO item_languages (item, item_language)
VALUES (4, 'CZ');
INSERT INTO item_languages (item, item_language)
VALUES (4, 'EN');
INSERT INTO item_languages (item, item_language)
VALUES (5, 'CZ');
INSERT INTO item_languages (item, item_language)
VALUES (6, 'EN');
INSERT INTO item_languages (item, item_language)
VALUES (7, 'CZ');
INSERT INTO item_languages (item, item_language)
VALUES (7, 'EN');
INSERT INTO item_languages (item, item_language)
VALUES (8, 'CZ');
INSERT INTO item_languages (item, item_language)
VALUES (9, 'EN');

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

ALTER SEQUENCE items_sq
RESTART WITH 10;
