import com.mongodb.*;

import java.util.logging.Logger;

import static java.lang.String.format;

public class MongoConnection {

    private static Logger logger = Logger.getLogger(MongoConnection.class.getName());
    private static MongoConnection instance = new MongoConnection();

    private MongoClient mongo = null;

    private MongoConnection() {
    }

    public MongoClient getMongo() throws RuntimeException {
        if (mongo == null) {
            logger.info("Starting Mongo");
            MongoClientOptions.Builder options = MongoClientOptions.builder()
                    .connectionsPerHost(4)
                    .maxConnectionIdleTime((60 * 1_000))
                    .maxConnectionLifeTime((120 * 1_000));

            MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/test", options);

            logger.info("About to connect to MongoDB @ " + uri.toString());

            try {
                mongo = new MongoClient(uri);
                mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
            } catch (MongoException ex) {
                logger.severe("An error occoured when connecting to MongoDB "+ ex.getMessage());
            } catch (Exception ex) {
                logger.severe("An error occoured when connecting to MongoDB "+  ex.getMessage());
            }

            // To be able to wait for confirmation after writing on the DB
            mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        }

        return mongo;
    }

    public void init() {
        logger.info("Bootstraping");
        getMongo();
    }

    public void close() {
        logger.info("Closing MongoDB connection");
        if (mongo != null) {
            try {
                mongo.close();
                logger.info("Nulling the connection dependency objects");
                mongo = null;
            } catch (Exception e) {
                logger.severe(format("An error occurred when closing the MongoDB connection\n%s", e.getMessage()));
            }
        } else {
            logger.info("mongo object was null, wouldn't close connection");
        }
    }

    public static MongoConnection getInstance() {
        return instance;
    }
}

