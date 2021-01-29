package com.ankoki.olysene.files;

import com.ankoki.olysene.utils.Utils;
import com.ankoki.olysene.utils.Validate;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/*
 * This is to parse JSON files for language. An example of a en_gb.json is:
 * {
 *     "key": "value"
 * }
 * This should only be called onEnable and that instance should be passed around.
 */
public class Messages {
    private static String NULL_VALUE = "<NULL>";
    private final JsonObject lang;

    public Messages(JavaPlugin plugin, String resourceName) throws JsonParseException, IOException, IllegalStateException {
        File langFolder = new File(plugin.getDataFolder(), "lang");
        File jsonFile = new File(langFolder, resourceName);
        if (!jsonFile.exists()) {
            try {
                InputStream in = plugin.getResource(resourceName);
                Validate.notNull(in, "You need to have the specified json file in your plugins resource folder!");
                Files.copy(in, jsonFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ignored) {}
        }
        JsonParser parser = new JsonParser();
        lang = parser.parse(new BufferedReader(new FileReader(jsonFile))).getAsJsonObject();
    }

    public Messages(JavaPlugin plugin, String resourceName, String nullValue) throws IOException {
        this(plugin, resourceName);
        NULL_VALUE = nullValue;
    }

    public String msg(String key) {
        return lang.has(key) ? Utils.coloured(lang.get(key).getAsString()) : NULL_VALUE;
    }
}