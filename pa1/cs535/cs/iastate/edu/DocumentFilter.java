package pa1.cs535.cs.iastate.edu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentFilter {
	
	private String pathName;
	private String fileName;
	
	private String filePath;
	
	private BloomFilterDet bfd;
	
	private int bitsPerElement;
	
	private TreeSet<String> treeSet;
	
	public DocumentFilter(int bitsPerElement,String fileName,String pathName){
		this.bitsPerElement = bitsPerElement;
		this.fileName = fileName;
		this.pathName = pathName;
		
		treeSet = new TreeSet<String>();	
	}
	
	public DocumentFilter(int bitsPerElement,String filePath){
		this.bitsPerElement = bitsPerElement;
		this.filePath = filePath;
		this.fileName = filePath;
		
		treeSet = new TreeSet<String>();
	}
	
	public String getDocument(){
		return this.fileName;
	}
	
	public int filterSize(){
		if(bfd!=null)
			return bfd.filterSize;
		else{
			System.err.println("Filter is empty!\n");
			return -1;
		}
	}
	public int dataSize(){
		if(bfd!=null)
			return bfd.dataSize();
		else{
			System.err.println("Filter is empty!\n");
			return -1;
		}
	}
	public int numHashes(){
		if(bfd!=null)
			return bfd.numHashes();
		else{
			System.err.println("Filter is empty!\n");
			return -1;
		}
	}
	
	public boolean appears(String s){
		if(bfd!=null)
			return bfd.appears(s);
		else{
			System.err.println("Filter is empty!\n");
			return false;
		}
	}
	
	public void addDocument(){
		preProcessing();
		if(treeSet != null){
			int dataSize = treeSet.size();
			bfd = new BloomFilterDet(dataSize,bitsPerElement);
			for(String s: treeSet){
				bfd.add(s);
//				System.out.println(s);
			}
		}
		treeSet = null;
	}

	private void preProcessing(){
		FileInputStream fstream;
		BufferedReader br = null;
		try {
			fstream = new FileInputStream(this.filePath);
			br = new BufferedReader(new InputStreamReader(fstream));

			String strLine = null;
			
			Pattern pattern = Pattern.compile("([A-Za-z]{3,})");
			Matcher matcher;
			String tempString;
			while ((strLine = br.readLine()) != null) {
				matcher = pattern.matcher(strLine);
				while(matcher.find()){
					tempString = matcher.group();
					if(!tempString.matches("(?i)the")){
						treeSet.add(tempString.toLowerCase());
					}
					
//					System.out.println(tempString);
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@Override
	public String toString(){
		String result = "File Path: "+ this.filePath+"\nWord in document: "+ this.bfd.dataCount+"\n";
		return result;
	}
}
