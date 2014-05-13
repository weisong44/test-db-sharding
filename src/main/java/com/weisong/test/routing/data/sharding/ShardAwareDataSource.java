package com.weisong.test.routing.data.sharding;

import lombok.Getter;
import lombok.Setter;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Getter @Setter 
public class ShardAwareDataSource extends DriverManagerDataSource {
	private String schema;
}
