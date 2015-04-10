package com.ocrapp.imageui;

import java.util.ArrayList;

import startscreen.StartActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ocrapp.Conversion;
import com.ocrapp.R;

public class ImagePreprocessor extends Activity {

	Bitmap imageBitmap;
	String lang;
	ArrayList <Bitmap>history;
	Crop crop;
	Flip flip;
	
	TextImageView imageView;
	ImageView node1;
	ImageView node2;
	ImageView node3;
	ImageView node4;
	NodeTouchListener touchListener1;
	NodeTouchListener touchListener2;
	NodeTouchListener touchListener3;
	NodeTouchListener touchListener4;
	NodeDragListener dragListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_preprocessor);
		
		history = new ArrayList<Bitmap>();
		
		/* Get ImageView's from stylesheet */
		imageView = (TextImageView) findViewById(R.id.imagePreview);
		node1 = (ImageView) findViewById(R.id.nodeIcon1);
		node2 = (ImageView) findViewById(R.id.nodeIcon2);
		node3 = (ImageView) findViewById(R.id.nodeIcon3);
		node4 = (ImageView) findViewById(R.id.nodeIcon4);
		
		/* Create and set drag / touch listeners */
		dragListener = new NodeDragListener(node1, node2, node3, node4);
		touchListener1 = new NodeTouchListener(node1.getX(), node1.getY(), 1);
		touchListener2 = new NodeTouchListener(node2.getX(), node2.getY(), 2);
		touchListener3 = new NodeTouchListener(node3.getX(), node3.getY(), 3);
		touchListener4 = new NodeTouchListener(node4.getX(), node4.getY(), 4);
		
		node1.setOnTouchListener(touchListener1);
		node2.setOnTouchListener(touchListener2);
		node3.setOnTouchListener(touchListener3);
		node4.setOnTouchListener(touchListener4);
        
		/* Get passed file selected, read to bitmap, and place on image preview */
		String fileSelected = (String) this.getIntent().getExtras().get("file");
		lang = (String) this.getIntent().getExtras().get("lang");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        imageBitmap = BitmapFactory.decodeFile(fileSelected, options);
        imageView.setImageBitmap(imageBitmap);
        
        imageView.setOnDragListener(dragListener);
	}

//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//		super.onCreateContextMenu(menu, v, menuInfo);
//		getMenuInflater().inflate(R.menu.image_preprocessor_menu, menu);
//	}
//	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_preprocessor, menu);
//		getMenuInflater().inflate(R.menu.image_preprocessor_menu, menu);
		return super.onCreateOptionsMenu(menu);
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
		else if(id == R.id.crop) {
			history.add(imageBitmap);
			System.out.println("CROPPING IMAGE");
			Crop.setNodes(node1, node2, node3, node4);
			Crop.setImage(imageBitmap);
			imageBitmap = Crop.cropBitmap(imageView.getWidth(), imageView.getHeight());
			imageView.setImageBitmap(imageBitmap);
		}
		else if(id == R.id.rotate_left){
			history.add(imageBitmap);
			System.out.println("ROTATING IMAGE LEFT");
			Flip.setImage(imageBitmap);
			imageBitmap = Flip.rotateBitmap(-90);
			imageView.setImageBitmap(imageBitmap);
		}
		else if(id == R.id.rotate_right){
			history.add(imageBitmap);
			System.out.println("ROTATING IMAGE RIGHT");
			Flip.setImage(imageBitmap);
			imageBitmap = Flip.rotateBitmap(90);
			imageView.setImageBitmap(imageBitmap);
		}
		else if(id == R.id.undo){
			if(history.size() != 0){
				imageBitmap = history.remove(history.size() - 1);
				System.out.println("UNDOING");
				imageView.setImageBitmap(imageBitmap);
			}
		}
		else if(id == R.id.cancel){
			Intent i = new Intent(this, StartActivity.class);
			startActivity(i);
		}
		else if(id == R.id.OK){
			Intent i = new Intent(this, Conversion.class);
			i.putExtra("lang", lang);
			i.putExtra("image", imageBitmap);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	

}
