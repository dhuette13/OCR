package com.ocrapp;

import java.io.File;

import android.app.Application;
import android.os.Environment;


public class Glyphtic extends Application {
	private final String tesseractRoot = Environment.getExternalStorageDirectory() + "/tesseract/";

	@Override
	public void onCreate(){
		super.onCreate();
		
		/* Check for necessary directories */
		File tesseractDirectory = new File(tesseractRoot);
		if(!tesseractDirectory.exists()){
			tesseractDirectory.mkdirs();
		}

		File picturesDirectory = new File(tesseractRoot + "pictures/");
		if(!picturesDirectory.exists()){
			picturesDirectory.mkdirs();
		}

		File cameraDirectory = new File(tesseractRoot + "camera/");
		if(!cameraDirectory.exists()){
			System.out.println("Making Camera Directory");
			cameraDirectory.mkdirs();
		}

		File outputDirectory = new File(tesseractRoot + "output/");
		if(!outputDirectory.exists()){
			System.out.println("Making Output Directory");
			outputDirectory.mkdirs();
		}
		
		System.out.println("IN GLYPHTIC APPLICATION");
	}
}
