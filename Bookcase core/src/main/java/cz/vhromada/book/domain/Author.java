package cz.vhromada.book.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cz.vhromada.common.Movable;

/**
 * A class represents author.
 *
 * @author Vladimir Hromada
 */
@Entity
@Table(name = "authors")
public class Author implements Movable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @SequenceGenerator(name = "author_generator", sequenceName = "authors_sq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    private Integer id;

    /**
     * First name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Middle name
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Last name
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Position
     */
    private Integer position;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Returns first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a new value to first name.
     *
     * @param firstName new value
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns middle name.
     *
     * @return middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets a new value to middle name.
     *
     * @param middleName new value
     */
    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    /**
     * Returns last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a new value to last name.
     *
     * @param lastName new value
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Author) || id == null) {
            return false;
        }

        return id.equals(((Author) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Author [id=%d, firstName=%s, middleName=%s, lastName=%s, position=%d]", id, firstName, middleName, lastName, position);
    }

}
