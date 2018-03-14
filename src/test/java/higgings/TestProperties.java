package higgings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class TestProperties {
    public static TestProperties get() {
        TestProperties props = new TestProperties();
        props.load(TestProperties.class.getResource("/test.properties"));
        return props;
    }

    private final Properties props = new Properties();

    private void load(URL resource) {
        if (resource == null)
            throw new RuntimeException("Missing resource");

        try (InputStream in = resource.openStream()) {
            props.load(new InputStreamReader(in, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File baseDir() {
        return new File(props.getProperty("baseDir"));
    }
    public File buildDir() {
        return new File(props.getProperty("buildDir"));
    }
}
