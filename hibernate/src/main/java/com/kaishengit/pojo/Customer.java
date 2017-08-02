package com.kaishengit.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    //演示了如何使用Hibernate中的主键策略UUID
    @Id
    @GenericGenerator(name = "myUuid",strategy = "uuid")
    @GeneratedValue(generator = "myUuid")
    private String id;
    private String name;
    @Version
    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
