package org.sdblt.utils;

public class Constants {
	public static final String COLUMN_19PAY = "version,reqTime,mxId,mxLoginName,mxOrderId,amount,cardHolder,bankCardId,accountType,cardType,bankCode,subBankName,provinceId,cityId,alliedBankCode,payType,tradeDesc,notifyUrl,currency,payerId,tradeType,mxReserved,commonExtend,persistHandling,persistTimeOut,securityInfo,extend1,extend2,extend3";
	public static final String COLUMN_19TELPAY = "prodid,agentid,backurl,returntype,orderid,mobilenum,source,mark,merchantKey";
	public static final String COLUMN_19TELPAYQRY = "agentid,source,merchantKey";
	public static final String COLUMN_19TELPAY_QRYRESULT = "agentid,backurl,returntype,orderid,source,merchantKey";
	public static final String COLUMN_19TELPAY_MOBILENUM = "agentid,source,mobilenum,merchantKey";
	public static final String COLUMN_19PAY_ORDER_FAIL = "mxId,mxOrderId,retCode,status";
	public static final String COLUMN_19PAY_ORDER_SUCCESS = "mxId,mxOrderId,preOrderTime,retCode,status,sysOrderId";
	public static final String COLUMN_19PAY_CALLBACK = "amount,extend1,extend2,extend3,finishTime,mxId,mxOrderId,orderStatus,retCode,sysOrderId,version";
	public static final String COLUMN_19TEL_ORDER = "prodid,orderid,tranid,resultno,mark,merchantKey";
	public static final String COLUMN_19TEL_TRADE = "orderid,status,ordermoney,merchantKey";
	public static final String TEL_19KEY = "o2h3wtbrhwh4iuclqv12fwv14hlsrj8pv7l7nxspaiii4k1zq36w1hu5vvi1zsuu809t2tjlp6jnug000a8iotllu5ob00b8spqc7ybzsv4m865zge6r08j6oywkwllz";
	public static final String BD_SIGNKEY = "qwert123456";
	public static final String PAY_19KEY = "kyr5jqcyeismrlq5gkaof22vjl6jjyui5446ca1k0uqqt3tr3nwa16lrcusxoombaxkipq4nrag7v5yuh6f2qv91qpjaov7nd04fx9tz45b1tzno2esoj507g2teu9cm";
	public static final String CREDIT_PAY_19KEY = "kyr5jqcyeismrlq5gkaof22vjl6jjyui5446ca1k0uqqt3tr3nwa16lrcusxoombaxkipq4nrag7v5yuh6f2qv91qpjaov7nd04fx9tz45b1tzno2esoj507g2teu9cm";
	public static final String STATUS19_SUCCESS = "0";
	public static final String RETCODE19_SUCCESS = "00000";
	public static final String STATUS19_TELSUCCESS = "0000";
	public static final String TRANS_STATUS_INIT = "A0001";
	public static final String TRANS_STATUS_PAYSUCCESS = "A0002";
	public static final String TRANS_STATUS_PAYFAIL = "A0003";
	public static final String TRANS_STATUS_BANKSUCCESS = "A0004";
	public static final String TRANS_STATUS_BANKFAIL = "A0005";
	public static final String GBK = "gbk";
	public static final String UTF8 = "utf8";
}

/*
 * Location: E:\项目备份\服务器项目\App后台-151230\App后台\classes\ Qualified Name:
 * ky.util.Constants JD-Core Version: 0.5.3
 */