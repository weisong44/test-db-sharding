package com.weisong.test.common;


public class ContextHolder {

	private static final ThreadLocal<Context> holder = new ThreadLocal<Context>() {
	    protected Context initialValue() {
	        return new Context();
	    }
	};
    
	public static Context get() {
		return (Context) holder.get();
	}

}
