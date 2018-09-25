package com.indizen.exercise2;


import com.indizen.Helpers;
import com.indizen.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

public class InsertTestEx2Solved {

    private static Logger logger = Logger.getLogger(InsertTestEx2Solved.class.getName());

    public static void main(String[] args) {

        // Init connection
        MongoConnection conn = MongoConnection.getInstance();
        conn.init();

        MongoClient client = conn.getMongo();
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> coll = database.getCollection("insertTest");
        coll.drop();

        /* TODO Task1: create a document similar to the next json schema
         *   {
         *      "str" :  "MongoDB, Hello",
         *       "int" : 42,
         *       "long" :  1L,
         *       "double" :  1.1,
         *       "boolean" : false,
         *       "date" :  ISODate(xx:xx:xx hh:mm:ss),
         *       "objectId" :  ObjectID("xxxxxxxxxxx),
         *       "null" : null,
         *       "embeddedDoc": {
         *           x: 0
         *       },
         *       "list" : [ 1, 2, 3 ]
         *   }
         **/
        Document document = new Document()
                .append("str", "MongoDB, Hello")
                .append("int", 42)
                .append("l", 1L)
                .append("double", 1.1)
                .append("b", false)
                .append("date", new Date())
                .append("objectId", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("x", 0))
                .append("list", Arrays.asList(1, 2, 3));

        // Print document before inserting
        Helpers.printJson(document);

        // TODO Task2: insert above document in the "insertTest" collection
        coll.insertOne(document);

        // Print document after inserting
        Helpers.printJson(document);

        // TODO Task3: create two documents with this schema { name: <string>, profession: <string>, age: <number>} and inset them in one operation
        Document doc1 = new Document("name", "john").append("profession", "programmer").append("age", 33);
        Document doc2 = new Document("name", "jane").append("profession", "manager").append("age", 45);
        coll.insertMany(Arrays.asList(doc1, doc2));

        Helpers.printJson(doc1);
        Helpers.printJson(doc2);

        System.out.println("Documents after insertions: " + coll.countDocuments());
        // Close connection
        conn.close();
    }
}

