package us.curb.maven_.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics extends Question{

	private List<Question> quList;
	
	private Map<String,String> data;
	
	public Statistics(List<Question> list){
		this.quList = list;
		this.data = new HashMap<String,String>();
	}
	
	public void addData(String num,String statis){
		data.put(num, statis);
	}

	public List<Question> getQuList() {
		return quList;
	}

	public void setQuList(List<Question> quList) {
		this.quList = quList;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Statistics [quList=" + quList + ", data=" + data + "]";
	}
	
	
	
	
}
