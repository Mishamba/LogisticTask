package com.logistic.project.configuration;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
@AllArgsConstructor
public class DemoDataWriter implements ApplicationRunner {
    private MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, Path> mongoCollectionDataPaths = mongoExtendedJsonFilesLookup();
        dropCollections(mongoCollectionDataPaths.keySet());
        mongoExtendedJsonFilesLookup().forEach((key, value) -> insertDocumentsFromMongoExtendedJsonFile(value, key));
    }

    private Map<String, Path> mongoExtendedJsonFilesLookup() {
        Map<String, Path> collections = new HashMap<>();
        try {
            Files.walk(Paths.get("src","main","resources","mongo"))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> collections.put(
                            filePath.getFileName().toString().replace(".json", ""),
                            filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return collections;
    }

    private void dropCollections(Set<String> collectionNames) {
        collectionNames.forEach(collectionName -> mongoTemplate.dropCollection(collectionName));
    }

    private void insertDocumentsFromMongoExtendedJsonFile(Path path, String collectionName) {
        try {
            List<Document> documents = new ArrayList<>();
            Files.readAllLines(path).forEach(l -> documents.add(Document.parse(l)));
            mongoTemplate.getCollection(collectionName).insertMany(documents);
            System.out.println(documents.size() + " documents loaded for " + collectionName + " collection.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
