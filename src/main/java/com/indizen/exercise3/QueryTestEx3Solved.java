package com.indizen.exercise3;


import com.indizen.Helpers;
import com.indizen.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.UUID.randomUUID;

public class QueryTestEx3Solved {

    private static Logger logger = Logger.getLogger(QueryTestEx3Solved.class.getName());

    public static void main(String[] args) {

        // Init connection
        MongoConnection conn = MongoConnection.getInstance();
        conn.init();

        MongoClient client = conn.getMongo();
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> coll = database.getCollection("queryTest");
        coll.drop();

        // prepare collection with test data
        Helpers.augmentCollection(coll, 50, false);


        // TODO Task1: find first element in queryTest collection
        System.out.println("------------------------- Task1 -------------------------");

        Document doc = coll.find().first();
        Helpers.printJson(doc);


        // TODO Task2: find all elements, limit to ten results and save it into an ArrayList<Document>
        System.out.println("------------------------- Task2 -------------------------");

        List<Document> results = coll.find().limit(10).into(new ArrayList<>());
        results.forEach(Helpers::printJson);


        // TODO Task3: find all with iteration
        System.out.println("------------------------- Task3 -------------------------");
        List<Document> docs = new ArrayList<>();

        try (MongoCursor<Document> cursor = coll.find().iterator()) {
            while (cursor.hasNext()) {
                Document current = cursor.next();
                docs.add(current);
            }
        }

        docs.forEach(Helpers::printJson);



        Helpers.augmentCollection(coll, 25, true);


        // TODO Task4: find with filter criteria. Find all documents with y = 0, z > 10 and z < 90
        System.out.println("------------------------- Task4 -------------------------");

        //Bson criteria = new Document("y", 0).append("z", new Document("$gt", 10).append("$lt", 90));
        Bson filter = Filters.and(
                Filters.eq("y", 0),
                Filters.gt("z", 10),
                Filters.lt("z", 90));

        coll.find(filter)
                .into(new ArrayList<>())
                .forEach(Helpers::printJson);


        // TODO Task5: apply a projection to the last query. Show "z" field and exclude "_id" field
        System.out.println("------------------------- Task5 -------------------------");

        Bson projection = Projections.fields(Projections.excludeId(), Projections.include("z"));

        coll.find(filter)
                .projection(projection)
                .into(new ArrayList<>())
                .forEach(Helpers::printJson);


        // Close connection
        conn.close();
    }

}
