package com.logistic.project.configuration;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Component
@AllArgsConstructor
public class DemoDataWriter implements ApplicationRunner {
    private MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) {
        for (File file : Objects.requireNonNull(new File("classpath:resources/mongo").listFiles(File::isFile))) {
            insertDocumentsFromMongoExtendedJsonFile(file.toPath());
        }
    }

    private void insertDocumentsFromMongoExtendedJsonFile(Path path) {
        try {
            String collectionName = path.getFileName().toString().replace(".json", "");
            Files.readAllLines(path).forEach(l -> mongoTemplate.getCollection(collectionName).insertOne(Document.parse(l)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
