import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

public class Main {

    private static class Node {

	private String name;
	private String parent;
	private long degree;
	private String attribute;
	private ArrayList<String> sourceOfChildren = new ArrayList<String>();
	private HashMap<String, String> sourceOfEdges = new HashMap<String, String>();
	private ArrayList<String> targetOfChildren = new ArrayList<String>();
	private HashMap<String, String> targetOfEdges = new HashMap<String, String>();
	private boolean visited = false;

	Node(String name, String attribute, long degree) {
	    this.name = name;
	    this.degree = degree;
	    this.attribute = attribute;
	}//Node

	private void setParent(String parent) {
	    this.parent = parent;
	}//setParent

	private void addSourceOfChild(String child, String edge) {
	    sourceOfChildren.add(child);
	    sourceOfEdges.put(child, edge);
	}//addChild

	private void addTargetOfChild(String child, String edge) {
	    targetOfChildren.add(child);
	    targetOfEdges.put(child, edge);
	}//addChild

	private String edgeToString(Node nd, HashMap<String, Long> edges) {
	    String line;
	    if (sourceOfChildren.contains(nd.name)) {
		line = name + ',' + attribute + ',' + Long.toString(degree) + ',' + sourceOfEdges.get(nd.name) + ',';
		String temp = name + ',' + sourceOfEdges.get(nd.name) + ',' + nd.name;
		line += edges.get(temp).toString() + ',' + nd.name + ',' + nd.attribute + ',' + Long.toString(nd.degree) + '\n';
	    }//if
	    else {
		line = nd.name + ',' + nd.attribute + ',' + Long.toString(nd.degree) + ',' + targetOfEdges.get(nd.name) + ',';
		String temp = nd.name + ',' + targetOfEdges.get(nd.name) + ',' + name;
		line += edges.get(temp).toString() + ',' + name + ',' + attribute + ',' + Long.toString(degree) + '\n';	
	    }//else
	    return line;
	}//edgeToString
	
    }//Node

    private static ArrayList<String> bfs(HashMap<String, Node> graph, String[] search) {
	ArrayList<String> path = new ArrayList<String>();
	path.add(search[0]);
	for (int i = 0; i < search.length-1; ++i) {
	    ArrayList<String> temp = bfs_aux(graph, search[i], search[i+1]);
	    if (temp != null) for (int j = 0; j < temp.size(); ++j) path.add(temp.get(j));
	    else {
		path = null;
		break;
	    }//else
	}//for
	return path;
    }//bfs
    
    private static ArrayList<String> bfs_aux(HashMap<String, Node> graph, String start, String target) {
	Queue<Node> q = new LinkedList<Node>();
	ArrayList<Node> visited = new ArrayList<Node>();
	q.add(graph.get(start));
	boolean found = false;
	while (!q.isEmpty()) {
	    Node temp = q.poll();
	    if (temp.name.equals(target)) {
		found = true;
		break;
	    }//if
	    for (int i = 0; i < temp.sourceOfChildren.size(); ++i) {
		String child = temp.sourceOfChildren.get(i);
		if (!graph.get(child).visited) {
		    visited.add(graph.get(child));
		    graph.get(child).visited = true;
		    graph.get(child).parent = temp.name;
		    q.add(graph.get(child));
		}//if
	    }//for
	    for (int i = 0; i < temp.targetOfChildren.size(); ++i) {
		String child = temp.targetOfChildren.get(i);
		if (!graph.get(child).visited) {
		    visited.add(graph.get(child));
		    graph.get(child).visited = true;
		    graph.get(child).parent = temp.name;
		    q.add(graph.get(child));
		}//if
	    }//for
	}//while
	for (Node nd : visited) nd.visited = false;
	if (found) {
	    ArrayList<String> path = new ArrayList<String>();
	    String curr = target;
	    while (!curr.equals(start)) {
		path.add(0, curr);
		//graph.get(curr).visited = false;
		curr = graph.get(curr).parent;
	    }//while
	    //path.add(0, start);
	    return path;
	}//if
	else return null;
    }//bfs
    
    public static ArrayList<String> REMOVE = new ArrayList<String>();
    /*= new ArrayList<String> (Arrays.asList("hasDbXref", "hasNameSpace", "label", "comment", "chromosomalPosition", "hasCommonName", "hasEndLocation", "hasFASTAFormat", "hasFullName", "hasIdentifier", "hasMutationAA", "hasMutationId", "hasOtherName", "hasProteinStructure", "hasScientificName", "hasSequence", "hasStartLocation", "hasTopologicalDomainType", "hasUniprotPrimaryName","hasUniprotSynonymName", "hasURI", "isPrimaryUniprotId", "locatedIn", "occursIn","subClassOf", "hasMutationImpact"));
    //public static ArrayList<String> REMOVE = new ArrayList<String>(REMOVE_TEMP);
    */
    public static ArrayList<String> GENE_LIST = new ArrayList<String>(); //= {"Human_EGFR"};

