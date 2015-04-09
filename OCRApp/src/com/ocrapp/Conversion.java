package com.ocrapp;

import android.app.Activity;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class Conversion {

	private static final String TESSBASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/tesseract/";
	final String inputText = "Test text";
	private static Bitmap image;
	private static final String DEFAULT_LANGUAGE = "eng";
	
	public static void setImage(Bitmap i){
		image = i;
	}
	
	public static String convertBitmap(){
		final TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
		baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
		baseApi.setImage(image);
		String text = baseApi.getUTF8Text();
		
		return text;
	}
	
}
