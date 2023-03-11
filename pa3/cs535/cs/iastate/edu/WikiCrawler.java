package pa3.cs535.cs.iastate.edu;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiCrawler {
	
	private String seedUrl;
	private ArrayList<String> keywords;
	private int maxSites;
	private String outputFileName;
	
	private Queue<String> linkQueue;
	private ArrayList<String> edges;
	
	private TreeSet<String> visitedLinks;
	private TreeSet<String> noKeywordLinks;
	
	private String urlPrefix;
	
	private String robotsTXT;
	
	public WikiCrawler(){
		
	}
	
	public WikiCrawler(String url,ArrayList<String> keywords,int max, String fileName){
		this.seedUrl = url;
		this.maxSites = max;
		this.outputFileName = fileName;
		this.keywords = new ArrayList<String>();
		for(String keyword:keywords){
			this.keywords.add(keyword);
		}
		
		linkQueue = new LinkedList();
		linkQueue.add(this.seedUrl);
		
		visitedLinks = new TreeSet<String>();
		visitedLinks.add(this.seedUrl);
		
		noKeywordLinks = new TreeSet<String>();
		edges = new ArrayList<String>();
		urlPrefix = "http://en.wikipedia.org";
		robotsTXT = Connection.get(urlPrefix+"/robots.txt");
	}
	
	public void crawl(){
		
		String currentPageLink;
		TreeSet<String> subLinks;
		String temp;
		writeToFile(String.valueOf(this.maxSites));
		long startTime = System.currentTimeMillis();
		while(!this.linkQueue.isEmpty()){
			currentPageLink = linkQueue.poll();
//			System.out.println(currentPageLink);
			subLinks = extractLinks(currentPageLink);
			for(String link:subLinks){
				temp = currentPageLink + " " + link;
				if(this.visitedLinks.size()<this.maxSites){
					this.edges.add(temp);
					if(!this.visitedLinks.contains(link)){
						this.visitedLinks.add(link);
						this.linkQueue.add(link);
					}
				}else{
					if(this.visitedLinks.contains(link)){
						this.edges.add(temp);
					}
				}
			}
//			this.linkQueue.addAll(subLinks);
//			this.visitedLinks.addAll(subLinks);
			writeToFile(this.edges);
			this.edges.clear();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time is "+ (endTime-startTime));
		
		
	}
	
	private boolean isContainKeywords(String url){
		
		boolean isContainKeywords = true;
		
		String rawTextUrl = "http://en.wikipedia.org/w/index.php?title=$$$$&action=raw";
		String tokens[] = url.split("/");
		String title = tokens[tokens.length-1];
		String newString = rawTextUrl.replace("$$$$", title);
//		System.out.println(newString);
		
		String pageContent = Connection.get(newString);
		pageContent = pageContent.toLowerCase();
		
		for(String keyword:this.keywords){
			if(!pageContent.contains(keyword)){
				isContainKeywords = false;
				break;
			}
		}
		
		return isContainKeywords;
	}
	
	private TreeSet<String> extractLinks(String url){
		String pageContent = Connection.get(this.urlPrefix+url);
		TreeSet<String> paragraphList = new TreeSet<String>();
		extractTags("<p>(.+?)</p>",pageContent,paragraphList);
		TreeSet<String> linkList = new TreeSet<String>();
		TreeSet<String> subLinks = new TreeSet<String>();

		for (String paragraph : paragraphList) {
			extractTags("href=\"([^:#]+?)\"", paragraph, linkList);
			for (String link : linkList) {

				// Be careful when testing!!!!!

				if (!robotsTXT.contains(link) && !this.noKeywordLinks.contains(link)) {
					if(!subLinks.contains(link)){
						if(this.visitedLinks.contains(link)){
							subLinks.add(link);
						}else{
							if(this.visitedLinks.size()<this.maxSites){
								if (isContainKeywords(link)) {
									subLinks.add(link);
//									this.visitedLinks.add(link);
								}else{
									this.noKeywordLinks.add(link);
								}
							}
						}
					}
				}
			}
			linkList.clear();
		}
		paragraphList.clear();
		paragraphList = null;
		linkList = null;
		
		return subLinks;

	}
	
	private void extractTags(String pattern,String target,TreeSet<String> result){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(target);
		
		while(m.find()){
			result.add(m.group(1));
		}
	}
	
	private void writeToFile(ArrayList<String> lines){
		PrintWriter writer;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(this.outputFileName, true)));
			for (String line : lines) {
				writer.println(line);
			}
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToFile(String line){
		PrintWriter writer;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(this.outputFileName, true)));
			writer.println(line);
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
