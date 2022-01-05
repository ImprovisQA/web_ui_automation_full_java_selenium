package com.helpsystems.common.processors;

import java.io.InputStream;
import java.util.List;

public interface AttachmentProcessor {

	void validatePages(InputStream is, List<Integer> pagesList) throws Exception;
	
	void validateData(InputStream is, List<?> content) throws Exception;
}
