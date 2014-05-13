package com.weisong.test.routing.data.sharding;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;

import com.weisong.test.common.ContextHolder;

public class SimpleRoutingDataSource extends AbstractDataSource implements InitializingBean {

	private Map<Long, ShardAwareDataSource> targetDataSources = new ConcurrentHashMap<>();
	private ShardAwareDataSource defaultTargetDataSource;

	public void setDefaultTargetDataSource(ShardAwareDataSource ds) {
		this.defaultTargetDataSource = ds;
	}

	private void switchToShard(Connection conn, String schema) {
		try {
			conn.createStatement().execute( "SET search_path TO " + schema);
		}
		catch ( SQLException e ) {
			throw new HibernateException(
					"Could not alter JDBC connection to specified schema [" + schema + "]", e);
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		ShardAwareDataSource ds = (ShardAwareDataSource) determineTargetDataSource();
		Connection conn = ds.getConnection();
		switchToShard(conn, ds.getSchema());
		return conn;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		ShardAwareDataSource ds = (ShardAwareDataSource) determineTargetDataSource();
		Connection conn = ds.getConnection();
		switchToShard(conn, ds.getSchema());
		return conn;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return (T) this;
		}
		return determineTargetDataSource().unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
	}

	protected ShardAwareDataSource determineTargetDataSource() {
		Long shardId = ContextHolder.get().getShardId();
		ShardAwareDataSource ds = shardId == null ? null : targetDataSources.get(shardId);
		if (ds == null) {
			ds = defaultTargetDataSource;
		}
		if (ds == null) {
			throw new IllegalStateException(
					"Cannot determine target DataSource for shard [" + shardId + "]");
		}
		return ds;
	}

	public void addTargetDataSource(Long shardId, ShardAwareDataSource ds) {
		targetDataSources.put(shardId, ds);
	}

	@Override
	public void afterPropertiesSet() {
	}
}
