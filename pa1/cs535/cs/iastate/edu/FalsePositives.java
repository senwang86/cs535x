package pa1.cs535.cs.iastate.edu;

import java.util.Random;
import java.util.TreeSet;

public class FalsePositives {
	
	
	static final String CANDIDATES = "0123456789abcdefghijklmnopqrstuvwxyz";
	static Random rnd;
	
	private TreeSet<String> localDict;
	private TreeSet<String> testDict;
	private int dictSize;
	private int wordSize;
	
	private int testCaseNum;
	
	public FalsePositives(int dictSize, int wordSize, int testCaseNum){
		
		this.rnd = new Random();
		this.localDict = new TreeSet<String>();
		this.testDict = new TreeSet<String>();
		this.dictSize = dictSize;
		this.wordSize = wordSize;
		
		this.testCaseNum = testCaseNum;
		
		String tempRanString;
		
		for(int i=1;i<dictSize;i++){
			tempRanString = randomString(this.wordSize);
			this.localDict.add(tempRanString);
		}
		for(int i=0;i<testCaseNum;i++){
			tempRanString = randomString(this.wordSize);
			this.testDict.add(tempRanString);
		}
	}
	
	public float getFalsePositiveRate(BloomFilter bf){
		
		for(String s: localDict){
			bf.add(s);
		}
		
		int falseCount = 0;
		
		for(String s:testDict){
			if(bf.appears(s) && !localDict.contains(s)){
				falseCount++;
			}
		}
		float falsePositiveRates = (float)falseCount/this.testDict.size();
		return falsePositiveRates;
	}

	
	private String randomString(int len) 
	{
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append(CANDIDATES.charAt(rnd.nextInt(CANDIDATES.length())));
	   return sb.toString();
	}
	
	@Override
	public String toString(){
		String result = "FalsePositive Class:\nCandidate characters:"+CANDIDATES+"\nDictionary Size:"+this.localDict.size()+"\nWord Size:"+this.wordSize+"\nTest case Number:"+this.testDict.size()+"\nWord Example:"+ this.localDict.last()+"\n";
		return result;
	}
}
