package com.weisong.test.routing.data.sharding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShardInfo {
	final private Long id;
	final private String host;
	final private String database;
	final private String schema;

	public String getRoutingKey() {
		return String.format("%s:%s:%s", host, database, schema);
	}

	@Override
	public String toString() {
		return String.format("%d -> %s", id, getRoutingKey());
	}
}
