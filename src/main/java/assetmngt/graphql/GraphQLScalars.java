package assetmngt.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class GraphQLScalars {
    public static GraphQLScalarType GraphQLDate = new GraphQLScalarType("Date", "Date yyyy-MMM-dd", new Coercing<LocalDate, String>() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

        private LocalDate convertImpl(Object input) {
            if (input instanceof LocalDate) {
                return (LocalDate) input;
            }
            if (input instanceof String) {
                return LocalDate.parse((String) input, dateTimeFormatter);
            }
            return null;
        }

        @Override
        public String serialize(Object input) {
            LocalDate result = convertImpl(input);
            if (result == null) {
                throw new CoercingSerializeException("Invalid value '" + input + "' for LocalDate");
            }
            return dateTimeFormatter.format(result);
        }

        @Override
        public LocalDate parseValue(Object input) {
            LocalDate result = convertImpl(input);
            if (result == null) {
                throw new CoercingParseValueException("Invalid value '" + input + "' for Date");
            }
            return result;
        }

        @Override
        public LocalDate parseLiteral(Object input) {
            if (!(input instanceof StringValue))
                return null;
            String value = ((StringValue) input).getValue();
            return convertImpl(value);
        }
    });
}
