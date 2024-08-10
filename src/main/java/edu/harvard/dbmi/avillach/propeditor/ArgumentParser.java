package edu.harvard.dbmi.avillach.propeditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArgumentParser {

    private static final Logger LOG = LoggerFactory.getLogger(ArgumentParser.class);

    public Optional<Path> parseFileName(String[] args) {
        if (args.length == 0) {
            LOG.info("No filename specified in args.");
            return Optional.empty();
        }
        String filename = args[0];
        LOG.info("Using first arg ({}) as filename", filename);
        return Optional.of(Path.of(filename));
    }

    public Map<String, String> parsePropertyChanges(String[] args) {
        if (args.length % 2 == 0) {
            LOG.warn("Mismatched key value pairs. The last value will be discarded");
        }
        LOG.info("Received {} args, so assuming {} key value pairs", args.length, (args.length - 1) / 2);
        HashMap<String, String> pairs = new HashMap<>();
        if (args.length > 2) {
            for (int i = 0; i < (args.length - 1) / 2; i++) {
                pairs.put(args[i * 2 + 1], args[i * 2 + 2]);
            }
        }
        return pairs;
    }
}
