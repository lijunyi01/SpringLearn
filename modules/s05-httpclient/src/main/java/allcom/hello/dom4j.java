package allcom.hello;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;




public class dom4j {
	
	public static Map<String, String> dom4jvaluebyStringxml(String xml){
		Map<String, String> map = new HashMap<String, String>();
		
		SAXReader saxReader = new SAXReader();
		InputStream in = null;
		Document document = null;
		Element element = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			document = saxReader.read(in);
			element = document.getRootElement();
			List<Element> list = element.elements();
			if(list.size()>0) getElements(list, map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				document.clone();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static void getElements(List<Element> list,Map<String, String> map){
		String TextTrim = "";
		for (Element elements : list) {
			if("".equals(elements.getTextTrim())){
				List<Element> list_e = elements.elements();
				getElements(list_e, map);
			}else{
				if("protocol".equals(elements.getName())){
					TextTrim = elements.getTextTrim();
					map.put(TextTrim, "");
				}else if("location".equals(elements.getName())){
					map.put(TextTrim, elements.getTextTrim());
				}else if("PostBack".equals(elements.getName())){
					map.put(elements.getName(), elements.getTextTrim());
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><requesttokenchoices xmlns=\"http://citrix.com/delivery-services/1-0/auth/requesttokenchoices\"><choices><choice><protocol>CitrixFederation</protocol><location>http://cpicctxdc01.cpic.local/Citrix/Authentication/CitrixFederation/Authenticate</location></choice><choice><protocol>ExplicitForms</protocol><location>http://cpicctxdc01.cpic.local/Citrix/Authentication/ExplicitForms/Start</location></choice></choices></requesttokenchoices>";
		dom4j.dom4jvaluebyStringxml(xml);
	}
	
	
}
