package pa1.cs535.cs.iastate.edu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	
	public static void main(String args[]){
		
// 1. To test the false positive.	
	/*	
		int setSize = 1000;
		int bitsPerElement = 16;
		
		int dictSize = 1000;
		int wordSize = 10;
		
		int testCaseNum = dictSize * 100;
		
		for(int i=1;i<5;i++){
			setSize = setSize*2;
			wordSize++;
			testCaseNum = setSize*100;
			
			testBloomFilter(setSize,wordSize,bitsPerElement,testCaseNum);
			
			System.out.println("************************************************************************");
		}
		*/
//2. To test the DocumentFilter.
		/*
		String filePath = "G:\\sci.space\\sci.space\\59848";
		testDocumentFilter(filePath);
		*/
//3. To test the BloomSearch.	

		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String folderPath = null;
		String inputKeywords = null;
		String keywords[];
		ArrayList<String> keywordList = new ArrayList<String>();
		ArrayList<String> result;
		try {
			System.out.println("Please input a folderpath:\n");
			folderPath = br.readLine();
			BloomSearch bs = new BloomSearch(folderPath);
			bs.addDocuments();
			int totalFilterSize = bs.getTotalFilterSize();
			System.out.println("Total Filter Size is " + totalFilterSize+" bytes. \n");
			
			System.out.println("Please input keywords you want to search:\n");
			inputKeywords = br.readLine();
			
			while(inputKeywords.length() != 0){	
				keywords = inputKeywords.split("\\s+");
				for(String s:keywords){
					keywordList.add(s.toLowerCase());
				}
				result = bs.search(keywordList);
				if(result.size() == 0){
					System.err.println("No match document found!!\n");
				}else{
					for(String s:result){
						System.out.println(s);
					}
				}
				
				System.out.println("\n\nPlease input keywords you want to search:\n");
				inputKeywords = br.readLine();	
			}
			
			
		} catch (IOException ioe) {
			System.out.println("IO error trying to read your name!");
			System.exit(1);
		}
		
	}
	
	public static void testDocumentFilter(String filePath){
		int bitsPerElement = 8;
		DocumentFilter df = new DocumentFilter(8,filePath);
		df.addDocument();
		System.out.println(df.toString());
	}
	
	public static void testBloomFilter(int setSize,int wordSize, int bitsPerElement, int testCaseNum){
		float falsePositiveRate;
		
		FalsePositives fp = new FalsePositives(setSize,wordSize,testCaseNum);
		System.out.println(fp.toString());
		
		BloomFilterDet bfd = new BloomFilterDet(setSize,bitsPerElement);
			
		falsePositiveRate = fp.getFalsePositiveRate(bfd);
		System.out.println(bfd.toString());
		System.out.println("Deterministic Hash FalsePositiveRate: " + falsePositiveRate+"\n");
		
		
		BloomFilterRan bfr = new BloomFilterRan(setSize,bitsPerElement);
		falsePositiveRate = fp.getFalsePositiveRate(bfr);
		System.out.println(bfr.toString());
		System.out.println("Random Hash FalsePositiveRate: " + falsePositiveRate+"\n");
		
		fp = null;
		bfd = null;
		bfr = null;
	}
	
	public static void testBloomSearch(String folderPath){
		BloomSearch bs = new BloomSearch(folderPath);
		bs.addDocuments();
		ArrayList<String> queryWords = new ArrayList<String>();
		queryWords.add("edu".toLowerCase());
		queryWords.add("mit".toLowerCase());
		queryWords.add("questions".toLowerCase());
		queryWords.add("come".toLowerCase());
		queryWords.add("accommodate".toLowerCase());
		
		bs.search(queryWords);
	}
}
