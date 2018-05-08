package cz.vhromada.book.facade;

import cz.vhromada.book.entity.Book;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.result.Result;

/**
 * An interface represents facade for books.
 *
 * @author Vladimir Hromada
 */
public interface BookFacade extends MovableParentFacade<Book> {

    /**
     * Adds book. Sets new ID and position.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Book is null</li>
     * <li>ID isn't null</li>
     * <li>Position isn't null</li>
     * <li>Czech name is null</li>
     * <li>Czech name is empty string</li>
     * <li>Original name is null</li>
     * <li>Original name is empty string</li>
     * <li>Languages are null</li>
     * <li>Languages are empty</li>
     * <li>Languages contain null value</li>
     * <li>ISBN is empty string</li>
     * <li>Issue year isn't between 1940 and current year/</li>
     * <li>Description is null</li>
     * <li>Description is empty string</li>
     * <li>Note is empty string</li>
     * <li>Author ID is null</li>
     * <li>Author first name is empty string</li>
     * <li>Author middle name is empty string</li>
     * <li>Author last name is empty string</li>
     * <li>Author doesn't exist</li>
     * <li>Category ID is null</li>
     * <li>Category name is empty string</li>
     * <li>Category doesn't exist</li>
     * </ul>
     *
     * @param data book
     * @return result with validation errors
     */
    @Override
    Result<Void> add(Book data);

    /**
     * Updates book.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Book is null</li>
     * <li>ID is null</li>
     * <li>Position is null</li>
     * <li>Czech name is null</li>
     * <li>Czech name is empty string</li>
     * <li>Original name is null</li>
     * <li>Original name is empty string</li>
     * <li>Languages are null</li>
     * <li>Languages are empty</li>
     * <li>Languages contain null value</li>
     * <li>ISBN is empty string</li>
     * <li>Issue year isn't between 1940 and current year/</li>
     * <li>Description is null</li>
     * <li>Description is empty string</li>
     * <li>Note is empty string</li>
     * <li>Author ID is null</li>
     * <li>Author first name is empty string</li>
     * <li>Author middle name is empty string</li>
     * <li>Author last name is empty string</li>
     * <li>Author doesn't exist in data storage</li>
     * <li>Category ID is null</li>
     * <li>Category name is empty string</li>
     * <li>Category doesn't exist in data storage</li>
     * <li>Book doesn't exist in data storage</li>
     * </ul>
     *
     * @param data new value of book
     * @return result with validation errors
     */
    @Override
    Result<Void> update(Book data);

}
