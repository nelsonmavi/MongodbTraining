package com.indizen.exercise5;


import com.indizen.Helpers;
import com.indizen.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


/**
 * An example of aggregation using the zipcode data from <a href = https://docs.mongodb.com/manual/tutorial/aggregation-zip-code-data-set/></a>
 */
public class AggregateTestEx5Solved {

    private static Logger logger = Logger.getLogger(AggregateTestEx5Solved.class.getName());

    public static void main(String[] args) {

        // Init connection
        MongoConnection conn = MongoConnection.getInstance();
        conn.init();

        MongoClient client = conn.getMongo();
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> coll = database.getCollection("zips");

        System.out.println("------------------------- Sample data -------------------------");
        coll.find().limit(2).into(new ArrayList<>()).forEach(Helpers::printJson);

        // TODO Task1: Aggregate ZIPCode documents by "state" field, calculate the "totalPop" field as sum of "pop" for each state. Finally filter results which "totalPop" >= 10000000
        // Tip: use static methods of Aggregates.class and Accumulators.class
        System.out.println("------------------------- Task1 -------------------------");

        // List<Bson> pipeline = null;
        List<Bson> pipeline = Arrays.asList(
                Aggregates.group("$state", Accumulators.sum("totalPop", "$pop")),
                Aggregates.match(Filters.gte("totalPop", 10000000))
        );

        coll.aggregate(pipeline)
                .into(new ArrayList<>())
                .forEach(Helpers::printJson);


        // Close connection
        conn.close();
    }
}
