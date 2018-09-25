package com.indizen.exercise3;


import com.indizen.Helpers;
import com.indizen.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QueryTestEx3 {

    private static Logger logger = Logger.getLogger(QueryTestEx3.class.getName());

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
        Document doc = null;  // Write code here ...

        Helpers.printJson(doc);


        // TODO Task2: find all elements, limit to ten results and save it into an ArrayList<Document>
        System.out.println("------------------------- Task2 -------------------------");
        List<Document> results = null; // Write code here ...

        results.forEach(Helpers::printJson);


        // TODO Task3: find all with iteration
        System.out.println("------------------------- Task3 -------------------------");
        List<Document> docs = null; // Write code here ...

        docs.forEach(Helpers::printJson);


        Helpers.augmentCollection(coll, 25, true);
        // TODO Task4: find with filter criteria. Find all documents with y = 0, z > 10 and z < 90
        System.out.println("------------------------- Task4 -------------------------");
        // Tip: use static methods of Filters.class

        Bson filter = null;     // Write code here ...

        coll.find(filter)
                .into(new ArrayList<>())
                .forEach(Helpers::printJson);


        // TODO Task5: apply a projection to the last query. Show "z" field and exclude "_id" field
        System.out.println("------------------------- Task5 -------------------------");
        // Tip: use static methods of Projections.class

        Bson projection = null;  // Write code here ...

        coll.find(filter)
                .projection(projection)
                .into(new ArrayList<>())
                .forEach(Helpers::printJson);


        // Close connection
        conn.close();
    }

}
