package com.indizen.exercise4;

import com.indizen.Helpers;
import com.indizen.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Logger;

public class UpdateDeleteTestEx4Solved {

    private static Logger logger = Logger.getLogger(UpdateDeleteTestEx4Solved.class.getName());

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

        Bson criteria = Filters.eq("y", 0);
        //Bson update = new Document("$set", new Document("y", 20).append("updated", true));
        Bson update = Updates.combine(Updates.set("y", 20), Updates.set("updated", true));
        UpdateResult result = coll.updateOne(criteria, update);

        System.out.println("Documents updated: " + result.getModifiedCount());
        coll.find().into(new ArrayList<>()).forEach(Helpers::printJson);


        // TODO Task2: find one document with y = 10 (Not exist). Upsert value "y" = 10 and add a new field "upserted" = true
        //Tip: use static methods of Updates.class
        System.out.println("------------------------- Task2 -------------------------");

        Bson criteria2 = Filters.eq("y", 10);
        Bson update2 = Updates.combine(Updates.set("y", 10), Updates.set("upserted", true));
        UpdateOptions options = new UpdateOptions().upsert(true);
        UpdateResult result2 = coll.updateOne(criteria2, update2, options);

        coll.find().into(new ArrayList<>()).forEach(Helpers::printJson);


        // TODO Task3: find documents with y > 0. Update in all documents increasing in 1 the "y" value and add a new field "increased" = true
        //Tip: use static methods of Updates.class
        System.out.println("------------------------- Task3 -------------------------");

        Bson criteria3 = Filters.gt("y", 0);
        Bson update3 = Updates.combine(Updates.inc("y", 1), Updates.set("increased", true));
        UpdateResult result3 = coll.updateMany(criteria3, update3);

        coll.find().into(new ArrayList<>()).forEach(Helpers::printJson);

        // TODO Task4: delete all documents with updated=true or upserted=true.
        System.out.println("------------------------- Task4 -------------------------");

        Bson criteria4 = Filters.or(Filters.eq("updated", true),Filters.eq("upserted", true));
        DeleteResult result4 = coll.deleteMany(criteria4);

        coll.find().into(new ArrayList<>()).forEach(Helpers::printJson);

        // Close connection
        conn.close();
    }
}
