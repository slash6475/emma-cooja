package emma.model;


import java.util.ArrayList;

public class Resource {
	String name = new String();
	String desc = new String();
	int	   value = -1;

	ArrayList<Resource> resources = new ArrayList<Resource>();

	public Resource(String name){
		this.name = name;
	}

	public Resource(String name, String desc){
		this.name = name;
		this.desc = desc;
	}

	public String getName(){
		return this.name;
	}

	public void setDescription(String desc){
		this.desc = desc;
	}

	public String getDescription(){
		return this.desc;
	}

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
		return this.value;
	}

	public void addResource(Resource r){
		this.resources.add(r);
	}

	public ArrayList<Resource> getResources(){
		return this.resources;
	}
}