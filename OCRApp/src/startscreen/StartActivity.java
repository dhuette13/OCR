package startscreen;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.thinkti.android.filechooser.FileChooser;

import com.ocrapp.R;
import com.ocrapp.imageui.ImagePreprocessor;


public class StartActivity extends Activity implements OnItemSelectedListener{
	private String lang;
	Spinner spinner;
	Button uploadbtn;
	Button camerabtn;
	TextView tvOut;
	Intent intent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		/** Upload Button ***/
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
		         // TODO Auto-generated method stub
		   		
		   		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		   		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
		   			startActivityForResult(cameraIntent, 0);
		   		}
		       }
		     };
		camerabtn.setOnClickListener(oclBtnCAM);

		

		spinner = (Spinner) findViewById(R.id.spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lang_array, android.R.layout.simple_spinner_item);
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
    		 lang = "en";
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
		getMenuInflater().inflate(R.menu.start, menu);
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if ((requestCode == 1) && (resultCode == -1)) {
	    	Bitmap bmp = null;
	        String fileSelected = data.getStringExtra("fileSelected");
	        System.out.println("SELECTED FILE: " + fileSelected);
	        
	        Intent imagePreprocessor = new Intent(this, ImagePreprocessor.class);
	        imagePreprocessor.putExtra("file", fileSelected);
	        imagePreprocessor.putExtra(lang, lang);
	        startActivity(imagePreprocessor);
	        
//	        BitmapFactory.Options options = new BitmapFactory.Options();
//	        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//	        try {
//	            bmp = BitmapFactory.decodeStream(new FileInputStream(fileSelected), null, options);
//	        } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	        }
//	        bmp = BitmapFactory.decodeFile(fileSelected, options);
//	        
//	        if(bmp != null){
//	        	textView.setText("Initializing Tesesseract...");
//	        	final TessBaseAPI baseApi = new TessBaseAPI();
//	        	baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
//	        	baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
//	        	baseApi.setImage(bmp);
//	        	String text = baseApi.getUTF8Text();
//	        	System.out.println(text);
//	        	textView.append(text);
//	        	
//	        	baseApi.end();
//	        }
//	        else {
//	        	textView.setText("Error reading image file: " + fileSelected);
//	        }
	    }            
	}

}
