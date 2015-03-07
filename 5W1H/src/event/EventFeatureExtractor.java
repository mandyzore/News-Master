package event;

import model.Entity;
import model.News;

/*
 * Project: 5W1H
 * Author: Mengdi Zhang
 * Date: 2014-12-17 下午4:15:04
 * Version: 
 **/

public class EventFeatureExtractor { 
	/**
	 * Function: Person,Location,Orgnazition
	 *
	 */
	
	// dims, vectorize, normalization
	public static double[] getFeatureVector(News news,Entity entity){
		double[] featureVector=null;
		featureVector=new double[6];
		
		//1.The number of occurrences in title
		featureVector[0]=(double)entity.position_in_title.size()/news.wordCountsOfTitle;
		//2.The number of occurrences in the content
		featureVector[1]=(double)entity.position_in_content.size()/news.wordCountsOfContent;
		//3.Distribution
		for(int i:entity.position_in_content){
			entity.average_position+=i;
		}
		entity.average_position=entity.average_position/(entity.position_in_content.size()+1);
		featureVector[2]=(double)entity.average_position/news.wordCountsOfContent;
		//4.Type of the entity. Binary feature.
		featureVector[3]=(entity.label.equals("nr"))?(1.0):(0.0);//person
		featureVector[4]=(entity.label.equals("ns"))?(1.0):(0.0);//location
		featureVector[5]=(entity.label.equals("nt"))?(1.0):(0.0);//organization
		
		return featureVector;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("start...");
		long start = System.currentTimeMillis();
		System.out.println("end...");
		System.out.println("执行耗时 : " + (System.currentTimeMillis() - start)
				/ 1000f + " 秒 ");

	}
}


