package prcatie_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class makeIpost {
	private String input_file;
	private String output_file = "C:\\Users\\이민효\\Desktop\\opensw\\SimpleIR\\prcatie_1\\src\\prcatie_1\\index.post";
	
	
	public makeIpost(String file) throws Exception{
		input_file = file;
	}
	
	
	public void makePost() throws Exception{
		FileOutputStream filestream = new FileOutputStream(output_file);
		
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(filestream);
	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	      
	      docFactory.setIgnoringElementContentWhitespace(true);

	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	      org.w3c.dom.Document doc = docBuilder.newDocument();
	      Element docs = doc.createElement("docs");
	      HashMap dfx = new HashMap();
	      String indexLocation = input_file;
	      File dir = new File(indexLocation);
	      File file = new File(input_file);
	      org.jsoup.nodes.Document xml = Jsoup.parse(dir, "UTF-8");
	      org.w3c.dom.Document otdoc = docBuilder.parse(file);
	      NodeList nList = otdoc.getElementsByTagName("doc");
	      HashMap wholedata = new HashMap();

	      HashMap resulthash = new HashMap();
	      for(int b = 0; b < 5; b++) {
	             Node nNode = nList.item(b);
	             if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	             Element eElement = (Element) nNode;
	             
	             String test = eElement.getElementsByTagName("body").item(0).getTextContent();
	             String tagdata[] = test.split("#");
	             int i = 0;
	             while(i < tagdata.length) {
	            	 String sdata = tagdata[i];
	            	 String inputstr[] = sdata.split(":");
	            	 resulthash.put(inputstr[0], " ");
	            	 if(dfx.containsKey(inputstr[0])) {
	            		 int count = (int) dfx.get(inputstr[0]);
	            		 dfx.put(inputstr[0], count +1);
	            	 }
	            	 else {
	            		 dfx.put(inputstr[0], 1);
	            	 }
	            	 wholedata.put(inputstr[0],inputstr[1]); //여기서 value값을 ID값까지 넣어주면 되나??
	            	 i++;
	             	}
	             
	             }
	      }//모든데이터가 들어있는 해시맵
	      
	      for(int b = 0; b < 5; b++) {
	             Node nNode = nList.item(b);
	             HashMap hashmap = new HashMap();
	             if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	             Element eElement = (Element) nNode;
	             
	             String test = eElement.getElementsByTagName("body").item(0).getTextContent();
	             String tagdata[] = test.split("#");
	             int i = 0;
	             while(i < tagdata.length) {

		             Iterator<String> it = wholedata.keySet().iterator();
	            	 String sdata = tagdata[i];
	            	 String inputstr[] = sdata.split(":");
	            	  
	            	 hashmap.put(inputstr[0],inputstr[1]); //여기서 value값을 ID값까지 넣어주면 되나??
	            	
	            	 i++;
	            	 
	             	}
	             Iterator<String> it = dfx.keySet().iterator();
	            
	             while(it.hasNext()) {
	            	 double weight = 0.00;
	            	 String dkey = it.next();
	            	 int dvalue = (int)dfx.get(dkey);
	            	 double ddvalue = (double)dvalue;

		             Iterator<String> it2 = hashmap.keySet().iterator();
	            	 while(it2.hasNext()) {
	            	 String tkey = it2.next();
	            	 String tvalue = (String)hashmap.get(tkey);
	            	 double dtvalue = Double.valueOf(tvalue);
	            	 if(hashmap.containsKey(dkey)) {
	            		 weight = dtvalue * Math.log(5/ddvalue);
	            	 	}
	            	 }
	            	 String str = (String)resulthash.get(dkey);
	            	 weight = (Math.round(weight*100)/100.0);
	            	 str += b + ":" + weight + " ";
	            	 resulthash.put(dkey, str);
	             }
	             
	             }
	      }
	      
	      
	    
   	   objectOutputStream.writeObject(resulthash);
   	   objectOutputStream.close();
	    
	}
	public void ReadPost() throws Exception{
		FileInputStream filestream = new FileInputStream(output_file);
		
		ObjectInputStream objectinputStream = new ObjectInputStream(filestream);
		Object object = objectinputStream.readObject();
		objectinputStream.close();
		HashMap resulthash = (HashMap)object;
		Iterator<String> it = resulthash.keySet().iterator();
 	      while(it.hasNext()) {
 	    	  String key = it.next();
 	    	  String value4 = (String)resulthash.get(key);
 	    	  System.out.println( key + " > " + value4);
 	      }
	}
}
