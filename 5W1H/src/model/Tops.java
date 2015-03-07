package model;
/*
 * 实现对某种数据类型的排序，排序参考对应下标的t_prob数组
 * */
public class Tops{

	private Object[] tops;
	private double[] t_prob;
	private int n;
	
	public Tops(int top_n){
		this.n = top_n;
		this.t_prob = new double[top_n];
		this.tops = new Object[top_n];
		for(int i=0;i<this.n;i++){
			this.t_prob[i] = -100;
		}
	}
	
	public void clear(){
		this.t_prob = new double[this.n];
		this.tops = new Object[this.n];
		for(int i=0;i<this.n;i++){
			this.t_prob[i] = -100;
		}
	}
	
	public int size(){
		int count=0;
		for(int i=0;i<this.n;i++){
			if(getTNProb(i)!=-100)
				count++;
		}
		return count;
	}
	
	public double getTNProb(int index){
		double re = -1;
		if(index<=this.n)
			re = this.t_prob[index];
		return re;
	}
	
	public Object getTNWord(int index){
		Object re = null;
		if(index<=this.n)
			re = this.tops[index];
		return re;
	}
	
	//index:修改targetword的索引号，从0开始
	public void set(int index, double prob){
		Object target = this.tops[index];
		int i;
		for(i=index;i<this.n-1;i++){
			this.t_prob[i] = this.t_prob[i+1];
			this.tops[i] = this.tops[i+1];
		}
		this.t_prob[i] = -100;
		this.tops[i] = null;
		this.addTarget(target, prob);
	}
	//添加目标词在对应的score排序的位置处，
	public void addTarget(Object target, double prob){
		int i=0;
		while(prob<=this.t_prob[i++]){
			if(i>this.t_prob.length-1)
				return;
		}
		int j = i-1;
		i = this.t_prob.length-1;
		while(i>j){
			if(this.tops[i-1]!=null){
				this.t_prob[i] = this.t_prob[i-1];
				this.tops[i] = this.tops[i-1];
			}
			i--;
		}
		this.t_prob[j] = prob;
		this.tops[j] = target;
	}
	
	public int getN(){
		return this.n;
	}
	
	public void printTopN(int n){
		if(n>this.n)
			return;
		for(int i=0;i<n;i++)
			System.out.println(this.tops[i]+"\t"+this.t_prob[i]);
	}
}
