package xyz.homapay.hampay.balancer.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by Ebrahim Pasbani.
 */

public abstract class BaseEntity {

    @Id
    @Property("id")
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
