package com.coderfromscratch.httpserver.config;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.coderfromscratch.httpserver.MAIN.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;

    private List<Configuration>  hosts ;


    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager==null)
            myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    /**
     *  Used to load a configuration file by the path provided
     */
    public void loadConfigurationFile(String filePath) throws IOException {
        String jason=new String(Files.readAllBytes(Path.of(filePath)));
        ObjectMapper objectMapper=new ObjectMapper();

        hosts=objectMapper.readValue(jason,new TypeReference<List<Configuration>>(){});

    }


    public List<Configuration> getCurrentConfiguration() {

        return hosts;
    }
}
