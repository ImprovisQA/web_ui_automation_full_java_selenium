package com.helpsystems.common.processors;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import com.helpsystems.common.processors.AttachmentProcessor;

public class HtmlProcessor implements AttachmentProcessor {

	@Override
	public void validatePages(InputStream is, List<Integer> pagesList) throws Exception {
		throw new Exception("Unsupported operation for Html document");

	}

	@Override
	public void validateData(InputStream is, List<?> content) throws Exception {
		if (content != null && !content.isEmpty() && content.get(0) instanceof String) {
			@SuppressWarnings("unchecked")
			List<String> contentList = new ArrayList<>((List<String>) content);
			String text = Jsoup.parse(is, null, "").text();
			for (int i = 0; i < contentList.size(); i++) {
				if (text.contains(contentList.get(i))) {
					contentList.remove(i);
					i--;
				}
			}
			if (!contentList.isEmpty()) {
				throw new Exception("Attachement is invalid\n" + contentList.toString() + " not found");
			}
		} else {
			throw new Exception("Unsupported operation for Html document");
		}

	}

}
