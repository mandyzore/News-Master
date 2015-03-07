package model;

import java.util.ArrayList;

/*
 * Project: 5W1H
 * Author: Mengdi Zhang
 * Date: 2014-12-17 下午3:59:03
 * Version: 
 **/

public class Entity {
	/**
	 * Function:
	 */
	public String text=""; //
	
	//1.The number of occurrences in content
	public ArrayList<Integer> position_in_content;
	//2.The number of occurrences in the title
	public ArrayList<Integer> position_in_title;
	//3.Distribution.
	public ArrayList<Integer> position_in_paragraph; //0-headline, 1..n - n_st paragraph in content.
	public ArrayList<Integer> position_in_sentence;// position in the whole sentence
	public int average_position; //  The average position of the entity relative to the length of the text
	//4.Type of the entity. NOTE: For the classification of the WHERE candidates we reduce the features space by the type of the entity because we know that the type has to be a location entity.
	public String label; //{nr,ns,nt} <== {people,locations , organizations}
	
	public double[] feature;
	
	public Entity(){
		this.position_in_title=new ArrayList<Integer>();
		this.position_in_content=new ArrayList<Integer>();
		this.position_in_paragraph=new ArrayList<Integer>();
		this.position_in_sentence=new ArrayList<Integer>();
	}
	
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		
		if (arg0 instanceof Entity) {   
			Entity e = (Entity) arg0;   
            return this.text.equals(e.text);
        }   
		return super.equals(arg0);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("start...");
		long start = System.currentTimeMillis();
		System.out.println("end...");
		System.out.println("执行耗时 : " + (System.currentTimeMillis() - start)
				/ 1000f + " 秒 ");

	}
}


