package higgings.graphql;

import higgings.TestProperties;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SchemaFinderTest {

    private static final TestProperties testProperties = TestProperties.get();

    @Test
    public void should_() throws IOException {
        Path startingDir = Paths.get(testProperties.baseDir() + "/src/test/resources/higgings/graphql/schema");

        assertThat(SchemaFinder
                .traverse(startingDir)
                .map(Path::toString)
                .collect(Collectors.toList()))
                .containsExactly("root.graphqls", "auth/auth.graphqls", "user/user.graphqls");
    }
}