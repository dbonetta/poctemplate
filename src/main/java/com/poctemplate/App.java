package com.poctemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Handlebars handlebars = new Handlebars();
        try {
            // Leggi il template HTML da resources
            String templateString = new String(Files.readAllBytes(Paths.get("src/main/resources/template.html")));
            Template template = handlebars.compileInline(templateString);

            // Leggi il file JSON da resources
            byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/data.json"));

            // Converti il JSON in mappa
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.readValue(jsonData, Map.class);

            // Applica i dati al template
            String output = template.apply(data);

            // Definisci il percorso del file di output
            Path outputPath = Paths.get("src/main/resources/output.html");

            // Scrivi l'output nel file HTML
            Files.write(outputPath, output.getBytes(StandardCharsets.UTF_8));

            System.out.println("Il file HTML è stato generato con successo: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}