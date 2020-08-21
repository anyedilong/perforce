package org.sdblt.modules.activation.dto;


public class ActDto implements java.io.Serializable {
	
	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String proNum;//设备编号
	private String actCode;//激活码
	
	public String getProNum() {
		return proNum;
	}
	public void setProNum(String proNum) {
		this.proNum = proNum;
	}
	public String getActCode() {
		return actCode;
	}
	public void setActCode(String actCode) {
		this.actCode = actCode;
	}
	
	

}
