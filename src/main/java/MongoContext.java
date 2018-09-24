import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.List;

import static java.util.Arrays.asList;

public class MongoContext {

    private static final String DB = "test";
    private static final String COLL = "test";

    private static MongoContext context = new MongoContext();

    private MongoClient client;
    private static MongoDatabase db;
    private static MongoCollection coll;


    private MongoContext(){
        try{
           // init();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

//    private void init() throws UnknownHostException {
//        if (client == null) {
//            this.client =
//        }
//    }

    public static void main(String[] args) {

        MongoClientOptions ops = MongoClientOptions.builder().connectionsPerHost(500).build();
        ServerAddress server = new ServerAddress("localhost",27017);
        List<ServerAddress> servers = asList(new ServerAddress("node1",27017), new ServerAddress("node2",27017));
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");

        MongoClient client = new MongoClient(server, ops);

        MongoDatabase db = client.getDatabase(DB);
        MongoDatabase db2 = db.withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> coll = db.getCollection(COLL);
        MongoCollection<BsonDocument> coll2 = db.getCollection(COLL, BsonDocument.class);
    }


    public static MongoContext getInstance(){
        return MongoContext.context;
    }

}
