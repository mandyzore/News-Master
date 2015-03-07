package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;


public class Event implements Serializable{
//	public int id;
//	public  String headline; //标注后的?
//	public  String text; //标注后的?
//	public  WH wh;
//	public final static String[] WH={"Who","What","Where","When","Why","How"};
//	public Document doc;

	String eventType=null; //class label
	public  ArrayList<Entity> entities;
	public Tops Whos; //people, organizations and locations
	public Tops Wheres;//locations
	public Tops Whats;//sentence
	public Tops Whens;//time
	public Tops Whys;//sentence
	public Tops Hows;//relation
	
	
	
	public Event(){
//		entities
		entities=new ArrayList<Entity>();
	}
	
}
