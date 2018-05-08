package cz.vhromada.book.repository;

import cz.vhromada.book.domain.Book;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An interface represents repository for books.
 *
 * @author Vladimir Hromada
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
}
