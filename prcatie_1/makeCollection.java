package prcatie_1;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.w3c.dom.Element;

/**
 * 2주차 실습 코드
 * 
 * 주어진 5개의 html 문서를 전처리하여 하나의 xml 파일을 생성하세요. 
 * 
 * input : data 폴더의 html 파일들
 * output : collection.xml 
 */

public class makeCollection {
	
	private String data_path;
	private String output_file = "C:\\Users\\이민효\\Desktop\\opensw\\SimpleIR\\prcatie_1\\src\\prcatie_1\\collection.xml";
	
	public makeCollection(String path) throws Exception {
		this.data_path = path;
	}
	
	public void makeXml() throws Exception{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	      docFactory.setIgnoringElementContentWhitespace(true);

	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	      org.w3c.dom.Document doc = docBuilder.newDocument();
	      
	      Element docs = doc.createElement("docs");
	      doc.appendChild(docs);
	      //html경로
	      File dir = new File(data_path);
	      File[] files = dir.listFiles();
	      
	      for(int i = 0; i < 5; i++) {
	      org.jsoup.nodes.Document html = Jsoup.parse(files[i], "UTF-8");
	         
	      String Htitle = html.title();
	      String Hbody = html.body().text();
	      String to = Integer.toString(i);

	      Element code = doc.createElement("doc");
	      docs.appendChild(code);
	      code.setAttribute("id",to);
	      
	      Element title = doc.createElement("title");
	      title.appendChild(doc.createTextNode(Htitle));
	      code.appendChild(title);
	      
	      Element body = doc.createElement("body");
	      body.appendChild(doc.createTextNode(Hbody));
	      code.appendChild(body);
	      }
	      
	      //XML 파일로쓰기
	      TransformerFactory tff = TransformerFactory.newInstance();
	      Transformer tf = tff.newTransformer();
	      tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	      
	      DOMSource so = new DOMSource(doc);
	      //저장경로
	      StreamResult result = new StreamResult(new FileOutputStream(new File(output_file)));
	      
	      tf.transform(so, result);
	      
		System.out.println("2주차 실행완료");
	}
	
}