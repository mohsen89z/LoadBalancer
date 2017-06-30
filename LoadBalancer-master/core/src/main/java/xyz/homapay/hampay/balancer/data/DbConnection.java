package xyz.homapay.hampay.balancer.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Ebrahim Pasbani.
 */
public class DbConnection {

    private static Properties dbProperties = null;
    private static MongoClient mongoClient = null, bulkMongoClient = null;
    private static Morphia morphia = null;

    private static Properties props() throws IOException {
        if (dbProperties == null) {
            dbProperties = new Properties();
            dbProperties.load(DbConnection.class.getResourceAsStream("/persistence.properties"));
        }
        return dbProperties;
    }

    private static MongoClient mongoClient() {
        if (mongoClient == null) {
            try {
                MongoCredential credential = MongoCredential.createCredential(props().getProperty("mongodb.username"),
                        props().getProperty("mongodb.database"), props().getProperty("mongodb.password").toCharArray());

                mongoClient = new MongoClient(new ServerAddress(props().getProperty("mongodb.host"),
                        Integer.valueOf(props().getProperty("mongodb.port"))), Arrays.asList(credential));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mongoClient;
    }

    public static Morphia morphia() {
        if (morphia == null) {
            morphia = new Morphia().mapPackage("xyz.homapay.hampay.balancer.model");
        }
        return morphia;
    }

    public static Datastore datastore() throws IOException {
        return morphia().createDatastore(mongoClient(), props().getProperty("mongodb.database"));
    }

    public static Datastore bulkDatastore() throws IOException {
        return morphia().createDatastore(bulkMongoClient(), props().getProperty("bulk.db.database"));
    }

    private static MongoClient bulkMongoClient() {
        if (bulkMongoClient == null) {
            try {
                MongoCredential credential = MongoCredential.createCredential(props().getProperty("bulk.db.username"),
                        props().getProperty("bulk.db.database"), props().getProperty("bulk.db.password").toCharArray());
                bulkMongoClient = new MongoClient(new ServerAddress(props().getProperty("bulk.db.host"),
                        Integer.valueOf(props().getProperty("bulk.db.port"))), Arrays.asList(credential));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bulkMongoClient;
    }

}
