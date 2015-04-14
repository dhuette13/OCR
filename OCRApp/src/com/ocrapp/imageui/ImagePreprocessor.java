package com.ocrapp.imageui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ocrapp.Conversion;
import com.ocrapp.R;
import com.ocrapp.startscreen.StartActivity;

public class ImagePreprocessor extends Activity {

	Bitmap imageBitmap;
	Bitmap oldBitmap = null;
	String lang;
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
	
	Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_preprocessor);

		System.out.println("MAX MEMORY: " + Runtime.getRuntime().maxMemory() / (1024 * 1024));

		/* Initialize crop and flip tools, and bitmap history */
		crop = new Crop();
		flip = new Flip();

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_preprocessor, menu);
		//		getMenuInflater().inflate(R.menu.image_preprocessor_menu, menu);
		this.menu = menu;
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
			oldBitmap = imageBitmap;
			System.out.println("CROPPING IMAGE");
			crop.setNodes(node1, node2, node3, node4);
			crop.setImage(imageBitmap, imageView);

			imageBitmap = crop.cropBitmap();
			imageView.setImageBitmap(imageBitmap);
			menu.findItem(R.id.undo).setEnabled(true);
		}
		else if(id == R.id.rotate_left){
			oldBitmap = imageBitmap;
			System.out.println("ROTATING IMAGE LEFT");
			flip.setImage(imageBitmap);
			imageBitmap = flip.rotateBitmap(-90);
			imageView.setImageBitmap(imageBitmap);
			menu.findItem(R.id.undo).setEnabled(true);
		}
		else if(id == R.id.rotate_right){
			oldBitmap = imageBitmap;
			System.out.println("ROTATING IMAGE RIGHT");
			flip.setImage(imageBitmap);
			imageBitmap = flip.rotateBitmap(90);
			imageView.setImageBitmap(imageBitmap);
			menu.findItem(R.id.undo).setEnabled(true);

			System.gc();
		}
		else if(id == R.id.undo){
			if(oldBitmap != null){
				imageBitmap = oldBitmap;
				System.out.println("UNDOING");
				imageView.setImageBitmap(imageBitmap);
				oldBitmap = null;
				item.setEnabled(false);
				
				System.gc();
			}
		}
		else if(id == R.id.cancel){
			Intent i = new Intent(this, StartActivity.class);
			startActivity(i);

			System.gc();
		}
		else if(id == R.id.OK){
			System.out.println("OK Pressed");
			
			Intent i = new Intent(this, Conversion.class);
			i.putExtra("lang", lang);

			FileOutputStream out = null;
			try {
				out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/tesseract/modimage.png");
				imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(out != null)
						out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			imageBitmap.recycle();
			startActivity(i);
		}
		else if(id == R.id.action_help){
			//			Intent i = new Intent(this, Help.class);
			//			startActivity(i);
		}
		System.gc();
		return super.onOptionsItemSelected(item);
	}

}
