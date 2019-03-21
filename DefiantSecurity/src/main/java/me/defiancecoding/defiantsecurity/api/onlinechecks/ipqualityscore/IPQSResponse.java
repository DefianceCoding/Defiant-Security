package me.defiancecoding.defiantsecurity.api.onlinechecks.ipqualityscore;

public class IPQSResponse {

	public boolean proxy;
	public String host;
	public String ISP;
	public String organzization;
	public String ASN;
	public String country_code;
	public String city;
	public String region;
	public String timezone;
	public String latitude;
	public String longitude;
	public boolean is_crawler;
	public boolean vpn;
	public boolean tor;
	public boolean mobile;
	public float fraud_score;
	public String request_id;
	public String operating_system;
	public String browser;
	public String device_brand;
	public String device_model;
	public String message;
	public Boolean success;
	public String[] errors;

}
