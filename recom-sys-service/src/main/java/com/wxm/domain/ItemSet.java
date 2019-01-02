package com.wxm.domain;
import java.util.Date;
import lombok.Data;

@Data
public class ItemSet {


	private int id;
	private String name;
	private Date ctime;
	private Date utime;
	private String accessToken;
	private int status;
	private int itemNumbers;
	private int terminalType;
	private String mainUrl;

}