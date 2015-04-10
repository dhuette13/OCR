package com.ocrapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import br.com.thinkti.android.filechooser.FileChooser;

import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends Activity {
	private static final String TESSBASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/tesseract/";
	private static final String DEFAULT_LANGUAGE = "eng";

	private EditText textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		textView = (EditText) findViewById(R.id.editText1);
//		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(cameraIntent, 0);
		
		Intent intent = new Intent(this, FileChooser.class);
		ArrayList<String> extensions = new ArrayList<String>();
		extensions.add(".jpg");
		extensions.add(".bmp");
		extensions.add(".png");
		intent.putStringArrayListExtra("filterFileExtension", extensions);
		startActivityForResult(intent, 1);
		
		
//		final String inputText = "Test text";
//		final Bitmap bmp = getTextImage(inputText, 640, 480);
//		final TessBaseAPI baseApi = new TessBaseAPI();
//		baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
//		baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
//		baseApi.setImage(bmp);
//		String text = baseApi.getUTF8Text();
//		
//		textView.setText(text);
//		baseApi.end();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if ((requestCode == 1) && (resultCode == -1)) {
	    	Bitmap bmp = null;
	        String fileSelected = data.getStringExtra("fileSelected");
	        System.out.println("SELECTED FILE: " + fileSelected);
//	        
//	        Intent imagePreprocessor = new Intent(this, ImagePreprocessor.class);
//	        imagePreprocessor.putExtra("file", fileSelected);
//	        startActivity(imagePreprocessor);
	        
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
	        try {
	            bmp = BitmapFactory.decodeStream(new FileInputStream(fileSelected), null, options);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	        bmp = BitmapFactory.decodeFile(fileSelected, options);
	        
	        if(bmp != null){
	        	textView.setText("Initializing Tesesseract...");
	        	final TessBaseAPI baseApi = new TessBaseAPI();
	        	baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
	        	baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
	        	baseApi.setImage(bmp);
	        	String text = baseApi.getUTF8Text();
	        	System.out.println(text);
	        	textView.append(text);
	        	
	        	baseApi.end();
	        }
	        else {
	        	textView.setText("Error reading image file: " + fileSelected);
	        }
	    }            
	}
}
