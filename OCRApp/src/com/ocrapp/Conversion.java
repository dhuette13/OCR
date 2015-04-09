package com.ocrapp;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.graphics.Bitmap;
import android.os.Environment;

public class Conversion {

	private static final String TESSBASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/tesseract/";
	final String inputText = "Test text";
	private static Bitmap bmp;
	private static final String DEFAULT_LANGUAGE = "eng";
	
	public static void setImage(Bitmap i){
		bmp = i;
	}
	
	public static String convertBitmap(){
		final TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
		baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
		baseApi.setImage(bmp);
		String text = baseApi.getUTF8Text();
		
		return text;
	}
	
}
