package edu.harvard.dbmi.avillach.propeditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("Parsing arguments");
        ArgumentParser parser = new ArgumentParser();
        Optional<Path> maybePath = parser.parseFileName(args);
        if (maybePath.isEmpty()) {
            System.exit(1);
            return;
        }
        Map<String, String> changes = parser.parsePropertyChanges(args);

        LOG.info("Loading properties file");
        PropertiesFileEditor editor = new PropertiesFileEditor();
        Optional<Properties> maybeProperties = editor.load(maybePath.get());
        if (maybeProperties.isEmpty())  {
            System.exit(1);
            return;
        }

        LOG.info("Updating properties in memory");
        Properties properties = maybeProperties.get();
        changes.forEach((key, value) -> {
            LOG.info("Setting {} to {}", key, value);
            properties.setProperty(key, value);
        });

        LOG.info("Persisting properties file changes to {}", maybePath.get());
        System.exit(editor.persist(properties, maybePath.get()) ? 0 : 1);
    }
}