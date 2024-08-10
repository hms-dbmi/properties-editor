package edu.harvard.dbmi.avillach.propeditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

public class PropertiesFileEditor {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesFileEditor.class);


    public Optional<Properties> load(Path path) {
        File propFile = new File(path.toString());
        if (!propFile.exists()) {
            LOG.error("File {} DNE", path);
            return Optional.empty();
        }
        if (!propFile.isFile()) {
            LOG.error("File {} is not file (dir / special?)", path);
            return Optional.empty();
        }

        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(propFile)) {
            properties.load(in);
            return Optional.of(properties);
        } catch (IOException e) {
            LOG.error("Error opening {}", path, e);
        }

        return Optional.empty();
    }

    public boolean persist(Properties properties, Path path) {
        try (FileOutputStream s = new FileOutputStream(path.toFile())) {
            properties.store(s, null);
        } catch (IOException e) {
            LOG.error("Failed to persist", e);
            return false;
        }
        return true;
    }
}
