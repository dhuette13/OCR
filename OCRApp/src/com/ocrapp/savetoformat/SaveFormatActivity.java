package com.ocrapp.savetoformat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.ocrapp.R;
import com.ocrapp.startscreen.StartActivity;

public class SaveFormatActivity extends Activity {

	private String importedText;
	Button createPDF;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);

		Bundle b = getIntent().getExtras();
		importedText = b.getString("text");
		//added afterwards
		createPDF = (Button)findViewById(R.id.button1);
		  createPDF.setOnClickListener(new View.OnClickListener() {
		   @Override
		   public void onClick(View v) {
		    // TODO Auto-generated method stub
		    createPDF();
		   }
		  });
	            	
	            	
	            	
	            	
	            	
	            	
	            	
/*	                File file = new File("OCRApp.pdf");

//	                if (file.exists()) {
/	                    Uri path = Uri.fromFile(file);
	                    Intent intent = new Intent(Intent.ACTION_VIEW);
	                    intent.setDataAndType(path, "application/pdf");
	                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	                    try {
	                        startActivity(intent);
	                    } 
	                    catch (ActivityNotFoundException e) {
	                        Toast.makeText(SaveFormatActivity.this, 
	                            "No Application Available to View PDF", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                }
	                
	                
*/	                
//	            }
//	        });
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
/*	
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

*/	
	
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
	
	
	public void createPDF(){	
		
		Document doc = null;
		
  	  try {

  	  	  doc = new Document();
  		  
  		  String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

  	   File dir = new File(path);
  	   if(!dir.exists())
  	    dir.mkdirs();

  	   Log.d("PDFCreator", "PDF Path: " + path);

  	   File file = new File(dir, "demo.pdf");
  	   FileOutputStream fOut = new FileOutputStream(file);

  	   PdfWriter.getInstance(doc, fOut);

  	   //open the document
  	   doc.open();

  	   /* Create Paragraph and Set Font */
  	   Paragraph p1 = new Paragraph(importedText);

  	   /* Create Set Font and its Size */
  	   Font paraFont= new Font(Font.HELVETICA);
  	   paraFont.setSize(16);
  	   p1.setAlignment(Paragraph.ALIGN_CENTER);
  	   p1.setFont(paraFont);

  	   //add paragraph to document    
  	   doc.add(p1);

  	   
  	   //set footer
  	   Phrase footerText = new Phrase("This is an example of a footer");
  	   HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
  	   doc.setFooter(pdfFooter);

  	   Toast.makeText(getApplicationContext(), " PDF Created", Toast.LENGTH_LONG).show();
  	   
  	  } catch (DocumentException de) {
  	   Log.e("PDFCreator", "DocumentException:" + de);
  	  } catch (IOException e) {
  	   Log.e("PDFCreator", "ioException:" + e);
  	  } 
  	  finally
  	  {
  	   doc.close();
  	  }

		
		
	}
	
	
	public void showToast(){
		
		Toast giveMessage=Toast.makeText(this, "File saved in pdf format", Toast.LENGTH_LONG);
		giveMessage.show();  

		
	}
	
	public void gotostart(View view){
			Intent i = new Intent(this, StartActivity.class);
			startActivity(i);	
	} 
		
}


