package com.weisong.test.routing.data.sharding;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShardRegistry {
	
	private Map<Long, ShardInfo> shardMap = new ConcurrentHashMap<>();
	
	@Autowired SimpleRoutingDataSource routingDataSource;
	
	public ShardInfo getShard(Integer shardId) {
		return shardMap.get(shardId);
	}
	
	public void addShard(Long id, String host, String database, String schema, String username, String password) {
    	ShardInfo shard = ShardUtil.createShardInfo(id, host, database, schema);
    	ShardAwareDataSource ds = ShardUtil.createDataSource(host, database, schema, username, password);
		shardMap.put(id, shard);
		routingDataSource.addTargetDataSource(shard.getId(), ds);
	}
	
	public Collection<ShardInfo> getAllShards() {
		return Collections.unmodifiableCollection(shardMap.values());
	}
}
