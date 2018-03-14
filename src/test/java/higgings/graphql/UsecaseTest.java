package higgings.graphql;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
public class UsecaseTest {

    @Test
    public void query_simple_case() {
        String schema = "type Query{hello: String}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello}");

        Map<String, String> data = executionResult.getData();
        assertThat(data)
                .containsEntry("hello", "world");
    }

    @Test
    public void query_basic_case() {
        String schema = "type Link {\n" +
                "  url: String!\n" +
                "  description: String!\n" +
                "}\n" +
                "\n" +
                "type Query {\n" +
                "  allLinks: [Link]\n" +
                "}\n" +
                "\n" +
                "schema {\n" +
                "  query: Query\n" +
                "}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("allLinks", environment -> {
                    return asList(new Link("google.fr", "Google"), new Link("technbolts.org", "Technbolts"));
                }))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{allLinks{url}}");

        Map<String, ?> data = executionResult.getData();
        assertThat(data).containsKey("allLinks");
        assertThat((List<Map<String, String>>) data.get("allLinks"))
                .containsOnly(map("url", "google.fr"), map("url", "technbolts.org"));

    }

    @Test
    public void mutation_single_one() {
        String schema = "type Link {\n" +
                "  url: String!\n" +
                "  description: String!\n" +
                "}\n" +
                "\n" +
                "type Query {\n" +
                "  allLinks: [Link]\n" +
                "}\n" +
                "type Mutation {\n" +
                "  createLink(url: String!, description: String!): Link\n" +
                "}" +
                "\n" +
                "schema {\n" +
                "  query: Query\n" +
                "  mutation: Mutation\n" +
                "}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Mutation", builder -> builder.dataFetcher("createLink", environment -> {
                    String url = environment.getArgument("url");
                    String dsc = environment.getArgument("description");
                    return new Link("http://" + url, dsc);
                }))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("mutation cruck {\n" +
                "  createLink(url: \"puck.io\", description: \"Puck\") {\n" +
                "    url\n" +
                "    description\n" +
                "  }\n" +
                "}");

        Map<String, ?> data = executionResult.getData();
        assertThat(data).containsKey("createLink");
        assertThat((Map<String, String>) data.get("createLink"))
                .containsEntry("url", "http://puck.io")
                .containsEntry("description", "Puck");

    }

    @Test
    public void mutation_multiple_ones() {
        String schema = "type Link {\n" +
                "  url: String!\n" +
                "  description: String!\n" +
                "}\n" +
                "\n" +
                "type Query {\n" +
                "  allLinks: [Link]\n" +
                "}\n" +
                "type Mutation {\n" +
                "  createLink(url: String!, description: String!): Link\n" +
                "}" +
                "\n" +
                "schema {\n" +
                "  query: Query\n" +
                "  mutation: Mutation\n" +
                "}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        List<Link> links = new ArrayList<>();

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Mutation", builder -> builder.dataFetcher("createLink", environment -> {
                    String url = environment.getArgument("url");
                    String dsc = environment.getArgument("description");
                    Link link = new Link("http://" + url, dsc);
                    links.add(link);
                    return link;
                }))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();

        // Note that each mutation has a dedicated name to ease result correspondance
        ExecutionResult executionResult = build.execute("mutation cruck {\n" +
                "  puck: createLink(url: \"puck.io\", description: \"Puck\") {\n" +
                "    url\n" +
                "    description\n" +
                "  }\n" +
                "  oberon: createLink(url: \"oberon.io\", description: \"Oberon\") {\n" +
                "    url\n" +
                "    description\n" +
                "  }\n" +
                "  titania: createLink(url: \"titania.io\", description: \"Titania\") {\n" +
                "    url\n" +
                "    description\n" +
                "  }\n" +
                "}");

        Map<String, ?> data = executionResult.getData();
        assertThat((Map<String, String>) data.get("puck"))
                .containsEntry("url", "http://puck.io")
                .containsEntry("description", "Puck");
        assertThat((Map<String, String>) data.get("oberon"))
                .containsEntry("url", "http://oberon.io")
                .containsEntry("description", "Oberon");
        assertThat((Map<String, String>) data.get("titania"))
                .containsEntry("url", "http://titania.io")
                .containsEntry("description", "Titania");

        assertThat(links).hasSize(3);
    }

    @Test
    public void nested_query() {
        String schema = stripMargin("" +
                "| input Paging {                            " +
                "|    offset: Int                            " +
                "|    count: Int                             " +
                "| }                                         " +
                "| type PageInfos {                          " +
                "|    offset: Int                            " +
                "|    count: Int                             " +
                "| }                                         " +
                "|                                           " +
                "| type Person {                             " +
                "|   name: String!                           " +
                "|   comments(paging:Paging): PagedComments! " +
                "| }                                         " +
                "|                                           " +
                "| type PagedComments {                      " +
                "|     pageInfos: PageInfos!                 " +
                "|     comments: [Comment!]                  " +
                "| }                                         " +
                "| type Comment {                            " +
                "|   text: String!                           " +
                "|   author: Person!                         " +
                "|   post: Post!                             " +
                "| }                                         " +
                "|                                           " +
                "| type Post {                               " +
                "|   id: ID!                                 " +
                "|   description: String!                    " +
                "|   author: Person!                         " +
                "|   comments(paging:Paging): PagedComments! " +
                "| }                                         " +
                "|                                           " +
                "| type Query {                              " +
                "|   post(id:ID!): Post                      " +
                "| }                                         " +
                "|                                           " +
                "| schema {                                  " +
                "|   query: Query                            " +
                "| }                                         ");

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("post", environment -> {
                    String id = environment.getArgument("id");
                    return new Post(id, "description");
                }))
                .type("Post", builder -> builder
                        .dataFetcher("author", environment -> {
                            Post post = environment.getSource();
                            System.out.println(">> author of post <" + post + ">");
                            return new Person("J. d'Or");
                        })
                        .dataFetcher("comments", environment -> {
                            Post post = environment.getSource();
                            System.out.println(">> PagedComments of post <" + post + ">");
                            System.out.println(">>   arguments: " + environment.getArguments());
                            return new PagedComments(new PageInfos(5, 15),
                                    new Comment("Comment 1"),
                                    new Comment("Comment 2"));
                        })
                )
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();

        ExecutionResult executionResult = build.execute(stripMargin("" +
                "| {                                               " +
                "|     post(id:'p23') {                            " +
                "|         id                                      " +
                "|         description                             " +
                "|         author {                                " +
                "|             name                                " +
                "|         }                                       " +
                "|         comments(paging:{offset:7, count:10}) { " +
                "|             pageInfos {                         " +
                "|                 offset                          " +
                "|                 count                           " +
                "|             }                                   " +
                "|             comments {                          " +
                "|                 text                            " +
                "|             }                                   " +
                "|         }                                       " +
                "|     }                                           " +
                "| }                                               "));

        executionResult.getErrors().forEach(System.out::println);
        Map<String, ?> data = executionResult.getData();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(data));

        assertThat(data).isNotEmpty();
    }

    private static String stripMargin(String s) {
        return s.replace("|", "\n").replace('\'', '"');
    }

    public static class Comment {
        private final String text;

        public Comment(String text) {
            this.text = text;
        }
    }

    public static class PageInfos {
        private final int offset;
        private final int count;

        public PageInfos(int offset, int count) {
            this.offset = offset;
            this.count = count;
        }
    }

    public static class PagedComments {
        private final PageInfos pageInfos;
        private final List<Comment> comments = new ArrayList<>();

        public PagedComments(PageInfos pageInfos, Comment... comments) {
            this.pageInfos = pageInfos;
            this.comments.addAll(Arrays.asList(comments));
        }
    }

    public static class Person {
        private final String name;

        public Person(String name) {
            this.name = name;
        }
    }

    public static class Post {
        private final String id;
        private final String description;

        public Post(String id, String description) {
            this.id = id;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Post{" +
                    "id='" + id + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static <K, V> Map<K, V> map(K key, V value) {
        Map<K, V> m = new HashMap<>();
        m.put(key, value);
        return m;
    }

    public static class Link {
        final String url;
        final String description;

        Link(String url, String description) {
            this.url = url;
            this.description = description;
        }
    }
}
