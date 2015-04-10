package com.ocrapp.imageui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ocrapp.R;

public class ImagePreprocessor extends Activity {

	Bitmap imageBitmap;
	Bitmap oldBitmap;
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
		
		/* Get ImageView's from stylesheet */
		imageView = (TextImageView) findViewById(R.id.imagePreview);
		node1 = (ImageView) findViewById(R.id.nodeIcon1);
		node2 = (ImageView) findViewById(R.id.nodeIcon2);
		node3 = (ImageView) findViewById(R.id.nodeIcon3);
		node4 = (ImageView) findViewById(R.id.nodeIcon4);
		
		/* Create and set drag / touch listeners */
		dragListener = new NodeDragListener(node1, node2, node3, node4);
		touchListener1 = new NodeTouchListener(Nodes.NODE1, node1.getX(), node1.getY());
		touchListener2 = new NodeTouchListener(Nodes.NODE1, node2.getX(), node2.getY());
		touchListener3 = new NodeTouchListener(Nodes.NODE1, node3.getX(), node3.getY());
		touchListener4 = new NodeTouchListener(Nodes.NODE1, node4.getX(), node4.getY());
		
		node1.setOnTouchListener(touchListener1);
		node2.setOnTouchListener(touchListener2);
		node3.setOnTouchListener(touchListener3);
		node4.setOnTouchListener(touchListener4);
		
        
		/* Get passed file selected, read to bitmap, and place on image preview */
		String fileSelected = (String) this.getIntent().getExtras().get("file");
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
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.image_preprocessor, menu);
//		getMenuInflater().inflate(R.menu.image_preprocessor_menu, menu);
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
		else if(id == R.id.crop) {
			oldBitmap = imageBitmap;
			System.out.println("CROPPING IMAGE");
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int screenHeight = displaymetrics.heightPixels;
			int screenWidth = displaymetrics.widthPixels;
			Crop.setNodes(node1, node2, node3, node4);
			Crop.setImage(imageBitmap);
			imageBitmap = Crop.cropBitmap(imageView.getWidth(), imageView.getHeight());
			imageView.setImageBitmap(imageBitmap);
		}
		else if(id == R.id.rotate_left){
			oldBitmap = imageBitmap;
			System.out.println("ROTATING IMAGE LEFT");
			Flip.setImage(imageBitmap);
			imageBitmap = Flip.rotateBitmap(-90);
			imageView.setImageBitmap(imageBitmap);
		}
		else if(id == R.id.rotate_right){
			oldBitmap = imageBitmap;
			System.out.println("ROTATING IMAGE RIGHT");
			Flip.setImage(imageBitmap);
			imageBitmap = Flip.rotateBitmap(90);
			imageView.setImageBitmap(imageBitmap);
		}
		else if(id == R.id.undo){
			imageBitmap = oldBitmap;
			System.out.println("UNDOING");
			imageView.setImageBitmap(imageBitmap);
		}
		return super.onOptionsItemSelected(item);
	}
	

}
