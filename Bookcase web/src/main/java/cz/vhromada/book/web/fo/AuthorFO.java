package cz.vhromada.book.web.fo;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

/**
 * A class represents FO for author.
 *
 * @author Vladimir Hromada
 */
public class AuthorFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * First name
     */
    private @NotBlank String firstName;

    /**
     * Middle name
     */
    private String middleName;

    /**
     * Last name
     */
    private @NotBlank String lastName;

    /**
     * Position
     */
    private Integer position;

    /**
     * Returns ID.
     *
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets a new value to ID.
     *
     * @param id new value
     */
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

    /**
     * Returns position.
     *
     * @return position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets a new value to position.
     *
     * @param position new value
     */
    public void setPosition(final Integer position) {
        this.position = position;
    }

    /**
     * Normalizes data.
     */
    public void normalize() {
        if (StringUtils.isEmpty(middleName)) {
            middleName = null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AuthorFO) || id == null) {
            return false;
        }

        return id.equals(((AuthorFO) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("AuthorFO [id=%d, firstName=%s, middleName=%s, lastName=%s, position=%d]", id, firstName, middleName, lastName, position);
    }

}