    public static long DEGREE_THRESHOLD;

    public static ArrayList<String[]> searches = new ArrayList<String[]>();

    public static void setGeneList(String genes) {
	genes = genes.replace(" ", "");
	genes = genes.replace("\"", "");
	String[] temp = genes.split(",");
	for (int i = 0; i < temp.length; ++i) GENE_LIST.add(temp[i]);
    }//setGeneList

    public static void setRemove(String remove) {
	remove = remove.replace(" ", "");
	remove = remove.replace("\"", "");
	String[] temp = remove.split(",");
	for (int i = 0; i < temp.length; ++i) REMOVE.add(temp[i]);
    }//setGeneList

    public static void setDegreeThreshold(String deg) {
	deg = deg.replace(" ", "");
	deg = deg.replace("\"", "");
	DEGREE_THRESHOLD = Long.parseLong(deg);
    }//setDegreeThreshold
    
    public static void setMax(String max) {
	max = max.replace(" ", "");
	max = max.replace("\"", "");
	max = '"' + max + '"';
	Style.changeMax(max);
    }//changeMax
    
    public static void readOptions() throws IOException {
	File file_read = new File("Options.txt");
	FileReader fileReader = new FileReader(file_read);
	BufferedReader bufferedReader = new BufferedReader(fileReader);
	String line;
	while ((line = bufferedReader.readLine()) != null) {
	    String[] opt = line.split(":");
	    //System.out.println(opt.length + " " + opt[0]);
	    switch (opt[0]) {
	    case "Genes":
		setGeneList(opt[1]);
		break;
	    case "Remove":
		setRemove(opt[1]);
		break;
	    case "Threshold":
		setDegreeThreshold(opt[1]);
		break;
	    case "Max Size":
		setMax(opt[1]);
		break;
	    case "Shortest Path - Start":
		String temp = bufferedReader.readLine();
		while (!temp.equals("End")) {
		    //System.out.println(temp);
		    String[] nodes = temp.split(" ");
		    searches.add(nodes);
		    temp = bufferedReader.readLine();
		}//while 
		break;
	    default:
		break;
	    }//switch
	}//while
	fileReader.close();
    }//readOptions
    
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	try {
	    readOptions();
	    System.out.println("Parsing...");
	    File file_read = new File("prokino_graph.csv");
	    FileReader fileReader = new FileReader(file_read);
	    File file_write = new File("temp.csv");
	    if (!file_write.exists()) {
		file_write.createNewFile();
	    }//if
	    
	    FileWriter fw = new FileWriter(file_write.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    String line;
	    bufferedReader.readLine();
	    //int typeCount = 0;
	    HashMap<String, String> types = new HashMap<String, String>();
	    HashMap<String, Long> degrees = new HashMap<String, Long>();
	    HashMap<String, Long> edges = new HashMap<String, Long>();
	    for (int i = 0; i < GENE_LIST.size(); ++i) types.put(GENE_LIST.get(i), "Gene");
	    while ((line = bufferedReader.readLine()) != null) {
		
		String[] temp = line.split(",");
		//System.out.println(temp.length);
		//if (temp.length < 5) for (int i = 0; i < temp.length; ++i) System.out.println(temp[i]);
		String s = "";
		for (int i = 0; i < temp.length; ++i){
		    if (temp[i].contains("#")) {
			temp[i] = temp[i].split("#")[1];
		    }//if
		}//for
		if (temp[1].equals("comment")) continue;
		if (!REMOVE.contains(temp[1])) {
		    if (temp[1].equals("type")) {
			types.put(temp[0], temp[2]);
		    }//if
		    else {
			String edge = temp[0] + ',' + temp[1] + ',' + temp[2];
			if (degrees.get(temp[0]) == null) degrees.put(temp[0], (long)1);
			else degrees.put(temp[0], degrees.get(temp[0]).longValue()+(long)1);
			if (degrees.get(temp[2]) == null) degrees.put(temp[2], (long)1);
			else degrees.put(temp[2], degrees.get(temp[2]).longValue()+(long)1);
			if (edges.get(edge) == null) {
			    edges.put(edge, (long)1);
			    s += edge + '\n';
			}//if
			else edges.put(edge, edges.get(edge).longValue()+(long)1);
		    }//else
		}//if
		if (temp.length > 3) {
		    if (!REMOVE.contains(temp[3])) {
			if (temp[3].equals("type")) {
			    types.put(temp[2], temp[4]);
			}//if
			else {
			    String edge = "";
			    if (temp.length == 4) edge += temp[2] + ',' + temp[3] + ',' + temp[2];
			    else {
				edge += temp[2] + ',' + temp[3] + ',' + temp[4];
				if (degrees.get(temp[2]) == null) degrees.put(temp[2], (long)1);
				else degrees.put(temp[2], degrees.get(temp[2]).longValue()+(long)1);
				if (temp.length > 4) {
				    if (degrees.get(temp[4]) == null) degrees.put(temp[4], (long)1);
				    else degrees.put(temp[4], degrees.get(temp[4]).longValue()+(long)1);
				}//if
			    }//else
			    if (edges.get(edge) == null) {
				edges.put(edge, (long)1);
				s += edge + '\n';
			    }//if
			    else edges.put(edge, edges.get(edge).longValue()+(long)1);
			}//else
		    }//if
		}//if
		if (!s.isEmpty()) bw.write(s);
		//System.out.println(s  + "\n");
	    }//while
	    fileReader.close();
	    bw.close();
	    File file_read2 = new File("temp.csv");
	    FileReader fileReader2 = new FileReader(file_read2);
	    File file_write2 = new File("result.csv");
	    if (!file_write2.exists()) {
		file_write2.createNewFile();
	    }//if
	    FileWriter fw2 = new FileWriter(file_write2.getAbsoluteFile());
	    BufferedWriter bw2 = new BufferedWriter(fw2);
	    BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
	    HashMap<String, Node> graph = new HashMap<String, Node>();
	    bw2.write("node,attribute,degree,edge,edge_degree,target,attribute,degree\n");
	    //int count = 0;
	    //HashMap<String, String> types = new HashMap();
	    while ((line = bufferedReader2.readLine()) != null) {
		String[] temp = line.split(",");
		//System.out.println(line + ' ' + temp.length);
		if (degrees.get(temp[0]).longValue() >= DEGREE_THRESHOLD && degrees.get(temp[2]).longValue() >= DEGREE_THRESHOLD) {
		    if (graph.get(temp[0]) == null) {
			Node nd = new Node(temp[0], types.get(temp[0]), degrees.get(temp[0]).longValue());
			graph.put(temp[0], nd);
		    }//if
		    if (graph.get(temp[2]) == null) {
			String targetAtt;
			if (types.containsKey(temp[2])) targetAtt = types.get(temp[2]);
			else targetAtt = temp[1].replace("has", "");
			Node nd = new Node(temp[2], targetAtt, degrees.get(temp[2]).longValue());
			graph.put(temp[2], nd);
		    }//if
		    graph.get(temp[0]).addSourceOfChild(temp[2], temp[1]);
		    graph.get(temp[2]).addTargetOfChild(temp[0], temp[1]);
		    String s = temp[0] + ',';
		    s += types.get(temp[0]) +',';
		    s += degrees.get(temp[0]).toString() + ',';
		    s += temp[1] + ',';
		    String edge = temp[0] + ',' + temp[1] + ',' + temp[2];
		    //System.out.println(edge);
		    s += edges.get(edge).toString() + ',';
		    s += temp[2] + ',';
		    if (types.containsKey(temp[2])) s += types.get(temp[2]) + ',';
		    else s += temp[1].replace("has", "") +',';
		    s += degrees.get(temp[2]).toString() + '\n';
		    bw2.write(s);
		}//if
	    }//while
	    bw2.close();
	    fileReader2.close();
	    File file_write3 = new File("result_stats.csv");
	    if (!file_write3.exists()) {
		file_write3.createNewFile();
	    }//if
	    FileWriter fw3 = new FileWriter(file_write3.getAbsoluteFile());
	    BufferedWriter bw3 = new BufferedWriter(fw3);
	    Object[] keys = degrees.keySet().toArray();
	    for (int i = 0; i < keys.length; ++i) {
		String temp = (String)keys[i] + ',' + Long.toString(degrees.get((String)keys[i])) + '\n';
		bw3.write(temp);
	    }//for
	    bw3.close();
	    System.out.println("Done");
	    System.out.println("Finding Shortest Paths...");
	    File file_write4 = new File("result_shortest_paths.csv");
	    if (!file_write4.exists()) {
		file_write4.createNewFile();
	    }//if
	    FileWriter fw4 = new FileWriter(file_write4.getAbsoluteFile());
	    BufferedWriter bw4 = new BufferedWriter(fw4);
	    bw4.write("node,attribute,degree,edge,edge_degree,target,attribute,degree\n");
	    for (int i = 0; i < searches.size(); ++i) {
		ArrayList<String> path = bfs(graph, searches.get(i));
		if (path == null) {
		    System.out.println("No path found");// between " + starts.get(i) + " and " + goals.get(i));
		    continue;
		}//if
		for (int j = 0; j < path.size()-1; ++j) {
		    String temp = graph.get(path.get(j)).edgeToString(graph.get(path.get(j+1)), edges);
		    bw4.write(temp);
		}//for
	    }//for
	    bw4.close();
	    System.out.println("Done");
	    System.out.println("Styling...");
	    Style.style();
	    System.out.println("Done");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }
    
}
