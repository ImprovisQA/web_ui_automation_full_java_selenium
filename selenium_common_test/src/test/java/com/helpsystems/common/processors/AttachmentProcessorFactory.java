package com.helpsystems.common.processors;

import java.util.HashMap;
import java.util.Map;

import com.helpsystems.common.processors.AttachmentProcessor;
import com.helpsystems.common.processors.ExcelProcessor;
import com.helpsystems.common.processors.HtmlProcessor;
import com.helpsystems.common.processors.PdfProcessor;
import com.helpsystems.common.processors.RtfProcessor;
import com.helpsystems.common.processors.TxtProcessor;
import com.helpsystems.common.processors.XmlProcessor;

public class AttachmentProcessorFactory {

	private static final Map<String,AttachmentProcessor> attachmentProcessorsMap = new HashMap<>();
	
	static {
		attachmentProcessorsMap.put("pdf", new PdfProcessor());
		attachmentProcessorsMap.put("txt", new TxtProcessor());
		attachmentProcessorsMap.put("rtf", new RtfProcessor());
		attachmentProcessorsMap.put("html", new HtmlProcessor());
		attachmentProcessorsMap.put("xls", new ExcelProcessor("xls"));
		attachmentProcessorsMap.put("xlsx", new ExcelProcessor("xlsx"));
		attachmentProcessorsMap.put("xml", new XmlProcessor("xml"));
		attachmentProcessorsMap.put("xml1", new XmlProcessor("xml1"));
	}
	
	public static AttachmentProcessor getProcessor(String attachmentType){
		return attachmentProcessorsMap.get(attachmentType);
	}

}
