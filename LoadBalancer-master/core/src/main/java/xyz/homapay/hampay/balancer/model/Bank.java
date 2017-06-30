package xyz.homapay.hampay.balancer.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * @author Siavash Mahmoudpour
 */
@Entity(value = "banks", noClassnameStored = true)
public class Bank extends BaseEntity {

    private String code;
    private String name;
    private long achMax;
    @Property("acctFormat")
    private String accountFormat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountFormat() {
        return accountFormat;
    }

    public void setAccountFormat(String accountFormat) {
        this.accountFormat = accountFormat;
    }

    public long getAchMax() {
        return achMax;
    }
}
