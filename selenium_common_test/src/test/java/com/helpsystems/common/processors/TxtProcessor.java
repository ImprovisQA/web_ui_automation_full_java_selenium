package com.helpsystems.common.processors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import com.helpsystems.common.processors.AttachmentProcessor;

public class TxtProcessor implements AttachmentProcessor {

	@Override
	public void validatePages(InputStream is, List<Integer> pagesList) throws Exception {
		try(BufferedReader in = new BufferedReader(new InputStreamReader(is))){
			Pattern p = Pattern.compile("(?:Page\\s*)([0-9]+)");
			String line;
			List<Integer> extraPages = new ArrayList<>();
			pagesList = new ArrayList<>(pagesList);
			while((line = in.readLine()) != null){
				Matcher matcher = p.matcher(line);
				if (matcher.find()) {
					Integer pageNum = Integer.valueOf(matcher.group(1));
					if (pagesList.contains(pageNum)) {
						pagesList.remove(pageNum);
					} else {
						extraPages.add(pageNum);
					}
				}
			}
			StringBuilder errorMessage = new StringBuilder();
			if (!pagesList.isEmpty()) {
				errorMessage.append(String.format("Pages %s not found\n", pagesList.toString()));
			}
			if (!extraPages.isEmpty()) {
				errorMessage.append(String.format("Found extra pages %s\n", extraPages.toString()));
			}
			if (!errorMessage.toString().isEmpty()) {
				errorMessage.insert(0, "Attachement is invalid\n");
				throw new Exception(errorMessage.toString());
			}
		}

	}

	@Override
	public void validateData(InputStream is, List<?> content) throws Exception {
		if (content != null && !content.isEmpty()) {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
				if (content.get(0) instanceof String) {
					@SuppressWarnings("unchecked")
					List<String> contentList = new ArrayList<>((List<String>) content);
					validateData(contentList, in);
				} else if(content.get(0) instanceof Pair) {
					@SuppressWarnings("unchecked")
					List<Pair<Integer, String>> contentList = new ArrayList<>((List<Pair<Integer, String>>) content);
					validateDataByPages(contentList, in);
				} else {
					throw new Exception("Unsupported operation for Txt document");
				}
			}
		}
		
	}

	private void validateData(List<String> contentList, BufferedReader in) throws IOException, Exception {
		String line;
		while ((line = in.readLine()) != null) {
			for (int i = 0; i < contentList.size(); i++) {
				if (line.contains(contentList.get(i))) {
					contentList.remove(i);
					i--;
				}
			}
		}
		if (!contentList.isEmpty()) {
			throw new Exception("Attachement is invalid\n" + contentList.toString() + " not found");
		}
	}
	
	private void validateDataByPages(List<Pair<Integer, String>> content, BufferedReader in) throws IOException, Exception {
		Pattern p = Pattern.compile("(?:Page\\s*)([0-9]+)");
		String line;
		int pageN = 0;
		while((line = in.readLine()) != null){
			Matcher matcher = p.matcher(line);
			if (matcher.find()) {
				pageN = Integer.valueOf(matcher.group(1));
			}
			if(pageN != 0) {
				for (int i = 0; i < content.size(); i++) {
					Pair<Integer, String> pair = content.get(i);
					if(pair.getKey() == pageN && line.contains(pair.getValue())) {
						content.remove(i);
						i--;
					}
				}
			}
		}
		if(!content.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder("Attachement is invalid\n");
			for (Pair<Integer, String> pair : content) {
				errorMessage.append("In Page N").append(pair.getKey()).append(" text '").append(pair.getValue()).append("' not found\n");
			}
			throw new Exception(errorMessage.toString());
		}
	}

}
