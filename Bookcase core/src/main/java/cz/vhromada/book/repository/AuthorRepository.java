package cz.vhromada.book.repository;

import cz.vhromada.book.domain.Author;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An interface represents repository for authors.
 *
 * @author Vladimir Hromada
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
