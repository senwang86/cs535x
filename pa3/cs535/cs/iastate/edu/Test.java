package pa3.cs535.cs.iastate.edu;

import java.util.ArrayList;

public class Test {

	public static void main(String args[]){
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("tennis");
		keywords.add("grand slam");
		WikiCrawler crawler = new WikiCrawler("/wiki/Tennis",keywords,2,"test.txt");
//		crawler.crawl();
		
		String graphFileName = "D:\\Dropbox\\Courses\\CS535x\\ProgrammingAssignment\\p3\\PavanWikiTennis.txt";;
		Graph graph = new Graph(graphFileName);
//		graph.printGraph();
		graph.topKInDegree(10);
		System.out.println();
		graph.topKOutDegree(10);
		
		PageRank pr = new PageRank(graph);
		pr.pageRank((float)0.85, (float)0, graph);
		pr.topKPageRank(10);
	}
}
