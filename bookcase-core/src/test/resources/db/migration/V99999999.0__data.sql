INSERT INTO accounts (id, uuid, username, password) VALUES (accounts_sq.nextval, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', 'Account 1 username', 'Account 1 password');
INSERT INTO accounts (id, uuid, username, password) VALUES (accounts_sq.nextval, '0998ab47-0d27-4538-b551-ee7a471cfcf1', 'Account 2 username', 'Account 2 password');
INSERT INTO account_roles (account, role) VALUES ((SELECT id FROM accounts WHERE uuid = 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014'), (SELECT id FROM roles WHERE role_name = 'ROLE_USER'));
INSERT INTO account_roles (account, role) VALUES ((SELECT id FROM accounts WHERE uuid = '0998ab47-0d27-4538-b551-ee7a471cfcf1'), (SELECT id FROM roles WHERE role_name = 'ROLE_ADMIN'));
INSERT INTO account_roles (account, role) VALUES ((SELECT id FROM accounts WHERE uuid = '0998ab47-0d27-4538-b551-ee7a471cfcf1'), (SELECT id FROM roles WHERE role_name = 'ROLE_USER'));
INSERT INTO authors (id, first_name, middle_name, last_name, position, created_user, created_time, updated_user, updated_time) VALUES (authors_sq.nextval, 'Author 1 First Name', '', 'Author 1 Last Name', 0, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO authors (id, first_name, middle_name, last_name, position, created_user, created_time, updated_user, updated_time) VALUES (authors_sq.nextval, 'Author 2 First Name', 'Author 2 Middle Name', 'Author 2 Last Name', 1, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO authors (id, first_name, middle_name, last_name, position, created_user, created_time, updated_user, updated_time) VALUES (authors_sq.nextval, 'Author 3 First Name', '', 'Author 3 Last Name', 2, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, note, position, created_user, created_time, updated_user, updated_time) VALUES (books_sq.nextval, 'Book 1 Czech Name', 'Book 1 Original Name', 'Book 1 ISBN', 2001, 'Book 1 Description', 'Book 1 Note', 0, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, note, position, created_user, created_time, updated_user, updated_time) VALUES (books_sq.nextval, 'Book 2 Czech Name', 'Book 2 Original Name', '', 2002, 'Book 2 Description', '', 1, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO books (id, czech_name, original_name, isbn, issue_year, description, note, position, created_user, created_time, updated_user, updated_time) VALUES (books_sq.nextval, 'Book 3 Czech Name', 'Book 3 Original Name', 'Book 3 ISBN', 2003, 'Book 3 Description', 'Book 3 Note', 2, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name'), 'PAPER', 'Book 1 Item 1 Note', 0, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name'), 'PDF', '', 1, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name'), 'TXT', 'Book 1 Item 3 Note', 2, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 2 Czech Name'), 'PAPER', 'Book 2 Item 1 Note', 0, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 2 Czech Name'), 'PDF', '', 1, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 2 Czech Name'), 'TXT', 'Book 2 Item 3 Note', 2, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 3 Czech Name'), 'PAPER', 'Book 3 Item 1 Note', 0, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 3 Czech Name'), 'PDF', '', 1, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO items (id, book, format, note, position, created_user, created_time, updated_user, updated_time) VALUES (items_sq.nextval, (SELECT id FROM books WHERE czech_name = 'Book 3 Czech Name'), 'TXT', 'Book 3 Item 3 Note', 2, 'd53b2577-a3de-4df7-a8dd-2e6d9e5c1014', '2020-01-01 00:00:00', '0998ab47-0d27-4538-b551-ee7a471cfcf1', '2020-01-02 00:00:00');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name') AND format = 'PAPER'), 'CZ');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name') AND format = 'PAPER'), 'EN');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name') AND format = 'PDF'), 'CZ');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name') AND format = 'TXT'), 'EN');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 2 Czech Name') AND format = 'PAPER'), 'CZ');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 2 Czech Name') AND format = 'PAPER'), 'EN');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 2 Czech Name') AND format = 'PDF'), 'CZ');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 2 Czech Name') AND format = 'TXT'), 'EN');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 3 Czech Name') AND format = 'PAPER'), 'CZ');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 3 Czech Name') AND format = 'PAPER'), 'EN');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 3 Czech Name') AND format = 'PDF'), 'CZ');
INSERT INTO item_languages (item, item_language) VALUES ((SELECT id FROM items WHERE book = (SELECT id FROM books WHERE czech_name = 'Book 3 Czech Name') AND format = 'TXT'), 'EN');
INSERT INTO book_authors (book, author) VALUES ((SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name'), (SELECT id FROM authors WHERE first_name = 'Author 1 First Name'));
INSERT INTO book_authors (book, author) VALUES ((SELECT id FROM books WHERE czech_name = 'Book 1 Czech Name'), (SELECT id FROM authors WHERE first_name = 'Author 2 First Name'));
INSERT INTO book_authors (book, author) VALUES ((SELECT id FROM books WHERE czech_name = 'Book 2 Czech Name'), (SELECT id FROM authors WHERE first_name = 'Author 2 First Name'));
INSERT INTO book_authors (book, author) VALUES ((SELECT id FROM books WHERE czech_name = 'Book 3 Czech Name'), (SELECT id FROM authors WHERE first_name = 'Author 3 First Name'));
