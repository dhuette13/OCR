package com.ocrapp.imageui;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.googlecode.leptonica.android.Box;
import com.googlecode.leptonica.android.Clip;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.WriteFile;

public class Crop {

	private ImageView node1, node2, node3, node4;
	private Bitmap image;
	
	public Crop(ImageView node1, ImageView node2, ImageView node3, ImageView node4) {
		this.node1 = node1;
		this.node2 = node2;
		this.node3 = node3;
		this.node4 = node4;
	}

	public void setImage(Bitmap imageBitmap) {
		image = imageBitmap;
	}
	
	public Bitmap crop(int screenWidth, int screenHeight){
		int width = image.getWidth();
		int height = image.getHeight();
		
		/* Read original bitmap file to Pix object */
		Pix pix = ReadFile.readBitmap(image);
		width = pix.getWidth();
		height = pix.getHeight();
		
		int x1 = (width * ((int) node1.getX())) / screenWidth;
		int y1 = (height * ((int) node1.getY())) / screenHeight;
		int x2 = (width * ((int) node2.getX())) / screenWidth;
		int y2 = (height * ((int) node3.getY())) / screenHeight;
		int cropWidth = x2 - x1;
		int cropHeight = y2 - y1;
		
		
		Box box = new Box(x1, y1, cropWidth, cropHeight);
		Pix croppedPix = Clip.clipRectangle(pix, box);
		Bitmap croppedBitmap = WriteFile.writeBitmap(croppedPix);
		
		return croppedBitmap;
	}

}
