package com.indizen.exercise4;

import com.indizen.Helpers;
import com.indizen.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Logger;

public class UpdateDeleteTestEx4 {

    private static Logger logger = Logger.getLogger(UpdateDeleteTestEx4.class.getName());

    public static void main(String[] args) {

        // Init connection
        MongoConnection conn = MongoConnection.getInstance();
        conn.init();
        MongoClient client = conn.getMongo();

        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> coll = database.getCollection("updateTest");
        coll.drop();
        // prepare collection with test data
        Helpers.augmentCollection(coll, 5, true);

        // TODO Task1: find one document with y = 0. Update value "y" = 20 and add a new field "updated" = true
        //Tip: use static methods of Updates.class
        System.out.println("------------------------- Task1 -------------------------");

        // Write code here....
        Bson criteria = null;
        Bson update = null;
        UpdateResult result = null;

        System.out.println("Documents updated: " + result.getModifiedCount());
        coll.find().into(new ArrayList<>()).forEach(Helpers::printJson);


        // TODO Task2: find one document with y = 10 (Not exist, so do an upsert instead an update). Upsert value "y" = 10 and add a new field "upserted" = true
        //Tip: use static methods of Updates.class
        System.out.println("------------------------- Task2 -------------------------");

        // Write code here....
        Bson criteria2 = null;
        Bson update2 = null;
        UpdateOptions options = null;
        UpdateResult result2 = null;

        coll.find().into(new ArrayList<>()).forEach(Helpers::printJson);


        // TODO Task3: find documents with y > 0. Update all this documents, increasing in 1 the "y" value and add a new field "increased" = true
        //Tip: use static methods of Updates.class
        System.out.println("------------------------- Task3 -------------------------");

        // Write code here....
        Bson criteria3 = null;
        Bson update3 = null;
        UpdateResult result3 = null;

        coll.find().into(new ArrayList<>()).forEach(Helpers::printJson);

        // TODO Task4: delete all documents with updated=true or upserted=true.
        System.out.println("------------------------- Task4 -------------------------");

        // Write code here....
        Bson criteria4 = null;
        DeleteResult result4 = null;

        coll.find().into(new ArrayList<>()).forEach(Helpers::printJson);

        // Close connection
        conn.close();
    }
}
