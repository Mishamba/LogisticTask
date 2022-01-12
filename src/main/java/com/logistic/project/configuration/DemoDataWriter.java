package com.logistic.project.configuration;

import com.mongodb.MongoWriteException;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@AllArgsConstructor
public class DemoDataWriter implements ApplicationRunner {
    private MongoTemplate mongoTemplate;

    // TODO fix exception in docker container
    @Override
    public void run(ApplicationArguments args) throws IOException {
        File folder = new ClassPathResource("mongo").getFile();
        File[] files = folder.listFiles(File::isFile);
        assert files != null;
        for (File file : files) {
            insertDocumentsFromMongoExtendedJsonFile(file.toPath());
        }
    }

    private void insertDocumentsFromMongoExtendedJsonFile(Path path) {
        try {
            String collectionName = path.getFileName().toString().replace(".json", "");
            Files.readAllLines(path).forEach(l -> mongoTemplate.getCollection(collectionName).insertOne(Document.parse(l)));
        } catch (IOException | MongoWriteException e) {
            e.printStackTrace();
        }
    }
}
