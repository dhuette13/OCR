package com.ocrapp.startscreen;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.thinkti.android.filechooser.FileChooser;

import com.ocrapp.R;
import com.ocrapp.imageui.ImagePreprocessor;

public class StartActivity extends Activity implements OnItemSelectedListener{
	private String lang;
	private Spinner spinner;
	private Button uploadbtn;
	private Button camerabtn;
	private Button helpbtn;
	private Intent intent;	
	private Point point;
	
	private final String cameraDirectory = Environment.getExternalStorageDirectory() + "/tesseract/camera/image.png";

	private boolean isCamera = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		
		TextView myTextView = (TextView)findViewById(R.id.textview1);
		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Dosis-Bold.ttf");
		myTextView.setTypeface(typeFace);
		
		
		/* Upload Button */
		intent = new Intent(this, FileChooser.class);
		uploadbtn = (Button) findViewById(R.id.button2);
		OnClickListener oclBtnUP = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				ArrayList<String> extensions = new ArrayList<String>();
				extensions.add(".jpg");
				extensions.add(".bmp");
				extensions.add(".png");
				intent.putStringArrayListExtra("filterFileExtension", extensions);
				startActivityForResult(intent, 1);
			}
		};
		uploadbtn.setOnClickListener(oclBtnUP);

		/* Camera Button*/
		camerabtn = (Button) findViewById(R.id.button1);
		OnClickListener oclBtnCAM = new OnClickListener() {
			@Override
			public void onClick(View v) {
				isCamera = true;
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				
				Uri uriSavedImage = Uri.fromFile(new File(cameraDirectory));
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
				
				if (cameraIntent.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(cameraIntent, 1);
				}
			}
		};
		camerabtn.setOnClickListener(oclBtnCAM);

		
		/* Help Button */
		 helpbtn = (Button) findViewById(R.id.button3);
		 OnClickListener oclBtnHELP = new OnClickListener() {
		       @Override
		       public void onClick(View v) {
		         // TODO Auto-generated method stub

		    	   if (point != null)
		    	       showPopup(StartActivity.this, point);
		       }
		     };
		helpbtn.setOnClickListener(oclBtnHELP);

		
		/** Spinner **/
		spinner = (Spinner) findViewById(R.id.spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lang_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)
		spinner.setSelection(pos);
		lang = (String) spinner.getSelectedItem();

		if(lang.equals("English")){
			lang = "eng";
		}
		else if(lang.equals("Spanish"))
			lang = "spa";
		else if(lang.equals("French"))
			lang = "fra";
		else if(lang.equals("Hindi"))
			lang = "hin";
		else if(lang.equals("Arabic"))
			lang = "ara";
		else if(lang.equals("Portuguese"))
			lang ="por";
		else if(lang.equals("Simplified Chinese"))
			lang = "chi_sim";
		else if(lang.equals("German"))
			lang ="deu";
		System.out.println(lang); 

	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
		lang = "en";
		System.out.println(lang);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
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
		/* File chooser used */
		if ((requestCode == 1) && (resultCode == -1) && !isCamera) {
			String fileSelected = data.getStringExtra("fileSelected");
			System.out.println("SELECTED FILE: " + fileSelected);

			Intent imagePreprocessor = new Intent(this, ImagePreprocessor.class);
			imagePreprocessor.putExtra("file", fileSelected);
			imagePreprocessor.putExtra("lang", lang);
			startActivity(imagePreprocessor);

		}
		/* Camera used to capture image */
		else if ((requestCode == 1) && (resultCode == -1) && isCamera) {
//			String fileSelected = data.getStringExtra("fileSelected");
//			System.out.println("SELECTED FILE: " + fileSelected);
			
			Intent imagePreprocessor = new Intent(this, ImagePreprocessor.class);
			imagePreprocessor.putExtra("file", cameraDirectory);
			imagePreprocessor.putExtra("lang", lang);
			startActivity(imagePreprocessor);
			
		}
	}
	
	// Next two methods are for help button popup
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	 
	   int[] location = new int[2];
	   Button button = (Button) findViewById(R.id.button3);
	 
	   // Get the x, y location and store it in the location[] array
	   // location[0] = x, location[1] = y.
	   button.getLocationOnScreen(location);
	 
	   //Initialize the Point with x, and y positions
	   point = new Point();
	   point.x = location[0];
	   point.y = location[1];
	}
	 
	// The method that displays the popup.
	private void showPopup(final Activity context, Point p) {
	   int popupWidth = 900;
	   int popupHeight = 650;
	 
	   // Inflate the popup_layout.xml
	   LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
	   LayoutInflater layoutInflater = (LayoutInflater) context
	     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);
	 
	   // Creating the PopupWindow
	   final PopupWindow popup = new PopupWindow(context);
	   popup.setContentView(layout);
	   popup.setWidth(popupWidth);
	   popup.setHeight(popupHeight);
	   popup.setFocusable(true);
	 
	   // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
	   int OFFSET_X = 60;
	   int OFFSET_Y = 70;
	 
	   // Clear the default translucent background
	   popup.setBackgroundDrawable(new BitmapDrawable());
	 
	   // Displaying the popup at the specified location, + offsets.
	   popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
	 
	   // Getting a reference to Close button, and close the popup when clicked.
	   Button close = (Button) layout.findViewById(R.id.close);
	   close.setOnClickListener(new OnClickListener() {
	 
	     @Override
	     public void onClick(View v) {
	       popup.dismiss();
	     }
	   });
	}

}
