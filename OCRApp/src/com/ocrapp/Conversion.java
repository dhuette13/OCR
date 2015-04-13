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

import java.io.File;
import java.io.FileOutputStream;

import savetoformat.SaveFormatActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

public class Conversion extends Activity {

	// Progress Bar Variables
	private static final int PROGRESS = 0x1;
	private ProgressBar mProgress;
	private int mProgressStatus = 0;
	private Handler mHandler = new Handler();
	
	// Conversion Variables
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
		
		//mProgress = (ProgressBar) findViewById(R.id.progressBar1);
		//mProgress.setVisibility(View.VISIBLE);
		
		// Starting Progress Bar
		//mProgress.setIndeterminate(true);
		
		// Still need to change the thread to go with the indeterminate progress bar -> no need for a while loop
		/*new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = doWork();

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
                
                mProgress.setVisibility(View.GONE);
            }
        }).start();*/ // thread example for progress bar
		

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
		
		
		baseAPI.setDebug(true);
		baseAPI.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
		baseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
		baseAPI.setImage(newBitmap);
		text = baseAPI.getUTF8Text();
		baseAPI.end();
		
		
		System.out.println(text);
		
		// return text for text preview
		
		// Pull progress bar out manually
		// mProgress.setVisibility(View.GONE);
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
	public void todoOnclick(View view){
		Intent i = new Intent(this, SaveFormatActivity.class);
		i.putExtra("item", text);
		startActivity(i);
	//	finish();

	}
}
