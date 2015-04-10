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

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

public class Conversion extends Activity {

	final TessBaseAPI baseAPI = new TessBaseAPI();
	String TESSBASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/tesseract/";
	
	Bitmap newBitmap;
	String DEFAULT_LANGUAGE; 
	Bundle extras;
	String text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversion);
		
		Intent intent = getIntent();
		newBitmap = (Bitmap) intent.getParcelableExtra("image");
		
		if (savedInstanceState == null) {
		    extras = getIntent().getExtras();
		    if(extras == null) {
		    	DEFAULT_LANGUAGE = null;
		    } else {
		    	DEFAULT_LANGUAGE = extras.getString("lang");
		    }
		} else {
			DEFAULT_LANGUAGE= (String) savedInstanceState.getSerializable("lang");
		}
		
		baseAPI.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
		baseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
		baseAPI.setImage(newBitmap);
		text = baseAPI.getUTF8Text();
		// return text 
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
