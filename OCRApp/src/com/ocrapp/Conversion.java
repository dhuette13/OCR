/*
 * Getting Strings (Intent)
 * http://stackoverflow.com/questions/5265913/
 * how-to-use-putextra-and-getextra-for-string-data
 * 
 * Passing Bitmaps (Intent)
 * http://stackoverflow.com/questions/2459524/
 * how-can-i-pass-a-bitmap-object-from-one-activity-
 * to-another/2459624#2459624
 * 
 */

package com.ocrapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

import com.googlecode.tesseract.android.TessBaseAPI;

public class Conversion extends Activity {

	// Conversion Variables
	final TessBaseAPI baseAPI = new TessBaseAPI();
	String TESSBASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/tesseract/";
	Bitmap newBitmap; 
	String lang; 
	Bundle extras; 
	String text; 

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		System.out.println("In Conversion Activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversion);
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				lang = (String) getIntent().getExtras().getString("lang");
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				newBitmap = BitmapFactory.decodeFile(TESSBASE_PATH + "modimage.png", options);
				
				baseAPI.init(TESSBASE_PATH, lang);
				baseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
				baseAPI.setImage(newBitmap);
				
				newBitmap.recycle();
				text = baseAPI.getUTF8Text();
				
				baseAPI.end();
				
				System.out.println(text);
				
				Intent i = new Intent(Conversion.this, TextPreview.class);
				i.putExtra("text", text);
				startActivity(i);
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversion, menu);
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
}
