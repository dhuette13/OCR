package com.ocrapp.savetoformat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ocrapp.R;
import com.ocrapp.startscreen.StartActivity;

public class SaveFormatActivity extends Activity {

	private String importedText;
	private final String output = Environment.getExternalStorageDirectory() + "/tesseract/output.txt";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);

		Bundle b = getIntent().getExtras();
		importedText = b.getString("text");
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_format, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
	
	public void converttxt(View view){
		PrintWriter print;
		try {
			
			print = new PrintWriter(new File(output));
			print.println(importedText);
			print.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Toast giveMessage = Toast.makeText(this, "File saved in txt format", Toast.LENGTH_LONG);
		giveMessage.show();
	}
	
	
	public void gotostart(View view){
			Intent i = new Intent(this, StartActivity.class);
			startActivity(i);	
	} 
		
}


