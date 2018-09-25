package com.indizen.exercise1;

import com.indizen.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Logger;

public class ConnectionTestEx1 {

    private static Logger logger = Logger.getLogger(ConnectionTestEx1.class.getName());

    public static void main(String[] args) {

        MongoConnection conn = MongoConnection.getInstance();

        // TODO Task1: Define a valid MongoDB connection URI
        String customURI = null;

        conn.init(customURI);

        // Get singleton instance of MongoClient
        MongoClient client = conn.getMongo();

        // List all available databases
        ListDatabasesIterable dbs = client.listDatabases().nameOnly(Boolean.TRUE);
        for (Object db : dbs) {
            logger.info("Available DB: " + db.toString());
        }

        // TODO Task2: Get admin database
        MongoDatabase database = null;

        logger.info("MongoDB active Database is: " + database.getName());

        // TODO Task3: Create a new collection in "test" db
        // write code here....


        // TODO Task4: get the collection created above and log its name
        MongoCollection<Document> coll = null;
        String dbName = null;

        logger.info("MongoDB active Collection is: " + dbName);

        coll.drop();
        //Close connection
        conn.close();
    }
}
