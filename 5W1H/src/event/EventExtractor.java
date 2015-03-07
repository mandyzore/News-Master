package event;

import java.util.ArrayList;
import java.util.Hashtable;

import model.Entity;
import model.Event;
import model.News;
import model.Tops;
import util.ml.classify.Logistic;

/*
 * Project: 5W1H
 * Author: Mengdi Zhang
 * Date: 2014-12-11 下午4:39:33
 * Version: 
 **/

public class EventExtractor {

	Logistic lg;
	Hashtable<String,String> entityLabel=new Hashtable<String,String>();
	public EventExtractor(){
		lg=new Logistic();
		entityLabel.put("person", "nr");
		entityLabel.put("location", "ns");
		entityLabel.put("organization", "nt");
	}
	public void processEvent(ArrayList<News> newsList, int topN) {
		System.out.println("start...");
		long start = System.currentTimeMillis();
		// ----------------------step0.train classifier----------------------
		
		//----------------------step1.gradually extract----------------------
		int count = 1;
		for (News news : newsList) {
			System.out.println("-------------------------" + (count++)+ "----------------------------");
			this.annotateEvent(news,entityLabel);// calculate entities feature,classify, rank 
			this.extractWho(news,topN); 
			this.extractWhere(news,topN);
		}
	}

	public void annotateEvent(News news,Hashtable<String,String>entityLabel) {// entities
		//calculate entities feature,classify, rank
		//-------------Step 0. recognize named ENTITIES  -------------
		EventProcessor.ner(news,entityLabel);
		for (Entity e : news.event.entities) {
			System.out.println(e.text + "\t" + e.label);
		}
		//-------------Step 1. recognize topic SENTENCE  -------------
		//-------------Step 2. annotate entities FEATURE  -------------
		EventProcessor.calculateFeature(news); //average and normalize
	}

	public Tops extractWho(News news, int topN) {// extract person,location,org with features
		news.event.Whos=new Tops(topN);
		//load weight
		try {
			lg.loadWeight("./Data/weights/whoW");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//classify
		int count=0;
		for(Entity e:news.event.entities){
			if(e.label.equals("person")||e.label.equals("location")||e.label.equals("organization")||e.label.contains("title_")){
				news.event.Whos.addTarget(e,this.lg.classify(e.feature));
				count++;
			}
		}
		return news.event.Whos;
	}

	public Tops extractWhere(News news, int topN) { //extract location with features
		news.event.Wheres=new Tops(topN);
		//load weight
		try {
			lg.loadWeight("./Data/weights/whereW");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//classify
		int count=0;
		for(Entity e:news.event.entities){
			if(count<topN){
				if(e.label.equals("nr")||e.label.equals("ns")||e.label.equals("nt")){
					news.event.Wheres.addTarget(e,this.lg.classify(e.feature));
					count++;
				}
			}
		}
		return news.event.Wheres;
	}
}
