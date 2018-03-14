package higgings.graphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.nio.file.FileVisitResult.CONTINUE;

public class SchemaFinder extends SimpleFileVisitor<Path> {

    private static final Logger LOG = LoggerFactory.getLogger(SchemaFinder.class);

    public static Stream<Path> traverse(Path startingDir) throws IOException {
        List<Path> paths = new ArrayList<>();

        SchemaFinder finder = new SchemaFinder(paths::add);
        Files.walkFileTree(startingDir, finder);
        finder.done();

        return paths.stream()
                .map(startingDir::relativize)
                .sorted(Comparator.comparing(Path::getNameCount))
                .map(p -> {
                    LOG.info("Consuming {}", p);
                    return p;
                });
    }

    private final PathMatcher matcher;
    private final Consumer<Path> handler;
    private int numMatches = 0;

    SchemaFinder(Consumer<Path> handler) {
        this("*.graphqls", handler);
    }

    SchemaFinder(String pattern, Consumer<Path> handler) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.handler = handler;
    }

    // Compares the glob pattern against
    // the file or directory name.
    void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            numMatches++;
            LOG.debug("Accepting {}", file);
            handler.accept(file);
        }
    }

    // Prints the total number of
    // matches to standard out.
    void done() {
        LOG.debug("#{} file(s) accepted", numMatches);
    }

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    // Invoke the pattern matching
    // method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir,
                                             BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        LOG.error("Oops while visiting {}", file, exc);
        return CONTINUE;
    }
}
