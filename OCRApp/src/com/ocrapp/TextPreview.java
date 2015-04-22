package com.ocrapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ocrapp.imageui.ImagePreprocessor;
import com.ocrapp.savetoformat.SaveFormatActivity;

public class TextPreview extends Activity {
	
	private EditText textView;
	private String text;
	private final String modifiedImageDirectory = Environment.getExternalStorageDirectory() + "/tesseract/modimage.png";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_preview);
		textView = (EditText) findViewById(R.id.editText1);
		
		text = getIntent().getExtras().getString("text");
		textView.setText(text);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text_preview, menu);
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
		else if(id == R.id.OK){
			Intent i = new Intent(this, SaveFormatActivity.class);
			i.putExtra("text", textView.getText());
			startActivity(i);
			return true;
		}
		else if(id == R.id.cancel){
			Intent i = new Intent(this, ImagePreprocessor.class);
			i.putExtra("file", modifiedImageDirectory);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
