package com.swag.pe.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class JSONReader {

    private static final String FILE_PATH = "src/test/resources/testdata.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static JsonNode cachedJson;
    private static long lastModified;

    private JSONReader() {
    }

    private static synchronized JsonNode getJsonData() {
        File jsonFile = new File(FILE_PATH);
        long fileTimestamp = jsonFile.lastModified();
        if (cachedJson == null || fileTimestamp != lastModified) {
            try {
                cachedJson = OBJECT_MAPPER.readTree(jsonFile);
                lastModified = fileTimestamp;
            } catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo JSON: " + e.getMessage(), e);
            }
        }
        return cachedJson;
    }

    public static String getValue(String key) {
        JsonNode jsonData = getJsonData();
        if (!jsonData.hasNonNull(key)) {
            throw new IllegalArgumentException("La clave '" + key + "' no existe en testdata.json");
        }
        return jsonData.get(key).asText();
    }
}
