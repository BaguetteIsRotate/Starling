package com.baguetteisrotate.starling;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.baguetteisrotate.starling.graphing.Graph;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;

public class Tracker<T extends Entry> {

    private final Path path;
    private final Class<T> entryClass;

    public static final ObjectMapper MAPPER =
            new ObjectMapper()
                    .registerModule(new JavaTimeModule());

    public Tracker(Path path, Class<T> entryClass) {
        this.path = path;
        this.entryClass = entryClass;
    }

    public List<T> loadData() {
        try {
            if (Files.notExists(path)) {
                return new ArrayList<>();
            }

            return MAPPER.readValue(
                    path.toFile(),
                    MAPPER.getTypeFactory()
                            .constructCollectionType(List.class, entryClass)
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not load mood history from " + path, e
            );
        }
    }

    public void save(List<T> entries) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValue(path.toFile(), entries);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not save mood history to " + path, e
            );
        }
    }
}