package higgings.graphql;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.TypeResolver;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import higgings.TestProperties;
import higgings.graphql.model.GroupFilter;
import higgings.graphql.model.GroupsPage;
import higgings.graphql.model.MembersPage;
import higgings.graphql.model.Paging;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class AppSchemaTest {

    @Test
    public void query_simple_case() throws IOException {
        StringWriter sw = new StringWriter();

        Path startingDir = Paths.get(TestProperties.get().baseDir() + "/src/test/resources/higgings/graphql/schema");
        SchemaFinder.traverse(startingDir)
                .map(startingDir::resolve)
                .forEach(p -> readContent(p, sw));

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sw.toString());

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("groups", env -> {
                    Map<String, Object> groupFilterAsMap = env.getArgument("groupFilter");
                    Map<String, Object> pagingAsMap = env.getArgument("paging");

                    GroupFilter groupFilter = GroupFilter.fromMap(groupFilterAsMap);
                    Paging paging = Paging.fromMap(pagingAsMap);
                    System.out.println("groupFilter: " + groupFilter + ", Paging: " + paging);
                    return new GroupsPage();
                }))
                .type(TypeRuntimeWiring.newTypeWiring("Page")
                        .typeResolver(pageTypeResolver()))
                .build();

        GraphQL build = newGraphQL(typeRegistry, runtimeWiring);
        ExecutionResult executionResult = build.execute(
                ExecutionInput.newExecutionInput()
                        .query("query QueryGroup ($groupFilter:GroupFilter!) {\n" +
                                "  groups(groupFilter:$groupFilter) {\n" +
                                "    pageInfos {\n" +
                                "      after\n" +
                                "      count\n" +
                                "    }\n" +
                                "    groups {\n" +
                                "      id\n" +
                                "      name\n" +
                                "      description\n" +
                                "    }\n" +
                                "  }\n" +
                                "}")
                        .variables(toMap("groupFilter", toMap("nameGlob", "c*")))
                        .build());

        executionResult.getErrors()
                .stream()
                .forEach(error -> System.err.println("E>" + error));

        assertThat(executionResult.getErrors()).isEmpty();

        Map<String, String> data = executionResult.getData();
        assertThat(data)
                .containsEntry("hello", "world");
    }

    private static Map<String, Object> toMap(Object... kvs) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < kvs.length; i += 2) {
            map.put((String) kvs[i], kvs[i + 1]);
        }
        return map;
    }

    private TypeResolver pageTypeResolver() {
        return new TypeResolver() {
            @Override
            public GraphQLObjectType getType(TypeResolutionEnvironment env) {
                Object javaObject = env.getObject();
                if (javaObject instanceof MembersPage) {
                    return (GraphQLObjectType) env.getSchema().getType("MembersPage");
                }
                if (javaObject instanceof GroupsPage) {
                    return (GraphQLObjectType) env.getSchema().getType("GroupsPage");
                }
                throw new RuntimeException("Unknown type");
            }
        };
    }

    public static void readContent(Path path, StringWriter sw) {
        try (Reader reader = Files.newBufferedReader(path)) {
            char[] buffer = new char[1024 * 4];
            int n;
            while (-1 != (n = reader.read(buffer))) {
                sw.write(buffer, 0, n);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static GraphQL newGraphQL(TypeDefinitionRegistry typeRegistry, RuntimeWiring runtimeWiring) {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
