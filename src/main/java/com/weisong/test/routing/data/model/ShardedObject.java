package com.weisong.test.routing.data.model;

import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
@Getter
abstract public class ShardedObject extends BaseObject {
	
	protected Integer shardId;
	
	public ShardedObject() {}
	public ShardedObject(Integer shardId) {
		this.shardId = shardId;
	}
}
