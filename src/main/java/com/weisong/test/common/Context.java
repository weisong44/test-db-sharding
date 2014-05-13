package com.weisong.test.common;

import lombok.Getter;
import lombok.Setter;

import org.springframework.util.Assert;


public class Context {
	@Getter @Setter private Long shardId; 
	private int txDepth;
	
	public boolean isInTx() {
		return txDepth > 0;
	}
	
	public void beginTx() {
		++txDepth;
	}

	public void endTx() {
		--txDepth;
		Assert.isTrue(txDepth >= 0, "txDepth is negative");
	}
}
