import java.util.*;
import java.io.*;

public class Parser {


    public static void main(String[] args) {
	String line = "";
	try {
	    
	    File file_read = new File("prokino_full_graph.csv");
	    FileReader fileReader = new FileReader(file_read);
	    File file_write = new File("prokino_graph.csv");
	    if (!file_write.exists()) {
		file_write.createNewFile();
	    }//if
	    
	    FileWriter fw = new FileWriter(file_write.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    //String line;
	    System.out.println("Parsing...");
	    while ((line = bufferedReader.readLine()) != null) {
		String[] temp = new String[5];
		String[] ln = line.split(",");
		if (ln.length <= 5) {
		    for (int i = 0; i < ln.length; ++i) temp[i] = ln[i];
		    //System.out.println(temp.length + '\t' + ln.length);
		    String s = temp[0] + ',' + temp[4] + ',' + temp[2] + ',' + temp[3] + ',' + temp[1] + '\n';
		    bw.write(s);
		}//if
	    }//while
	    bw.close();
	    System.out.println("Done!");
	}//try
	catch (Exception e) {
	    System.out.println(e);
	    System.out.println(line);
	}
	
    }//main



}//Parser
