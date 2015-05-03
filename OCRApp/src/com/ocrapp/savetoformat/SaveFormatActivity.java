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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ocrapp.R;
import com.ocrapp.startscreen.StartActivity;

public class SaveFormatActivity extends Activity {

	private String importedText;
	private final String outputDirectory = Environment.getExternalStorageDirectory() + "/tesseract/output/";
	private EditText fileInput;
	private Button saveButton;
	private Button continueButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);

		Bundle b = getIntent().getExtras();
		importedText = b.getString("text");
		fileInput = (EditText) findViewById(R.id.fileName);
		
		saveButton= (Button) findViewById(R.id.saveButton);
		continueButton = (Button) findViewById(R.id.continueButton);
		
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				convertText(v);
			}
		});
		
		continueButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SaveFormatActivity.this, StartActivity.class);
				startActivity(i);
			}
			
		});
	}

	
	public void convertText(View view){
		PrintWriter print;
		Toast notify;
		String filename = fileInput.getText().toString();
		if(!filename.equals("")){
			try {
				filename = fileInput.getText().toString();
				print = new PrintWriter(new File(outputDirectory + filename + ".txt"));
				print.println(importedText);
				print.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			notify = Toast.makeText(this, "File " + filename + ".txt saved in txt format", Toast.LENGTH_LONG);
			notify.show();
		}
		else {
			notify = Toast.makeText(this, "Filename cannot be empty", Toast.LENGTH_LONG);
			notify.show();
			
		}
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
}


