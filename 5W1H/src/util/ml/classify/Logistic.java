package util.ml.classify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * Project: 5W1H
 * Author: Mengdi Zhang
 * Date: 2014-12-15 下午4:22:57
 * Version: 
 **/

public class Logistic {
	/**
	 * Function:
	 *
	 */

	  /** the learning rate */
    private double rate;

    /** the weight to learn */
    private double[] weights;

    /** the number of iterations */
    private int ITERATIONS = 3000;

    public Logistic() {
        this.rate = 0.0001;
        weights = new double[6];
    }
    
    public double[] getWeights(){
    	return this.weights;
    }

    private double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    
    public void train(ArrayList<double[]> instances) {
    	double[] t_weights = new double[this.weights.length];
    	int n;
        for (n=0; n<ITERATIONS; n++) {
        	double[] x;
        	for(int k=0;k<this.weights.length;k++)
            	t_weights[k] = this.weights[k];
            for (int i=0; i<instances.size(); i++) {
                x = instances.get(i);
                double predicted = classify(x);
//                int label = instances.get(i).getLabel();
                int label=-1;
                
                this.weights[0] = this.weights[(int)x[0]]+this.rate*(label-predicted);
                this.weights[(int)x[1]] = this.weights[(int)x[1]]+this.rate*(label-predicted);
                this.weights[(int)x[2]] = this.weights[(int)x[2]]+this.rate*(label-predicted);
                this.weights[(int)x[3]] = this.weights[(int)x[3]]+this.rate*(label-predicted);
                this.weights[(int)x[4]] = this.weights[(int)x[4]]+this.rate*(label-predicted);
                this.weights[(int)x[5]] = this.weights[(int)x[5]]+this.rate*(label-predicted);
                
            }
            if(isConverge(t_weights, weights))
            	break;
        }
            System.out.println("iteration: " + n);
    }
    
    public boolean isConverge(double[] w1, double[] w2){
    	double result= 0.0;
    	for(int i=0;i<w1.length;i++)
    		result = result+(w1[i]-w2[i])*(w1[i]-w2[i]);
    	if(result<0.0002)
    		return true;
    	else
    		return false;
    }
    
    public double mulDoub(double[] wvector){
    	double re_d = .0;
    	
    	for(int i=0;i<wvector.length;i++){
    		re_d += this.weights[i]*wvector[i];
    	}
    	return re_d;
    }

    public double classify(double[] x) {
        double logit = .0;
        logit = mulDoub(x);//project multiple dimension to one , by SUMMING, x'=W*X
        return sigmoid(logit);
    }
    
    public void printWeight(String path) throws Exception{
    	File proFile = new File(path);
		FileOutputStream fos = new FileOutputStream(proFile);
		Writer out = new OutputStreamWriter(fos,"UTF-8");
		PrintWriter pw = new PrintWriter(out);
		StringBuffer weights = new StringBuffer();
		int i;
		for(i=0;i<this.weights.length-1;i++)
			weights.append(this.weights[i]).append("\t");
		weights.append(this.weights[i]);
		pw.println(weights.toString());
		pw.close();
    }
    
    public void loadWeight(String path) throws Exception{
    	File inf = new File(path);
		FileInputStream fis= new FileInputStream(inf);
		InputStreamReader in= new InputStreamReader(fis,"UTF-8");
		Scanner cin= new Scanner(in);
		String[] line = cin.nextLine().split("\t");
		System.out.println("weights.length:   "+this.weights.length);
		System.out.println(line.length);
		if(line.length==this.weights.length){
			for(int i=0;i<line.length;i++)
				this.weights[i] = Double.valueOf(line[i]);
			System.out.println("load weights success!");
		}
		else
			System.out.println("load weights failed!");
		cin.close();
    }
    
    //训练target权重
    public void writeWeights(){
//    	dataset ds = new dataset();
		Logistic  ls = new Logistic();
//		ds.readData(cp.qas);
//		ls.train(ds.vector);
//		ls.printWeight();
    }
    
	public static void main(String[] args) throws Exception {
		System.out.println("start...");
		long start = System.currentTimeMillis();
		System.out.println("end...");
		System.out.println("执行耗时 : " + (System.currentTimeMillis() - start)
				/ 1000f + " 秒 ");

	}
}


