package org.sdblt.utils.cache;

public enum EhCacheEmun {
	SYS_CACHE("sysCache"),
	USER_CACHE("userCache") ,
	DOCK_CACHE("dockCache") ,
	ORDER_CACHE("orderCache"),
	TEST_CACHE("testCache"), 
	COMM_CACHE("commCache");

	private String value;

	private EhCacheEmun(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}