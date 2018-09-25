package com.indizen.exercise2;


import com.indizen.Helpers;
import com.indizen.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Logger;

public class InsertTestEx2 {

    private static Logger logger = Logger.getLogger(InsertTestEx2.class.getName());

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

        Document document = null;


        // Print document before inserting
        Helpers.printJson(document);

        // TODO Task2: insert above document in the "insertTest" collection
        // write code here ....


        // Print document after inserting
        Helpers.printJson(document);

        // TODO Task3: create two documents with this schema { name: <string>, profession: <string>, age: <number>} and inset them in one operation
        // write code here .....
        Document doc1 = null;
        Document doc2 = null;


        Helpers.printJson(doc1);
        Helpers.printJson(doc2);

        logger.info("Documents after insertions: " + coll.countDocuments());
        // Close connection
        conn.close();
    }
}

