package prcatie_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MidTerm {
	private String m_path;
	private String m_query;
	public MidTerm(String path, String query) {
		m_path = path;
		m_query = query;
	}
	
	public void mid() throws Exception {

		
		String dvdata[] = m_query.toString().split(" ");
		String d[] = new String[5];
		 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

	      docFactory.setIgnoringElementContentWhitespace(true);

	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	      org.w3c.dom.Document doc = docBuilder.newDocument();
	      Element docs = doc.createElement("docs");
	      String indexLocation = m_path;
	      File dir = new File(indexLocation);
	      File file = new File(m_path);
	      org.jsoup.nodes.Document xml = Jsoup.parse(dir, "UTF-8");
	      org.w3c.dom.Document otdoc = docBuilder.parse(file);
	      NodeList nList = otdoc.getElementsByTagName("doc");
	      for(int b = 0; b < 5; b++) {
	             Node nNode = nList.item(b);
	             if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	             Element eElement = (Element) nNode;

	             d[b] = eElement.getElementsByTagName("body").item(0).getTextContent();
	             }
	      }
	      //30문장씩 짜르고
	      String[] tmp = new String[1000];
	      int as = 0;
	      for(int a = 0; a <d.length; a++) {
	    	  int stnum = 0;
	    	  int lnum = 29;
	    	  for(int c = 0; c < (d[a].length())/30;c++) {
	    		  tmp[as] = d[a].substring(stnum,lnum);
	    		  stnum += 30;
	    		  lnum += 30;
	    		  as++;
	    	  }
	      }
	      int tmp2 = 0;

	         StringBuilder sb = new StringBuilder();
	      while(tmp[tmp2] == NULL) {
	    	  KeywordExtractor ke = new KeywordExtractor();
	             KeywordList k1 = ke.extractKeyword(tmp[tmp2], true);
	             for(int a = 0;a<k1.size();a++) {
	                Keyword kwrd = k1.get(a);
	                sb.append(kwrd.getString() + ":" + kwrd.getCnt() + "#");
	             }
	             //저거다시 나누고
	      } // 한문장마다 검사하고
	      
	}
}
