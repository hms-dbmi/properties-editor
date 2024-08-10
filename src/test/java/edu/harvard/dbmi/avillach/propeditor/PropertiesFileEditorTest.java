package edu.harvard.dbmi.avillach.propeditor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

class PropertiesFileEditorTest {

    @Test
    void shouldNotOpenFileThatDNE(@TempDir File testDir) {
        Optional<Properties> actual =
            new PropertiesFileEditor().load(Path.of(testDir.getAbsolutePath(), "dne.properties"));
        Optional<Properties> expected = Optional.empty();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotOpenFileThatIsDir(@TempDir File testDir) {
        Optional<Properties> actual =
            new PropertiesFileEditor().load(testDir.toPath());
        Optional<Properties> expected = Optional.empty();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldOpenPropertiesFile(@TempDir File testDir) throws IOException {
        Path propFilePath = Path.of(testDir.getAbsolutePath(), "test.properties");
        Properties p = new Properties();
        p.setProperty("key", "value");
        p.store(new FileOutputStream(propFilePath.toString()), null);

        Optional<Properties> actual =
            new PropertiesFileEditor().load(propFilePath);
        Optional<Properties> expected = Optional.of(p);

        Assertions.assertEquals(expected, actual);
    }
}