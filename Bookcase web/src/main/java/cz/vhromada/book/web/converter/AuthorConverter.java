package cz.vhromada.book.web.converter;

import cz.vhromada.book.entity.Author;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

/**
 * A class represents converter between {@link Author} and {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@Component("authorConverter")
public class AuthorConverter extends BidirectionalConverter<Author, Integer> {

    @Override
    public Integer convertTo(final Author source, final Type<Integer> type, final MappingContext mappingContext) {
        if (source == null) {
            return null;
        }

        return source.getId();
    }

    @Override
    public Author convertFrom(final Integer source, final Type<Author> type, final MappingContext mappingContext) {
        if (source == null) {
            return null;
        }

        final Author author = new Author();
        author.setId(source);

        return author;
    }

}
