package pa3.cs535.cs.iastate.edu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WikiRanker {
	public static void main(String args[]){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String graphFilePath;
		
		int k = 100;
		
		System.out.println("Please input the graph file path:");
		try {
			graphFilePath = br.readLine();
//			File file = new File(filePath);
//			String graphFileName = "D:\\Dropbox\\Courses\\CS535x\\ProgrammingAssignment\\p3\\PavanWikiTennis.txt";;
			Graph graph = new Graph(graphFilePath);
//			graph.printGraph();
			System.out.println(graphFilePath+ "\nhas "+graph.getGraphSize()+" vertices and " + graph.NumEdges()+" edges\n");
			
			System.err.println("Top "+k+ " Indegree vertices:");
			ArrayList<Vertex> topKInDegree = graph.topKInDegree(k);
			System.out.println();
			System.err.println("Top "+k+ " Outdegree vertices:");
			ArrayList<Vertex> topKOutDegree = graph.topKOutDegree(k);
			
			PageRank pr = new PageRank(graph);
			System.err.println("Top "+k+ " PageRank vertices:");
			pr.pageRank((float)0.85, (float)0.1, graph);
			
			ArrayList<Vertex> topKPageRank = pr.topKPageRank(k);
			System.out.println();
			getJaccardSimiliary(topKPageRank,topKInDegree,topKOutDegree);
			
			pr.pageRank((float)0.85, (float)0.05, graph);
			pr.topKPageRank(k);
			System.out.println();
			getJaccardSimiliary(topKPageRank,topKInDegree,topKOutDegree);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void getJaccardSimiliary(ArrayList<Vertex> kPageRank,ArrayList<Vertex> kIndegree,ArrayList<Vertex> kOutdegree){
		
		ArrayList<String> topKPageRank = getVerticesName(kPageRank);
		ArrayList<String> topKInDegree = getVerticesName(kIndegree);
		ArrayList<String> topKOutDegree = getVerticesName(kOutdegree);
		
		topKPageRank.retainAll(topKInDegree);
		float jacPageRankVSIndegree = (float) (1.0*topKPageRank.size()/(kPageRank.size()+kIndegree.size()-topKPageRank.size()));
		System.out.println("Jaccard Similary between K PageRank and K InDegree is "+jacPageRankVSIndegree);
		
		topKPageRank = getVerticesName(kPageRank);
		topKPageRank.retainAll(topKOutDegree);
		float jacPageRankVSOutdegree = (float) (1.0*topKPageRank.size()/(kPageRank.size()+kOutdegree.size()-topKPageRank.size()));
		System.out.println("Jaccard Similary between K PageRank and K OutDegree is "+jacPageRankVSOutdegree);
		
		topKInDegree.retainAll(topKOutDegree);
		float jacOutdegreeVSIndegree = (float) (1.0*topKInDegree.size()/(kOutdegree.size()+kIndegree.size()-topKInDegree.size()));
		System.out.println("Jaccard Similary between K InDegree and K OutDegree is "+jacOutdegreeVSIndegree);
	}
	
	private static ArrayList<String> getVerticesName(ArrayList<Vertex> vertices){
		ArrayList<String> vNames = new ArrayList<String>();
		for(Vertex v:vertices){
			vNames.add(v.getName());
		}
		return vNames;
	}
}
