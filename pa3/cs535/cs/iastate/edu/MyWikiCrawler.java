package pa3.cs535.cs.iastate.edu;

import java.util.ArrayList;

public class MyWikiCrawler {
	public static void main(String args[]){
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("football");
		keywords.add("super bowl");
		WikiCrawler crawler = new WikiCrawler("/wiki/american_football",keywords,100,"myWikiGraph.txt");
		crawler.crawl();
		}
}
