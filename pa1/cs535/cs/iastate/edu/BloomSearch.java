package pa1.cs535.cs.iastate.edu;

import java.io.File;
import java.util.ArrayList;

public class BloomSearch {
	
	private String folderPath;
	
	private ArrayList<DocumentFilter> docFilterList;
	
	private int bitsPerElement = 8;
	
	public BloomSearch(String folderPath){
		this.folderPath = folderPath;
		this.docFilterList = new ArrayList<DocumentFilter>(); 
	}
	
	public ArrayList<String> search(ArrayList<String> keywordList){
		
		boolean isHit = true;
		ArrayList<String> resultList = new ArrayList<String>();
		for(DocumentFilter df: docFilterList){
			isHit = true;
			for(String keyword:keywordList){
				if(!df.appears(keyword)){
					isHit = false;
					break;
				}
			}
			if(!isHit){
				continue;
			}else{
				resultList.add(df.getDocument());
//				System.out.println(df.getDocument());
			}
			
		}
		
		return resultList;
	}
	
	public void addDocuments(){
		File folder = new File(this.folderPath);	
		File[] listOfFiles = folder.listFiles();
		System.out.println(this.folderPath+" contains "+listOfFiles.length+" files.\nBuilding DoucmentFilter...");
		DocumentFilter df;
		for(int i=0;i<listOfFiles.length;i++){
//			System.out.println(listOfFiles[i].getAbsolutePath());
			df = new DocumentFilter(this.bitsPerElement,listOfFiles[i].getAbsolutePath());
			df.addDocument();
			docFilterList.add(df);
		}
		System.out.println(docFilterList.size()+" DocumentFilters have been built\n");
	}
	
	public int getTotalFilterSize(){
		int totalSize = 0;
		for(DocumentFilter df: docFilterList){
			totalSize = df.filterSize()+totalSize;
		}
		return totalSize/8;
	}
}
