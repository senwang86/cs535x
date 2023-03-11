package pa2.cs535.cs.iastate.edu;

public class MinHashSpeed {
	
	public static void main(String[] args){
		String folderPath = null;
//		folderPath = "/Users/watson/Code/workspace/cs535/space/";
		folderPath = "D:\\programmer\\workspace\\cs535_S2015\\space\\";
		int numPermutation = 400;
		
		MinHash mh= new MinHash(folderPath,numPermutation);
		
		Document tempD1;
		Document tempD2;
		float exactJacSimilarity = 0;
		float approJacSimilarity = 0;
		
		long timeStart;
		long timeEnd;
		
		timeStart = System.currentTimeMillis();
		for(int i=0;i<mh.getAllDocuments().size();i++){
			tempD1 = mh.getAllDocuments().get(i);
			for(int j=i+1;j<mh.getAllDocuments().size();j++){
				tempD2 = mh.getAllDocuments().get(j);
				exactJacSimilarity = tempD1.exactJaccard(tempD2);
			}
		}
		timeEnd = System.currentTimeMillis();
		
		System.out.println(mh.getAllDocuments().size()+" files takes "+(timeEnd-timeStart)+ " milliseconds to calculate the exact Jaccard similarity.");
		System.out.println("------------------------------------------------");
		
		
		
		timeStart = System.currentTimeMillis();
		
		for(int i=0;i<mh.getAllDocuments().size();i++){
			tempD1 = mh.getAllDocuments().get(i);
			for(int j=i+1;j<mh.getAllDocuments().size();j++){
				tempD2 = mh.getAllDocuments().get(j);
				approJacSimilarity = tempD1.approximateJaccard(tempD2);
				
			}
		}
		
		timeEnd = System.currentTimeMillis();
		System.out.println(mh.getAllDocuments().size()+" files takes "+(timeEnd-timeStart)+ " milliseconds to calculate the approximate Jaccard similarity.");
		}
}
