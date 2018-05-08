package cz.vhromada.book.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cz.vhromada.common.Language;
import cz.vhromada.common.Movable;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * A class represents book.
 *
 * @author Vladimir Hromada
 */
@Entity
@Table(name = "books")
public class Book implements Movable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "book_generator", sequenceName = "books_sq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    private Integer id;

    /**
     * Czech name
     */
    @Column(name = "czech_name")
    private String czechName;

    /**
     * Original name
     */
    @Column(name = "original_name")
    private String originalName;

    /**
     * Languages
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book"))
    @Column(name = "book_language")
    @Enumerated(EnumType.STRING)
    @Fetch(FetchMode.SELECT)
    private List<Language> languages;

    /**
     * ISBN
     */
    private String isbn;

    /**
     * Issue year
     */
    @Column(name = "issue_year")
    private Integer issueYear;

    /**
     * Description
     */
    private String description;

    /**
     * True if there is electronic version
     */
    private Boolean electronic;

    /**
     * True if there is paper version
     */
    private Boolean paper;

    /**
     * Note
     */
    private String note;

    /**
     * Position
     */
    private Integer position;

    /**
     * Authors
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book"), inverseJoinColumns = @JoinColumn(name = "author"))
    @OrderBy("position")
    @Fetch(FetchMode.SELECT)
    private List<Author> authors;

    /**
     * Categories
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_categories", joinColumns = @JoinColumn(name = "book"), inverseJoinColumns = @JoinColumn(name = "category"))
    @OrderBy("position")
    @Fetch(FetchMode.SELECT)
    private List<Category> categories;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Returns czech name.
     *
     * @return czech name
     */
    public String getCzechName() {
        return czechName;
    }

    /**
     * Sets a new value to czech name.
     *
     * @param czechName new value
     */
    public void setCzechName(final String czechName) {
        this.czechName = czechName;
    }

    /**
     * Returns original name.
     *
     * @return original name
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Sets a new value to original name.
     *
     * @param originalName new value
     */
    public void setOriginalName(final String originalName) {
        this.originalName = originalName;
    }

    /**
     * Returns languages.
     *
     * @return languages
     */
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     * Sets a new value to languages.
     *
     * @param languages new value
     */
    public void setLanguages(final List<Language> languages) {
        this.languages = languages;
    }

    /**
     * Returns ISBN.
     *
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets a new value to ISBN.
     *
     * @param isbn new value
     */
    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns issue year.
     *
     * @return issue year
     */
    public Integer getIssueYear() {
        return issueYear;
    }

    /**
     * Sets a new value to issue year.
     *
     * @param issueYear new value
     */
    public void setIssueYear(final Integer issueYear) {
        this.issueYear = issueYear;
    }

    /**
     * Returns description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a new value to description.
     *
     * @param description new value
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Returns true if there is electronic version.
     *
     * @return true if there is electronic version
     */
    public Boolean getElectronic() {
        return electronic;
    }

    /**
     * Sets a new value to if there is electronic version.
     *
     * @param electronic new value
     */
    public void setElectronic(final Boolean electronic) {
        this.electronic = electronic;
    }

    /**
     * Returns true if there is paper version.
     *
     * @return true if there is paper version
     */
    public Boolean getPaper() {
        return paper;
    }

    /**
     * Sets a new value to if there is paper version.
     *
     * @param paper new value
     */
    public void setPaper(final Boolean paper) {
        this.paper = paper;
    }

    /**
     * Returns note.
     *
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets a new value to note.
     *
     * @param note new value
     */
    public void setNote(final String note) {
        this.note = note;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Integer position) {
        this.position = position;
    }

    /**
     * Returns authors.
     *
     * @return authors
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets a new value to authors.
     *
     * @param authors new value
     */
    public void setAuthors(final List<Author> authors) {
        this.authors = authors;
    }

    /**
     * Returns categories.
     *
     * @return categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Sets a new value to categories.
     *
     * @param categories new value
     */
    public void setCategories(final List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Book) || id == null) {
            return false;
        }

        return id.equals(((Book) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    @Override
    public String toString() {
        return String.format("Book [id=%d, czechName=%s, originalName=%s, languages=%s, isbn=%s, issueYear=%d, description=%s, electronic=%b, paper=%b, "
                + "note=%s, position=%d, authors=%s, categories=%s]", id, czechName, originalName, languages, isbn, issueYear, description, electronic, paper,
            note, position, authors, categories);
    }

}
