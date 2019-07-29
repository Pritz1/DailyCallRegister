package com.eis.dailycallregister.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TestResponse{

	@SerializedName("testList")
	private List<TestListItem> testList;

	public void setTestList(List<TestListItem> testList){
		this.testList = testList;
	}

	public List<TestListItem> getTestList(){
		return testList;
	}

	@Override
 	public String toString(){
		return 
			"TestResponse{" + 
			"testList = '" + testList + '\'' + 
			"}";
		}
}