package event;

import java.util.ArrayList;
import java.util.Hashtable;

import model.Entity;
import model.Event;
import model.News;

/*
 * Project: 5W1H
 * Author: Mengdi Zhang
 * Date: 2014-12-17 下午3:50:41
 * Version: 
 **/

public class EventProcessor {
	/**
	 * Function:
	 */

	EventFeatureExtractor efe;
	double[] feature;

	public EventProcessor() {
	}

	public static void ner(News news,Hashtable<String, String> entityLabel) {
		Entity e;
		// int allWordCount=0;
		int contentWordCount = 0;
		int paragraphCount = 0;
		int sentenceWordCount = 0;
		int titleWordCount = 0;
		boolean isEntity = false;
		
		String word;
		String pos;
		String[] wp;
		// title
		
		for (String ww : news.getSeggedTitle().split(" ")) {
			titleWordCount++;
			// allWordCount++;
			wp=ww.split("/");
			if (wp[1].equals(entityLabel.get("person"))){// person name
				if (!news.getKeywords().contains(wp[0])) {
					news.getPersons().add(wp[0]);
					e = new Entity();
					e.text = wp[0];
					e.label = "person";//w.POS
					e.position_in_title.add((Integer) (titleWordCount - 1));
					news.getKeywords().add(e.text);
					news.event.entities.add(e);
				}
			}else if(wp[1].equals(entityLabel.get("location"))){// location name
				if (!news.getKeywords().contains((wp[0]))) {
					news.getLocations().add(wp[0]);
					e = new Entity();
					e.text = wp[0];
					e.label = "location";//w.POS
					e.position_in_title.add((Integer) (titleWordCount - 1));
					news.getKeywords().add(e.text);
					news.event.entities.add(e);
				}
			}else if(wp[1].equals(entityLabel.get("organization"))){// org name 
				if (!news.getKeywords().contains(wp[0])) {
					news.getOrganizations().add(wp[1]);
					e = new Entity();
					e.text = wp[0];
					e.label = "organization";//w.POS
					e.position_in_title.add((Integer) (titleWordCount - 1));
					news.getKeywords().add(e.text);
					news.event.entities.add(e);
				}
			}else if(wp[1].equals("nz")||wp[1].equals("nl")||wp[1].equals("nsf")||wp[1].equals("n")){ // 其它专名
				if (!news.getKeywords().contains(wp[0])) {
//					news.get.add(wp[0]);
					e = new Entity();
					e.text = wp[0];
					e.label = "title_"+wp[1];
					e.position_in_title.add((Integer) (titleWordCount - 1));
					news.getKeywords().add(e.text);
					news.event.entities.add(e);
				}
			}
		}
				
		// content
		for (String p : news.getSeggedPListOfContent()) {
			paragraphCount++;
			sentenceWordCount = 0;
			
			for (String sen : p.split("\n")) {
				sentenceWordCount++;
				for (String ww : sen.split(" ")) {
					contentWordCount++;
					// allWordCount++;
					wp=ww.split("/");
					switch (wp[1]) {
					case "nr": {// person name
						if (!news.getKeywords().contains(wp[0])) {
							news.getPersons().add(wp[0]);
							e = new Entity();
							e.text = wp[0];
							e.label = "person";//w.POS
							e.position_in_paragraph
									.add((Integer) (paragraphCount - 1));
							e.position_in_sentence
									.add((Integer) (sentenceWordCount - 1));
							e.position_in_content
									.add((Integer) (contentWordCount - 1));
							news.getKeywords().add(e.text);
							news.event.entities.add(e);
						}
						isEntity = true;
						break;
					}
					case "ns": {// location name
						if (!news.getKeywords().contains(wp[0])) {
							news.getLocations().add(wp[0]);
							e = new Entity();
							e.text = wp[0];
							e.label = "location";//w.POS
							e.position_in_paragraph
									.add((Integer) (paragraphCount - 1));
							e.position_in_sentence
									.add((Integer) (sentenceWordCount - 1));
							e.position_in_content
									.add((Integer) (contentWordCount - 1));
							news.getKeywords().add(e.text);
							news.event.entities.add(e);
						}
						isEntity = true;
						break;
					}
					case "nt": {// org name
						if (!news.getKeywords().contains(wp[0])) {
							news.getOrganizations().add(wp[0]);
							e = new Entity();
							e.text = wp[0];
							e.label = "organization";//w.POS
							e.position_in_paragraph
									.add((Integer) (paragraphCount - 1));
							e.position_in_sentence
									.add((Integer) (sentenceWordCount - 1));
							e.position_in_content
									.add((Integer) (contentWordCount - 1));
							news.getKeywords().add(e.text);
							news.event.entities.add(e);
						}
						isEntity = true;
						break;
					}
					}
				}
			}
		}
		
		news.wordCountsOfTitle=titleWordCount;
		news.wordCountsOfContent=contentWordCount;
	}


	public static void calculateFeature(News news) {
		// TODO Auto-generated method stub
		/*
		* Function:
		* Input:
		* Output:
		*/
		for (Entity e : news.event.entities) {//
			e.feature=EventFeatureExtractor.getFeatureVector(news, e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("start...");
		long start = System.currentTimeMillis();
		System.out.println("end...");
		System.out.println("执行耗时 : " + (System.currentTimeMillis() - start)
				/ 1000f + " 秒 ");
	}
}
