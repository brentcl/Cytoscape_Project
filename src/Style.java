import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Style {

    public static String XML_START = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<vizmap documentVersion=\"3.0\" id=\"VizMap-2016_01_19-18_47\">\n<visualStyle name=\"result_style\">\n";
    public static String NETWORK = "<network>\n<visualProperty name=\"NETWORK_CENTER_X_LOCATION\" default=\"0.0\"/>\n<visualProperty name=\"NETWORK_SCALE_FACTOR\" default=\"1.0\"/>\n<visualProperty name=\"NETWORK_BACKGROUND_PAINT\" default=\"#FFFFFF\"/>\n<visualProperty name=\"NETWORK_CENTER_Y_LOCATION\" default=\"0.0\"/>\n<visualProperty name=\"NETWORK_WIDTH\" default=\"550.0\"/>\n<visualProperty name=\"NETWORK_CENTER_Z_LOCATION\" default=\"0.0\"/>\n<visualProperty name=\"NETWORK_DEPTH\" default=\"0.0\"/>\n<visualProperty name=\"NETWORK_NODE_SELECTION\" default=\"true\"/>\n<visualProperty name=\"NETWORK_EDGE_SELECTION\" default=\"true\"/>\n<visualProperty name=\"NETWORK_TITLE\" default=\"\"/>\n<visualProperty name=\"NETWORK_HEIGHT\" default=\"400.0\"/>\n</network>\n";

    public static String NODE_DEFAULTS = "<node>\n<dependency name=\"nodeCustomGraphicsSizeSync\" value=\"true\"/>\n<dependency name=\"nodeSizeLocked\" value=\"true\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_5\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"NODE_LABEL_FONT_FACE\" default=\"SansSerif.plain,plain,12\"/>\n<visualProperty name=\"NODE_LABEL_COLOR\" default=\"#000000\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_4\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_9\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_6\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_6\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_1\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_8\" default=\"0.0\"/>\n<visualProperty name=\"NODE_DEPTH\" default=\"0.0\"/>\n<visualProperty name=\"NODE_Z_LOCATION\" default=\"0.0\"/>\n<visualProperty name=\"NODE_NESTED_NETWORK_IMAGE_VISIBLE\" default=\"true\"/>\n<visualProperty name=\"NODE_BORDER_TRANSPARENCY\" default=\"255\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_6\" default=\"0.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_3\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"COMPOUND_NODE_PADDING\" default=\"10.0\"/>\n<visualProperty name=\"NODE_LABEL_TRANSPARENCY\" default=\"255\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_2\" default=\"0.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_1\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_LABEL_WIDTH\" default=\"200.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_2\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_1\" default=\"0.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_3\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_7\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"NODE_HEIGHT\" default=\"40.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_8\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"NODE_LABEL_FONT_SIZE\" default=\"12\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_5\" default=\"0.0\"/>\n<visualProperty name=\"NODE_BORDER_STROKE\" default=\"SOLID\"/>\n<visualProperty name=\"NODE_SELECTED_PAINT\" default=\"#FFFF00\"/>\n<visualProperty name=\"NODE_BORDER_PAINT\" default=\"#000000\"/>\n<visualProperty name=\"NODE_VISIBLE\" default=\"true\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_4\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_4\" default=\"0.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_7\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_TOOLTIP\" default=\"\"/>\n<visualProperty name=\"NODE_BORDER_WIDTH\" default=\"2.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_5\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_3\" default=\"0.0\"/>\n<visualProperty name=\"COMPOUND_NODE_SHAPE\" default=\"ROUND_RECTANGLE\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_7\" default=\"0.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_8\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_POSITION_9\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_WIDTH\" default=\"60.0\"/>\n<visualProperty name=\"NODE_TRANSPARENCY\" default=\"135\"/>\n<visualProperty name=\"NODE_X_LOCATION\" default=\"0.0\"/>\n<visualProperty name=\"NODE_SHAPE\" default=\"ELLIPSE\"/>\n<visualProperty name=\"NODE_SELECTED\" default=\"false\"/>\n<visualProperty name=\"NODE_LABEL_POSITION\" default=\"C,C,c,0.00,0.00\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_SIZE_9\" default=\"0.0\"/>\n<visualProperty name=\"NODE_Y_LOCATION\" default=\"0.0\"/>\n<visualProperty name=\"NODE_CUSTOMGRAPHICS_2\" default=\"org.cytoscape.ding.customgraphics.NullCustomGraphics,0,[ Remove Graphics ],\"/>\n<visualProperty name=\"NODE_LABEL\" default=\"\">\n<passthroughMapping attributeType=\"string\" attributeName=\"name\"/></visualProperty>";
    public static String NODE_END = "</node>";
    public static String EDGE = "<edge>\n<visualProperty name=\"EDGE_LABEL\" default=\"\"/>\n<visualProperty name=\"EDGE_TRANSPARENCY\" default=\"255\"/>\n<visualProperty name=\"EDGE_SELECTED\" default=\"false\"/>\n<visualProperty name=\"EDGE_TARGET_ARROW_SHAPE\" default=\"NONE\"/>\n<visualProperty name=\"EDGE_UNSELECTED_PAINT\" default=\"#404040\"/>\n<visualProperty name=\"EDGE_VISIBLE\" default=\"true\"/>\n<visualProperty name=\"EDGE_TARGET_ARROW_SELECTED_PAINT\" default=\"#FFFF00\"/>\n<visualProperty name=\"EDGE_SOURCE_ARROW_UNSELECTED_PAINT\" default=\"#000000\"/>\n<visualProperty name=\"EDGE_LABEL_FONT_FACE\" default=\"Dialog.plain,plain,10\"/>\n<visualProperty name=\"EDGE_LABEL_FONT_SIZE\" default=\"10\"/>\n<visualProperty name=\"EDGE_TOOLTIP\" default=\"\"/>\n<visualProperty name=\"EDGE_WIDTH\" default=\"2.0\"/>\n<visualProperty name=\"EDGE_CURVED\" default=\"true\"/>\n<visualProperty name=\"EDGE_LABEL_COLOR\" default=\"#000000\"/>\n<visualProperty name=\"EDGE_SOURCE_ARROW_SHAPE\" default=\"NONE\"/>\n<visualProperty name=\"EDGE_LABEL_WIDTH\" default=\"200.0\"/>\n<visualProperty name=\"EDGE_STROKE_SELECTED_PAINT\" default=\"#FF0000\"/>\n<visualProperty name=\"EDGE_SELECTED_PAINT\" default=\"#FF0000\"/>\n <visualProperty name=\"EDGE_BEND\" default=\"\"/>\n<visualProperty name=\"EDGE_LABEL_TRANSPARENCY\" default=\"255\"/>\n<visualProperty name=\"EDGE_STROKE_UNSELECTED_PAINT\" default=\"#848484\"/>\n<visualProperty name=\"EDGE_LINE_TYPE\" default=\"SOLID\"/>\n<visualProperty name=\"EDGE_PAINT\" default=\"#323232\"/>\n<visualProperty name=\"EDGE_TARGET_ARROW_UNSELECTED_PAINT\" default=\"#000000\"/>\n<visualProperty name=\"EDGE_SOURCE_ARROW_SELECTED_PAINT\" default=\"#FFFF00\"/>\n</edge>\n";

    public static String NODE_FILL_START = "<visualProperty name=\"NODE_FILL_COLOR\" default=\"#C80000\">";
    public static String PROP_END = "</visualProperty>\n";
    public static String MAP_START = "<discreteMapping attributeType=\"string\" attributeName=\"attribute\">\n";
    public static String MAP_END = "</discreteMapping>\n";
    public static String MAP_ENTRY_START = "<discreteMappingEntry value=";
    public static String MAP_ENTRY_ATT = " attributeValue=";
    public static String MAP_ENTRY_END = "/>\n";
    public static String XML_END = "</visualStyle>\n</vizmap>\n";

    public static String NODE_SIZE_START = "<visualProperty name=\"NODE_SIZE\">\n<continuousMapping attributeType=\"long\" attributeName=\"degree\">\n";
    public static String POINT_START = "<continuousMappingPoint lesserValue=";
    public static String GREATER = " greaterValue=";
    public static String EQUAL = " equalValue=";
    public static String ATT = " attrValue=";
    public static String POINT_END = "/>\n";
    public static String NODE_SIZE_END = "</continuousMapping>\n</visualProperty>\n";
    public static String NODE_SIZE_MIN = "\"50.0\"";
    public static String NODE_SIZE_MAX = "\"750.0\"";

    public static void changeMax(String max) {NODE_SIZE_MAX = max;}
    
    public static String[] COLOR_LIST = {"#ff001a" , "#660029" , "#990082" , "#8000ff" , "#0066ff" , "#005c99" , "#005766" , "#00cc99" , "#006605" , "#4c9900" , "#ccb800" , "#ff8c00" , "#993600" , "#660014" , "#ff008c" , "#ff00f2" , "#0f0099" , "#003d99" , "#00b3ff" , "#008a99" , "#00664d" , "#a6ff00" , "#998200" , "#995400" , "#661a00" , "#990026" , "#99005c" , "#a300cc" , "#000566" , "#0066cc" , "#004766" , "#00f2ff" , "#00ff99" , "#cccccc" , "#a3cc00" , "#ffbf00" , "#cc5c00" , "#cc2900" , "#ff004d" , "#ff00bf" , "#7a0099" , "#0033cc" , "#003366" , "#007399" , "#00ffe5" , "#009945" , "#999999" , "#526600" , "#664700" , "#662e00" , "#990800" , "#99003d" , "#66004d" , "#520066" , "#001f66" , "#0099ff" , "#00ccff" , "#00997a" , "#00cc33" , "#19ff00" , "#e5ff00" , "#cc8500" , "#ff5900"};
    
    public static void style() {
	// TODO Auto-generated method stub
	try {
	    File file_read = new File("result.csv");
	    FileReader fileReader = new FileReader(file_read);
	    File file_write = new File("result_style.xml");
	    if (!file_write.exists()) {
		file_write.createNewFile();
	    }
	    
	    FileWriter fw = new FileWriter(file_write.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    String line;
	    bufferedReader.readLine();
	    int count = 0;
	    long max = 0;
	    ArrayList<String> types = new ArrayList<String>();
	    while ((line = bufferedReader.readLine()) != null) {
		
		String[] temp = line.split(",");
		String s = temp[1];
		if (!types.contains(s)) {
		    types.add(s);
		    //System.out.println(s);
		}//if
		if (temp.length > 3) {
		    s = temp[6];
		    if (!types.contains(s)) {
			types.add(s);
			//System.out.println(s);
		    }//if
		}//if
		if (Long.parseLong(temp[2]) > max) max = Long.parseLong(temp[2]);
		if (Long.parseLong(temp[7]) > max) max = Long.parseLong(temp[7]);
		//bw.write(s  + "\n");
		//System.out.println(s  + "\n");
	    }//while

	    bw.write(XML_START);
	    bw.write(NETWORK);
	    bw.write(EDGE);
	    bw.write(NODE_DEFAULTS);
	    bw.write(NODE_FILL_START);
	    bw.write(MAP_START);
	    for (int i = 0; i < types.size(); ++i) {
		String temp = MAP_ENTRY_START;
		temp += '"' + COLOR_LIST[i % COLOR_LIST.length] + '"';
		temp += MAP_ENTRY_ATT;
		temp += '"' + types.get(i) + '"';
		temp += MAP_ENTRY_END;
		bw.write(temp);
	    }//for
	    bw.write(MAP_END);
	    bw.write(PROP_END);
	    bw.write(NODE_SIZE_START);
	    String temp = POINT_START + NODE_SIZE_MIN + GREATER + NODE_SIZE_MIN + EQUAL + NODE_SIZE_MIN + ATT + "\"1.0\"" + POINT_END; 
	    bw.write(temp);
	    String deg_max = '"' + Long.toString(max) + '"';
	    temp = POINT_START + NODE_SIZE_MAX + GREATER + NODE_SIZE_MAX + EQUAL + NODE_SIZE_MAX + ATT + deg_max + POINT_END + NODE_SIZE_END; 
	    bw.write(temp);
	    bw.write(NODE_END);
	    bw.write(XML_END);
	    fileReader.close();
	    bw.close();

	    //System.out.println("Done");

	} catch (IOException e) {
	    e.printStackTrace();
	}//try catch

    }//style

}//Style
