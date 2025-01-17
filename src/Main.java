import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
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

    private static boolean checkReqs(HashMap<String, Node> graph, String start, String goal, final String[] nReqs, final String[] aReqs) {
	System.out.println("checkReqs start");
	Node curr = graph.get(goal);
	ArrayList<String> nameReqs = new ArrayList<String>();
	ArrayList<String> attReqs = new ArrayList<String>();
	for (String s : nReqs) if (s != null)nameReqs.add(s);
	for (String s : aReqs) if (s != null) attReqs.add(s);
	while (!curr.name.equals(start)) {
	    System.out.println("while curr name: " + curr.name + " nameReqs: " + nameReqs + " attReqs: " + attReqs);
	    if (!nameReqs.isEmpty() && nameReqs.contains(curr.name)) {nameReqs.remove(curr.name);
		System.out.println("Removed! " + nameReqs);}
	    if (!attReqs.isEmpty()) attReqs.remove(curr.attribute);
	    curr = graph.get(curr.parent);
	}//while
	System.out.println("checkReqs end - start: " + start + " curr: " + curr.name);
	if (nameReqs.isEmpty() && attReqs.isEmpty()) return true;
	else return false;
    }//checkReqs

    private static ArrayList<String> depth_iterating(HashMap<String, Node> graph, String[] search) {
	String start = search[0];
	String goal = search[1];
	//System.out.println(graph.size());
	for (String s : search) {
	    //System.out.println(s);
	}//for
	ArrayList<String> nameReqs = new ArrayList<String>();
	ArrayList<String> attReqs = new ArrayList<String>();
	ArrayList<String> path = new ArrayList<String>();
	for (int i = 2; i < search.length; ++i) {
	    String[] reqs = search[i].split(":");
	    if (reqs[0].equals("N")) nameReqs.add(reqs[1]);
	    else attReqs.add(reqs[1]);
	    //System.out.println(reqs[1]);
	}//for
	for (int lim = 1; lim < 5; ++lim) {
	    System.out.println("Iterate! lim = " + lim);
	    graph.get(start).visited = true;
	    path = depth_limited(graph, graph.get(start), start, goal, nameReqs.toArray(new String[nameReqs.size()]), attReqs.toArray(new String[nameReqs.size()]), 0, lim);
	    graph.get(start).visited = false;
	    if (path != null) return path;
	    else {
		Set<String> set = graph.keySet();
		for (Object s: set.toArray()) {
		    //System.out.println(s.toString());
		    graph.get(s.toString()).visited = false;
		}//for
	    }//else
	}//for
	return null;
    }//depth_iterating

    private static ArrayList<String> depth_limited(HashMap<String, Node> graph, Node curr, String start, String goal, final String[] nameReqs, final String[] attReqs, int count, int lim) {
	if (curr.name.equals("K") || curr.name.equals("Missense")) System.out.println("Branch: " + curr.name + " count = " + count + " lim = " + lim);
	if (count < lim) {
	    //System.out.println(expSet);
	    ArrayList<String> path = new ArrayList<String>();
	    if (curr.name.equals(goal) && checkReqs(graph, start, goal, nameReqs, attReqs)) {
		while (!curr.name.equals(start)) {
		    path.add(0, curr.name);
		    curr = graph.get(curr.parent);
		}//while
		path.add(0, start);
		return path;
	    }//if
	    else  if (!curr.name.equals(goal)) {
		for (String s: curr.sourceOfChildren) {
		    if (curr.name.equals("K") || curr.name.equals("Missense")) System.out.println("\tcurr: " + curr.name + " s: " + s + " " + graph.get(s).visited);
		    if (!graph.get(s).visited) {//if (!expSet.contains(s)) {
			//expSet.add(s);
			graph.get(s).visited = true;
			graph.get(s).parent = curr.name;
			if (curr.name.equals("K") || curr.name.equals("Missense")) System.out.println("\tCurr: " + curr.name + " Source: " + s);
			ArrayList<String> temp = depth_limited(graph, graph.get(s), start, goal, nameReqs, attReqs, count+1, lim);
			graph.get(s).visited = false;
			if (temp != null) return temp;
		    }//if
		}//for
		for (String s: curr.targetOfChildren) {
		    if (!graph.get(s).visited) {//if (!expSet.contains(s)) {
			//expSet.add(s);
			graph.get(s).visited = true;
			graph.get(s).parent = curr.name;
			System.out.println("\tCurr: " + curr.name + " Target: " + s);
			ArrayList<String> temp = depth_limited(graph, graph.get(s), start, goal, nameReqs, attReqs, count+1, lim);
			graph.get(s).visited = false;
			if (temp != null) return temp;
		    }//if
		}//for
		System.out.println("Branch End! - No Successors");
		return null;
	    }//else if
	    else {
		System.out.println("Branch End! - Goal found but not with reqs");
		return null;
	    }//else
	}//if
	else {
	    System.out.println("Branch End! - lim reached");
	    return null;
	}//else
    }//depth_limited
    
    private static ArrayList<String> bfs(HashMap<String, Node> graph, String[] search) {
	ArrayList<String> path = new ArrayList<String>();
	path.add(search[0]);
	String start = search[0];
	//for (String str: search) System.out.print(str + " , ");
	//System.out.println();
	for (int i = 0; i < search.length-1; ++i) {
	    //System.out.println(start);
	    ArrayList<String> temp = new ArrayList<String>();
	    if (i  == search.length-2) temp = bfs_aux(graph, start, search[i+1], true);
	    else {
		String[] arg = search[i+1].split(":");
		//System.out.println(arg[0] + " " + arg[1]);
		if (arg[0].equals("N")) temp = bfs_aux(graph, start, arg[1], true);
		else temp = bfs_aux(graph, start, arg[1], false);
	    }//else
	    if (temp != null) {
		for (int j = 0; j < temp.size(); ++j) path.add(temp.get(j));
		start = path.get(path.size()-1);
	    }//if
	    else {
		path = null;
		break;
	    }//else
	}//for
	return path;
    }//bfs
    
    private static ArrayList<String> bfs_aux(HashMap<String, Node> graph, String start, String target, boolean nameSearch) {
	Queue<Node> q = new LinkedList<Node>();
	ArrayList<Node> visited = new ArrayList<Node>();
	//System.out.println("start: " + start + " = " + graph.containsKey(start) + " , target: " + target + " = " + graph.containsKey(target));
	q.add(graph.get(start));
	boolean found = false;
	String goal = new String();
	while (!q.isEmpty()) {
	    Node temp = q.poll();
	    //System.out.println("curr: " + temp.name + " , target: " + target + " , start: " + start);
	    if (nameSearch) {
		//System.out.println("N Goal Check - " + temp.name);
		if (temp.name.equals(target)) {
		    found = true;
		    goal = temp.name;
		    break;
		}//if
	    }//if
	    else {
		//System.out.println("A Goal Check - " + target + " " + temp.attribute);
		if (temp.attribute.equals(target)) {
		    found = true;
		    goal = temp.name;
		    break;
		}//if
	    }//else
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
	    String curr = goal;
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

    public static ArrayList<String> combine = new ArrayList<String>();
    
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

    public static void setCombine(String comb) {
	comb = comb.replace(" ", "");
	if (!comb.equals("none")) {
	    String[] combs = comb.split(",");
	    for (String s: combs) combine.add(s);
	}//if
    }//setCombine
    
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
	    case "Combine Nodes Containing":
		setCombine(opt[1]);
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
		boolean print = false;
		String[] temp = line.split(",");
		//System.out.println(temp.length);
		//if (temp.length < 5) for (int i = 0; i < temp.length; ++i) System.out.println(temp[i]);
		String s = "";
		for (int i = 0; i < temp.length; ++i){
		    if (temp[i].contains("#")) {
			temp[i] = temp[i].split("#")[1];
		    }//if
		    temp[i] = temp[i].replace("\"", "");
		}//for
		if (temp[1].isEmpty() || temp[3].isEmpty()) {
		    //System.out.println(line);
		    continue;
		}//if
		if (temp[1].equals("comment") || temp[0].equals("label") || temp[2].equals("label") || temp[0].equals("comment") || temp[2].equals("comment")) continue;
		if (!REMOVE.contains(temp[1])) {
		    if (temp[1].equals("type")) {
			types.put(temp[0], temp[2]);
		    }//if
		    else {
			if (line.contains("ModifiedResidueType")) {
			    //System.out.println(line);
			    //for (String str: temp) System.out.print(str + " , ");
			    //System.out.print('\n');
			    //print = true;
			}//if
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
		    //if (temp[3].equals("label")) System.out.println(line + " " + REMOVE.contains(temp[3]));
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
		//if (print) System.out.println(s);
		if (!s.isEmpty()) bw.write(s);
		//System.out.println(s  + "\n");
	    }//while
	    fileReader.close();
	    bw.close();
	    if (!combine.isEmpty()) {
		File file_read1_2 = new File("temp.csv");
		FileReader fileReader1_2 = new FileReader(file_read1_2);
		File file_write1_2 = new File("temp2.csv");
		if (!file_write1_2.exists()) {
		    file_write1_2.createNewFile();
		}//if
		FileWriter fw1_2 = new FileWriter(file_write1_2.getAbsoluteFile());
		BufferedWriter bw1_2 = new BufferedWriter(fw1_2);
		BufferedReader bufferedReader1_2 = new BufferedReader(fileReader1_2);
		ArrayList <String> combined = new ArrayList<String>();
		while ((line = bufferedReader1_2.readLine()) != null) {
		    String[] temp = line.split(",");
		    boolean changed = false;
		    for (String s: combine) {
			if (temp[0].contains(s)) {
			    if (!combined.contains(temp[0])) {
				combined.add(temp[0]);
				temp[0] = types.get(temp[0]);
				types.put(temp[0], temp[0]);
				if (degrees.get(temp[0]) == null) degrees.put(temp[0], (long)1);
				else degrees.put(temp[0], degrees.get(temp[0]).longValue()+(long)1);
			    }//if
			    else temp[0] = types.get(temp[0]);
			    changed = true;
			}//if
			if (temp[2].contains(s)) {
			    if (!combined.contains(temp[2])) {
				combined.add(temp[2]);
				temp[2] = types.get(temp[2]);
				types.put(temp[2], temp[2]);
				if (degrees.get(temp[2]) == null) degrees.put(temp[2], (long)1);
				else degrees.put(temp[2], degrees.get(temp[2]).longValue()+(long)1);
			    }//if
			    else temp[2] = types.get(temp[2]);
			    changed = true;
			}//if
		    }//for
		    if (changed) {
			String edge = temp[0] + ',' + temp[1] + ',' + temp[2];
			String s = "";
			if (edges.get(edge) == null) {
			    edges.put(edge, (long)1);
			    s = edge + '\n';
			}//if
			else edges.put(edge, edges.get(edge).longValue()+(long)1);
			if (!s.isEmpty()) bw1_2.write(s);
		    }//else
		    else bw1_2.write(line + '\n');
		}//while
		bw1_2.close();
		fileReader1_2.close();
	    }//if
	    File file_read2;
	    if (!combine.isEmpty()) file_read2 = new File("temp2.csv");
	    else file_read2 = new File("temp.csv");
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
		//System.out.println(temp[0] + ' ' + temp[2]);
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
		    //if (s.contains("ModifiedResidueType")) System.out.println(s);
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
