package com.ocrapp.savetoformat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ocrapp.R;
import com.ocrapp.startscreen.StartActivity;



public class SaveFormatActivity extends Activity {

	String importedText="Hello World";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
	//	Bundle b = getIntent().getExtras();
//		importedText = b.getString("item");
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
		int id = item.getItemId();
		if (id == R.id.action_help) {
			
			Intent i = new Intent(this, Help.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void convertpdf(View view){
		FileOutputStream outputstream;
		try {
			outputstream=openFileOutput("ocr.pdf",Context.MODE_PRIVATE);
			outputstream.write(importedText.getBytes());
			outputstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast giveMessage=Toast.makeText(this, "File saved in pdf format", Toast.LENGTH_LONG);
		giveMessage.show();
	}
	
	public void converttxt(View view){
		FileOutputStream outputstream;
		try {
			outputstream=openFileOutput("ocr.txt",Context.MODE_PRIVATE);
			outputstream.write(importedText.getBytes());
			outputstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast giveMessage=Toast.makeText(this, "File saved in txt format", Toast.LENGTH_LONG);
		giveMessage.show();
	}
	
	public void gotostart(MenuItem item){

			Intent i = new Intent(this, StartActivity.class);
			startActivity(i);
			
	} 
		
}


