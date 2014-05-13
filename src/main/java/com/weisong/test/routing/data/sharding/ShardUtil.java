package com.weisong.test.routing.data.sharding;

import org.postgresql.Driver;

public class ShardUtil {
	static public ShardAwareDataSource createDataSource(
			String host, String database, String schema, String username, String password) {
		ShardAwareDataSource ds = new ShardAwareDataSource();
    	ds.setDriverClassName(Driver.class.getName());
    	ds.setUrl(String.format("jdbc:postgresql://%s/%s", host, database));
    	ds.setSchema(schema);
    	ds.setUsername(username);
    	ds.setPassword(password);
    	return ds;
	}

	static public ShardInfo createShardInfo(Long id, String host, String database, String schema) {
		return new ShardInfo(id, host, database, schema);
	}
}
