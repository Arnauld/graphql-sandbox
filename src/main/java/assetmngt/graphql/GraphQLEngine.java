package assetmngt.graphql;

import assetmngt.core.*;
import assetmngt.graphql.dto.Converters;
import assetmngt.graphql.dto.HoldingView;
import assetmngt.graphql.dto.ModelView;
import assetmngt.graphql.dto.PageInfos;
import assetmngt.graphql.dto.PagingInput;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.visibility.BlockedFields;
import graphql.schema.visibility.GraphqlFieldVisibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static assetmngt.graphql.GraphQLScalars.GraphQLDate;
import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class GraphQLEngine {

    private static final Logger LOG = LoggerFactory.getLogger(GraphQLEngine.class);
    private final GraphQL graphQL;

    public GraphQLEngine(Models models,
                         Instruments instruments,
                         Quotations quotations) {
        this.graphQL = initGraphQL(
                models,
                instruments,
                quotations,
                new Converters());
    }

    public ExecutionResult execute(String query) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .context(new GraphQLContext())
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);
        executionResult
                .getErrors()
                .forEach(error -> LOG.error("Execution error {}", error));
        return executionResult;
    }

    private GraphQL initGraphQL(Models models,
                                Instruments instruments,
                                Quotations quotations,
                                Converters converters) {
        // By default every fields defined in a GraphqlSchema is available.
        // There are cases where you may want to restrict certain fields depending on the user.
        GraphqlFieldVisibility blockedFields = BlockedFields.newBlock()
                .addPattern("__source")
                .build();

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .fieldVisibility(blockedFields)
                .scalar(GraphQLDate)
                .type("Query", builder -> builder
                        .dataFetcher("model", env -> {
                            String modelCode = env.getArgument("code");
                            LOG.info("Querying for model {}", modelCode);
                            Model model = models.byCode(modelCode);
                            LOG.info("Model used {}", model);
                            return converters.toView(model);
                        }))
                .type("Model", builder -> builder
                        .dataFetcher("holdings", env -> {
                            ModelView model = env.getSource();

                            Map<String, Object> pagingAsMap = env.getArgument("paging");
                            PagingInput pagingInput = PagingInput.fromMap(pagingAsMap);
                            LOG.info("Fetching holdings for model {} with paging {}", model.__source.code, pagingInput);

                            Paging paging = new Paging(pagingInput.asOffset(), pagingInput.count);
                            List<Holding> holdings = models.holdingsOf(model.__source.code, paging);
                            LOG.info("Holdings found {}", holdings);

                            List<HoldingView> holdingViews = holdings.stream().map(converters::toView).collect(Collectors.toList());

                            PageInfos pageInfos = new PageInfos();
                            pageInfos.after = "" + (pagingInput.asOffset() + holdings.size());
                            pageInfos.count = holdings.size();
                            return toMap("pageInfos", pageInfos, "holdings", holdingViews);
                        }))
                .type("Holding", builder -> builder
                        .dataFetcher("instrument", env -> {
                            HoldingView holdingView = env.getSource();
                            Isin isin = holdingView.__source.isin;
                            LOG.info("Fetching holding's instrument {}", isin);

                            Instrument instrument = instruments.byIsin(isin);
                            LOG.info("Holding's instrument {}: {}", isin, instrument);

                            return converters.toView(instrument);
                        })
                        .dataFetcher("quotation", env -> {
                            HoldingView holdingView = env.getSource();
                            Isin isin = holdingView.__source.isin;
                            LOG.info("Fetching holding's quotation {}", isin);

                            Quotation quotation = quotations.byIsin(isin);
                            LOG.info("Holding's quotation {}: {}", isin, quotation);

                            return converters.toView(quotation);
                        }))
                .build();

        String schemaAsString = loadSchema();

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaAsString);
        return newGraphQL(typeRegistry, runtimeWiring);
    }

    private static Map<String, Object> toMap(Object... kvs) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < kvs.length; i += 2) {
            map.put((String) kvs[i], kvs[i + 1]);
        }
        return map;
    }

    private static String loadSchema() {
        StringWriter sw = new StringWriter();
        try (InputStream in = GraphQLEngine.class.getResourceAsStream("/assetmngt/graphql/schema.graphqls")) {
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            char[] buffer = new char[1024 * 4];
            int n;
            while (-1 != (n = reader.read(buffer))) {
                sw.write(buffer, 0, n);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();
    }

    private static GraphQL newGraphQL(TypeDefinitionRegistry typeRegistry, RuntimeWiring runtimeWiring) {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
