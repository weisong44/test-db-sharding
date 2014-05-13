package com.weisong.test.routing.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Foo extends ShardedObject {

    @Column
    private String name;

    public Foo() {
    }

    public Foo(Integer shardId, String name) {
    	super(shardId);
        this.name = name;
    }
}
