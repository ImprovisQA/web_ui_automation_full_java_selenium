package com.helpsystems.common.processors;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import com.helpsystems.common.processors.AttachmentProcessor;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PdfProcessor implements AttachmentProcessor {


	@Override
	public void validatePages(InputStream is, List<Integer> pagesList) throws Exception {
		pagesList = new ArrayList<>(pagesList);
		PdfReader pdfReader = new PdfReader(is);
		Pattern p = Pattern.compile("(?:Page\\s*)([0-9]+)");
		try {
			int numberOfPages = pdfReader.getNumberOfPages();
			List<Integer> extraPages = new ArrayList<>();
			for (int j = 1; j <= numberOfPages; j++) {
				String textFromPage = PdfTextExtractor.getTextFromPage(pdfReader, j);
				Matcher matcher = p.matcher(textFromPage);
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
		} finally {
			pdfReader.close();
		}
	}

	@Override
	public void validateData(InputStream is, List<?> content) throws Exception {
		if (content != null && !content.isEmpty()) {
			PdfReader pdfReader = new PdfReader(is);
			try {
				if (content.get(0) instanceof String) {
					@SuppressWarnings("unchecked")
					List<String> contentList = new ArrayList<>((List<String>) content);
					validateData(contentList, pdfReader);

				} else if (content.get(0) instanceof Pair) {
					@SuppressWarnings("unchecked")
					List<Pair<Integer, String>> contentList = new ArrayList<>((List<Pair<Integer, String>>) content);
					validateDataByPages(contentList, pdfReader);
				} else {
					throw new Exception("Unsupported operation for Pdf document");
				}
			} finally {
				pdfReader.close();
			}
		}
	}
	
	private void validateDataByPages(List<Pair<Integer, String>> content, PdfReader pdfReader) throws IOException, Exception {
		Pattern p = Pattern.compile("(?:Page\\s*)([0-9]+)");
		int numberOfPages = pdfReader.getNumberOfPages();
		for (int j = 1; j <= numberOfPages; j++) {
			String textFromPage = PdfTextExtractor.getTextFromPage(pdfReader, j);
			Matcher matcher = p.matcher(textFromPage);
			if (matcher.find()) {
				Integer pageNum = Integer.valueOf(matcher.group(1));
				for (int i = 0; i < content.size(); i++) {
					Pair<Integer, String> pair = content.get(i);
					if(pageNum.equals(pair.getKey()) && textFromPage.contains(pair.getValue())){
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

	private void validateData(List<String> contentList, PdfReader pdfReader) throws IOException, Exception {
		int numberOfPages = pdfReader.getNumberOfPages();
		for (int j = 1; j <= numberOfPages; j++) {
			String textFromPage = PdfTextExtractor.getTextFromPage(pdfReader, j);
			for (int i = 0; i < contentList.size(); i++) {
				if (textFromPage.contains(contentList.get(i))) {
					contentList.remove(i);
					i--;
				}
			}
		}
		if (!contentList.isEmpty()) {
			throw new Exception("Attachement is invalid\n" + contentList.toString() + " not found");
		}
	}

}
