package edu.harvard.dbmi.avillach.propeditor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class ArgumentParserTest {

    @Test
    void shouldParseName() {
        ArgumentParser parser = new ArgumentParser();
        Optional<Path> actual = parser.parseFileName(new String[]{"foo.txt"});
        Optional<Path> expected = Optional.of(Path.of("foo.txt"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotParseName() {
        ArgumentParser parser = new ArgumentParser();
        Optional<Path> actual = parser.parseFileName(new String[]{});
        Optional<Path> expected = Optional.empty();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldParseNoPropertiesForNoArgs() {
        ArgumentParser parser = new ArgumentParser();
        Map<String, String> actual = parser.parsePropertyChanges(new String[]{});
        HashMap<Object, Object> expected = new HashMap<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldParseNoPropertiesForJustName() {
        ArgumentParser parser = new ArgumentParser();
        Map<String, String> actual = parser.parsePropertyChanges(new String[]{"foo.txt"});
        Map<String, String> expected = new HashMap<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldParseNoPropertiesForJustNameAndKey() {
        ArgumentParser parser = new ArgumentParser();
        Map<String, String> actual = parser.parsePropertyChanges(new String[]{"foo.txt", "key"});
        Map<String, String> expected = new HashMap<>();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldParseProperty() {
        ArgumentParser parser = new ArgumentParser();
        Map<String, String> actual = parser.parsePropertyChanges(new String[]{"foo.txt", "key", "value"});
        Map<String, String> expected = Map.of("key", "value");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldParsePropertyMismatch() {
        ArgumentParser parser = new ArgumentParser();
        Map<String, String> actual = parser.parsePropertyChanges(new String[]{"foo.txt", "key", "value", "key1"});
        Map<String, String> expected = Map.of("key", "value");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldParseTwoProperties() {
        ArgumentParser parser = new ArgumentParser();
        Map<String, String> actual = parser.parsePropertyChanges(new String[]{"foo.txt", "key", "val", "key1", "val1"});
        Map<String, String> expected = Map.of("key", "val", "key1", "val1");

        Assertions.assertEquals(expected, actual);
    }
}