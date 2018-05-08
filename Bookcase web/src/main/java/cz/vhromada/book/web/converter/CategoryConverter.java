package cz.vhromada.book.web.converter;

import cz.vhromada.book.entity.Category;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

/**
 * A class represents converter between {@link Category} and {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@Component("categoryConverter")
public class CategoryConverter extends BidirectionalConverter<Category, Integer> {

    @Override
    public Integer convertTo(final Category source, final Type<Integer> type, final MappingContext mappingContext) {
        if (source == null) {
            return null;
        }

        return source.getId();
    }

    @Override
    public Category convertFrom(final Integer source, final Type<Category> type, final MappingContext mappingContext) {
        if (source == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(source);

        return category;
    }

}
