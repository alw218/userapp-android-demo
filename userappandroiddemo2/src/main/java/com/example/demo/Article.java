package com.example.demo;

import com.google.gson.annotations.SerializedName;

public class Article {
	
	@SerializedName("id")
	public Integer id;
	
	@SerializedName("title")
	public String title;
	
	@SerializedName("body")
	public String body;
	
}
