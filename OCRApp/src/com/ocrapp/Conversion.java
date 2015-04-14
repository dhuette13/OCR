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
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.googlecode.tesseract.android.TessBaseAPI;

public class Conversion extends Activity {

	// Progress Bar Variables
	private ProgressBar mProgress;

	// Conversion Variables
	final TessBaseAPI baseAPI = new TessBaseAPI();
	String TESSBASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/tesseract/";
	Bitmap newBitmap; 
	String DEFAULT_LANGUAGE; 
	Bundle extras; 
	String text; 
	ProgressDialog dialog;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		System.out.println("In Conversion Activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversion);

		dialog = new ProgressDialog(this);
		if (savedInstanceState == null) {
			extras = getIntent().getExtras();
			if(extras == null) {
				DEFAULT_LANGUAGE = null;
			} else {
				DEFAULT_LANGUAGE = extras.getString("lang");
			}
		} else {
			DEFAULT_LANGUAGE= (String) savedInstanceState.getSerializable("lang");
			System.out.println("DEFAULT LANGUAGE: " + DEFAULT_LANGUAGE);
		}

		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.show();
		new ConversionTask().execute();


		//		mProgress = (ProgressBar) findViewById(R.id.progressBar1);
		//		mProgress.setVisibility(View.VISIBLE);

//		new Thread(new Runnable() {
//			public void run() {
//
//		
//
//			}
//		}).start(); // thread example for progress bar

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

	private class ConversionTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			dialog.setTitle("Reading Image...");
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			newBitmap = BitmapFactory.decodeFile(TESSBASE_PATH + "modimage.png", options);

			dialog.setTitle("Converting...");
			baseAPI.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
			baseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
			baseAPI.setImage(newBitmap);

			newBitmap.recycle();
			text = baseAPI.getUTF8Text();


			baseAPI.end();

			dialog.dismiss();
			//				mProgress.setVisibility(View.GONE);

			System.out.println(text);

			Intent i = new Intent(Conversion.this, TextPreview.class);
			i.putExtra("text", text);
			startActivity(i);
			
			return null;
		}

//		protected void onProgressUpdate(Integer... progress) {
//		}
//
//		protected void onPostExecute(Long result) {
//		}

	}
}
