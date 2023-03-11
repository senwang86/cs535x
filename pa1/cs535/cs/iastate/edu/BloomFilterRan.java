package pa1.cs535.cs.iastate.edu;

import java.util.ArrayList;
import java.util.Random;

public class BloomFilterRan extends BloomFilter{

	private ArrayList<ParameterPair> paraList;
	
	public BloomFilterRan(int setSize, int bitsPerElement) {
		super(setSize, bitsPerElement);
		
		paraList = new ArrayList<ParameterPair>();
		int tempA,tempB;
		Random randomGenerator = new Random();
		for(int i=0;i<k;i++){
			
//			tempA = randomGenerator.nextInt(this.filterSize-1);
//			tempB = randomGenerator.nextInt(this.filterSize-1);
			
			tempA = (int)(Math.random()*8*this.setSize+1)%this.filterSize;
			tempB = (int)(Math.random()*8*this.setSize+1)%this.filterSize;
			
			paraList.add(new ParameterPair(tempA,tempB));
		}
	}

	public void add(String s){
	
		int filterPosition;
		for(ParameterPair pp: paraList){
			filterPosition = ranHash(s.toLowerCase(),pp.getA(),pp.getB());
			localFilter.set(filterPosition);
		}
		this.dataCount++;
	}
	
	public boolean appears(String s){
		boolean isHit = true;
		int filterPosition;
		for(ParameterPair pp: paraList){
			filterPosition = ranHash(s.toLowerCase(),pp.getA(),pp.getB());
			isHit = localFilter.get(filterPosition);
			if(isHit == false)
				break;
			
		}
		return isHit;
		
	}
	
	private int ranHash(String s,int a,int b){
		
		int hashValue = s.hashCode()%this.filterSize;
//		int filterPosition = (a*hashValue+b);
//		filterPosition = filterPosition % this.filterSize;
//		return Math.abs(filterPosition);
		long h = 0xFFFFFFFFL & hashValue;
		long filterPosition = (h*a+b);
		filterPosition = filterPosition % this.filterSize;
		return (int)filterPosition;
	}
	
	@Override
	public String toString(){
		String result = "BloomFilter Random Hash:\n";
		result = result + super.toString()+"(a,b)\n";
		for(ParameterPair pp:paraList){
			result = result +"(" + pp.getA()+","+pp.getB()+")\n";
		}
		return result;
	}
}
