一、WSC（word-score-classifier）简介:

WSC.java是一个基于word-score思想的一个文本分类器，java实现。算法思想详见夏钧波研究生论文。

二、可调用函数接口：
1. 
/**
 * function: 训练分类模型
 * @param trainCorpusSeg: 训练集，类名和类的哈希表，每个类内是分好词的文档
 * @return 分类模型 -- 每个类的词汇表
 */
public HashMap<String, HashMap<String, Double>> train(HashMap<String, ArrayList<String>> trainCorpusSeg){}

2. 
/**
 * function：测试分类模型
 * @param testCorpusSeg： 测试集，类名和类的哈希表，每个类内是分好词的文档
 * @param classScores：分类模型 -- 每个类的词汇表
 * @return 准确率
 */
public double test(HashMap<String, ArrayList<String>> testCorpusSeg,HashMap<String, HashMap<String, Double>> classScores ){}


3.
/**
 * function: 用已有模型分类。已训练好的模型, 12个新闻类，每个类100篇。
 * @param fileDic: 文档的bow及每个词对应的
 * @return 预测类型，12个类之一："财经", "国际", "国内", "健康", "教育", "军事", "科技","汽车", "社会", "体育", "娱乐","旅游"
 */
public String classify(HashMap<String,Integer> fileDic){}

三、使用方式参见main函数：

public static void main(String[] args) throws Exception {

	WSClassification wsc = new WSClassification();
	
	//1. 载入训练集和预测集
	HashMap<String, ArrayList<String>> trainCorpusSeg = null; // 待读入
	HashMap<String, ArrayList<String>> testCorpusSeg = null; // 待读入
	
	//2. 训练分类模型
	HashMap<String, HashMap<String, Double>> classScores = wsc.train(trainCorpusSeg);
	
	//3. 测试
	Double precision = wsc.test(testCorpusSeg, classScores);
	
	System.out.println("分类准确率为："+precision);
}

