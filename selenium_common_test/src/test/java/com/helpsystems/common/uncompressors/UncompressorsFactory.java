package com.helpsystems.common.uncompressors;

import java.util.HashMap;
import java.util.Map;

import com.helpsystems.common.uncompressors.UncompressSevenZ;
import com.helpsystems.common.uncompressors.UncompressZip;
import com.helpsystems.common.uncompressors.Uncompressor;

public class UncompressorsFactory {

	private final static Map<CompressType, Uncompressor> UNCOMPRESSORS_MAP = new HashMap<>();
	
	static {
		UNCOMPRESSORS_MAP.put(CompressType.ZIP, new UncompressZip());
		UNCOMPRESSORS_MAP.put(CompressType.SEVEN_Z, new UncompressSevenZ());
	}

	public static Uncompressor getUncompressor(CompressType type) {
		return UNCOMPRESSORS_MAP.get(type);
	}

	public static enum CompressType {
		ZIP, SEVEN_Z
	}
}
