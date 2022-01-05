package com.helpsystems.common.processors;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.helpsystems.common.processors.AttachmentProcessor;

public class XmlProcessor implements AttachmentProcessor {
	
	private final String type;
	
	public XmlProcessor(String type){
		this.type = type;
	}

	@Override
	public void validatePages(InputStream is, List<Integer> pagesList) throws Exception {
		throw new Exception("Unsupported operation for Xml document");

	}

	@Override
	public void validateData(InputStream is, List<?> content) throws Exception {
		if (content != null && !content.isEmpty() && content.get(0) instanceof Pair && ((Pair<?, ?>) content.get(0)).getKey() instanceof Pair) {
			@SuppressWarnings("unchecked")
			List<Pair<Pair<Integer, String>, String>> contentList = new ArrayList<>((List<Pair<Pair<Integer, String>, String>>) content);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList root = doc.getElementsByTagName("viewfmt");

			for (int i = 0; i < contentList.size(); i++) {
				Pair<Pair<Integer, String>, String> pair = contentList.get(i);
				Pair<Integer,String> index = pair.getKey();
				Integer left = index.getLeft();
				if (left < root.getLength()) {
					Element element = (Element) root.item(left);
					String textContent = null;
					if (type.equals("xml")) {
						Element eElement = (Element) element.getElementsByTagName(index.getRight()).item(0);
						if (eElement != null) {
							textContent = eElement.getTextContent();
						}
					} else {
						textContent = element.getAttribute(index.getRight());
					}
					if ( pair.getValue().equals(textContent)) {
						contentList.remove(i);
						i--;
					}
				}
			}
			if(!contentList.isEmpty()) {
				StringBuilder errorMessage = new StringBuilder("Attachement is invalid\n");
				for (Pair<Pair<Integer, String>, String> pair : contentList) {
					Pair<Integer, String> index = pair.getKey();
					errorMessage.append("In Element N").append(index.getLeft()).append(" column '").append(index.getRight()).append("' with value '").append(pair.getValue()).append("' not found\n");
				}
				throw new Exception(errorMessage.toString());
			}
		} else {
			throw new Exception("Unsupported operation for Xml document");
		}

	}
	
}
