package classification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;


/*
 * Project: Law
 * Author: Mengdi Zhang
 * Date: 2015-12-17 下午8:46:34
 * Version: 
 **/

public class WSC{
	
	/**
	 * function:　 生成文档的词袋(bag-of-word)
	 * @param 　单篇文档的已分好词的文本，格式为 "词1/词性 词2/词性 ..."
	 * @return　 文档的词表及词频
	 */
	
	private String modelPath = "model/classScore.model";
	
	private HashMap<String, Integer> getBOW(String textSegs){
		HashMap<String, Integer> fileDic = new HashMap<String,Integer>();
		String word;
		int termCount;
		
		try {
			for(String ww: textSegs.split(" ")){
//				System.err.println(ww);
				// 词项-词性
				if(ww.length()<2){continue;}
				word=ww.split("/")[0];
				if(word.length()<=1)continue;
				
				// 词频统计
				if(!fileDic.containsKey(word)){
					fileDic.put(word, 1);
				}else{
					termCount=fileDic.get(word);
					fileDic.put(word, termCount+1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileDic;
	}

	/**
	 * 
	 * @param classLabels 所有类标
	 * @param categoryDics 训练集。类标-类中文档
	 * @param dic 训练集的字典
	 * @return 每个类的词得分
	 */
	private HashMap<String, HashMap<String, Double>> caculateClassScores(HashMap<String, ArrayList<HashMap<String, Integer>>> categoryDics, HashMap<String, Integer> dic){
		HashMap<String, HashMap<String, Double>> classScores=new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double>classScore=null;
		
		//对于每一类，计算词典中每个词的得分
				for(String label: categoryDics.keySet()){
					classScore=new HashMap<String, Double>();
					ArrayList<HashMap<String, Integer>> fileDics = categoryDics.get(label);// 类中文档
					for(String w:dic.keySet()){// 训练集总词典
						double score= scoreOnClass(w, fileDics, dic.get(w));//词在该类上的得分
						classScore.put(w, score);
					}
					classScores.put(label,classScore);
					classScore=null;
				}
		return classScores;
	}
	
	/**
	 * function：计算一个词在一个类上的得分
	 * @param w 词
	 * @param fileDics 类的文档集
	 * @param corpus_count 词在训练集中的出现次数
	 * @return 得分。计算公式详见夏钧波硕士论文13页。
	 */
	private Double scoreOnClass(String w, ArrayList<HashMap<String, Integer>> fileDics, int corpus_count){
		Double score = .0;
		int Category_w=0;//w出现在该类中的次数
		int DF_w=0;//该类文档集中包含w的文档数
		int Corpus_w=corpus_count;// 词在训练集中的出现次数
		
		// 词在类中的统计
		for(HashMap<String, Integer> fileDic: fileDics){
			if(fileDic.containsKey(w)){
				DF_w++;
				Category_w+=fileDic.get(w);
			}
		}
		
		// 词在类上的得分
		score=(double)((double)Category_w/(double)Corpus_w)*(double)DF_w;
		
		return score;
	}
	
	
	/**
	 * function： 预测一篇文档在某类上的得分
	 * @param fileDic: 待分类文档
	 * @param X: 类id
	 * @return 得分。
	 */
	private double fileScoreOnClassX(HashMap<String,Integer> fileDic, HashMap<String, Double> classScore){
		
		//文档在某类上的得分
		double score_file=0;
		//一个词
		double score_w=0;
		double classScore_w=0;
		int termFrequency_w=0;
		
		//对于该文档中的每一个词
		for(String w:fileDic.keySet()){
			termFrequency_w=fileDic.get(w);//该词在该文档中出现的次数
			classScore_w=(double) classScore.get(w);//该词在类上的得分
			score_w=classScore_w*termFrequency_w;
			score_file+=score_w;
		}
		return score_file;
	}
	
	
	
	/**
	 * function: 分类预测
	 * @param fileDic 文档的bow及每个词对应的
	 * @param classScores 训练好的分类模型，所有类的词汇表
	 * @return 预测的类名
	 */
	private String classify(HashMap<String,Integer> fileDic,  HashMap<String, HashMap<String, Double>> classScores){
		String predict="";
		double max=0;
		double tmp=0;
		
		// 计算当前文档在每个类上的得分，取得分最高的类为预测结果
		for(String label:classScores.keySet()){//类标
			 HashMap<String, Double> classScore = classScores.get(label); // 类的词汇表
			tmp=fileScoreOnClassX(fileDic, classScore); // 当前类上的得分
			if(tmp>max){
				max=tmp;
				predict=label;
			}
		}
		return predict;
	}
	
	/**
	 * function: 训练分类模型
	 * @param trainCorpusSeg: 训练集，类名和类的哈希表，每个类内是分好词的文档
	 * @return 分类模型 -- 每个类的词汇表
	 */
	public HashMap<String, HashMap<String, Double>> train(HashMap<String, ArrayList<String>> trainCorpusSeg){
		
		//1. 训练集准备 -- 分好词的训练集转化为词袋
		HashMap<String, ArrayList<HashMap<String, Integer>>> corpusBOW = new HashMap<>(); // 训练集的词袋
		ArrayList<HashMap<String, Integer>> categoriedFilesBOW = null; //类中文档词袋
		HashMap<String, Integer> corpusDic=new HashMap<String, Integer>();// 训练集的词典
		
		for(String label: trainCorpusSeg.keySet()){
			categoriedFilesBOW = new ArrayList<>();
			for(String textSeg: trainCorpusSeg.get(label)){
				HashMap<String, Integer> fileBOW = getBOW(textSeg);//一篇分好词的文档转化为词袋
				categoriedFilesBOW.add(fileBOW);
				for(String w: fileBOW.keySet()){
					if(!corpusDic.containsKey(w)){
						corpusDic.put(w, fileBOW.get(w));
					}else{
						int count = corpusDic.get(w);
						count+=fileBOW.get(w);
						corpusDic.put(w, count);
					}
				}
			}
			corpusBOW.put(label, categoriedFilesBOW);
		}
		
		//2. 训练：计算词项的在每个类上的得分
		HashMap<String, HashMap<String, Double>> classScores = null;//分类模型 -- 每个类的词汇表
		classScores = caculateClassScores(corpusBOW, corpusDic);
		return classScores;
	}
	
	/**
	 * function：测试分类模型
	 * @param testCorpusSeg： 测试集，类名和类的哈希表，每个类内是分好词的文档
	 * @param classScores：分类模型 -- 每个类的词汇表
	 * @return 准确率
	 */
	public double test(HashMap<String, ArrayList<String>> testCorpusSeg,HashMap<String, HashMap<String, Double>> classScores ){
		double precision = .0;
		int right = 0;
		int all = 0;
		
		for(String label: testCorpusSeg.keySet()){
			for(String textSeg: testCorpusSeg.get(label)){
				HashMap<String, Integer> fileBOW = getBOW(textSeg);//一篇分好词的文档转化为词袋
				String predict = classify(fileBOW, classScores);
				if(predict.equals(label)){
					right++;
				}
				all++;
			}
		}
		precision = (double)right/(double)all;
		return precision;
	}
	
	/**
	 * function: 用已有模型分类。已训练好的模型, 12个新闻类，每个类100篇。
	 * @param fileDic: 文档的bow及每个词对应的
	 * @return 预测类型
	 */
	public String classify(HashMap<String,Integer> fileDic){
	
		ObjectInputStream oin = null;	
		HashMap<String, HashMap<String, Double>> model = null;
		try {
			oin = new ObjectInputStream(new FileInputStream(this.modelPath));
			model = (HashMap<String, HashMap<String, Double>>)oin.readObject(); // 没有强制转换到Person类型  
        oin.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		String predict = this.classify(fileDic, model);
		return predict;
	}
	
	public static void main(String[] args) throws Exception {

		WSC wsc = new WSC();
		
		//1. 载入训练集和预测集
		HashMap<String, ArrayList<String>> trainCorpusSeg = null; // 待读入
		HashMap<String, ArrayList<String>> testCorpusSeg = null; // 待读入
		
		//2. 训练分类模型
		HashMap<String, HashMap<String, Double>> classScores = wsc.train(trainCorpusSeg);
		
		//3. 测试
		Double precision = wsc.test(testCorpusSeg, classScores);
		System.out.println("分类准确率为："+precision);
	}
}


