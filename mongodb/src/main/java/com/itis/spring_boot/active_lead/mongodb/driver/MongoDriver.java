package com.itis.spring_boot.active_lead.mongodb.driver;

import com.mongodb.client.*;
import com.mongodb.client.model.UpdateOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

@Data
public class MongoDriver {
    private MongoDatabase database;

    public static void main(String[] args) {
        MongoDriver mongoDriver = new MongoDriver("jobboard");
        MongoCollection<Document> collection = mongoDriver.getDatabase().getCollection("country");

        collection.insertOne(new Document().append("name", "Russia"));
        collection.find().forEach(document -> System.out.println(document.getString("name")));
        System.out.println("=======");

        collection.deleteOne(new Document().append("name", "Russia"));
        collection.find().forEach(document -> System.out.println(document.getString("name")));
        System.out.println("=======");

        collection.replaceOne(new Document().append("name","USA"), new Document().append("name", "USA").append("status","cool"));
        collection.find().forEach(document -> System.out.println(document.getString("")));
        System.out.println("=======");

        // db.courses.find({active: true, $or: [{keywords: 'java core'}, {studentsCount: {$lt: 35}}]})
//
       /* Document searchQuery = new Document();
//
        searchQuery
                .append("active", true)
                .append("$or", Arrays.asList(
                        new Document("keywords", "java core"),
                        new Document("studentsCount", new Document("$lt", 53))));*/

//        FindIterable<Document> resultDocuments = collection.find(searchQuery);

//
//        FindIterable<Document> resultDocuments = collection.find(searchQuery)
//                .projection(new Document("hours", 1)
//                        .append("active", 1)
//                        .append("studentsCount", 1)
//                        .append("keywords", 1)
//                        .append("_id", 0));

//        FindIterable<Document> resultDocuments = collection.find(
//                and(new Document("active", true),
//                    or(new Document("keywords", "java core"),
//                            lt("studentsCount", 35))));

     /*   FindIterable<Document> resultDocuments = collection.find(searchQuery)
                .projection(fields(include("studentsCount", "hours", "active"), excludeId()));

        for (Document document : resultDocuments) {
            System.out.println(document.toJson());

        }*/
    }

    MongoDriver(String mongoDatabase){
        MongoClient client = MongoClients.create();
        this.database = client.getDatabase(mongoDatabase);
    }
}
