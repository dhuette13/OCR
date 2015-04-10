package com.ocrapp.imageui;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.googlecode.leptonica.android.Box;
import com.googlecode.leptonica.android.Clip;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.WriteFile;

public class Crop {

	private static ImageView node1, node2, node3, node4;
	private static Bitmap image;
	
	public static void setImage(Bitmap imageBitmap) {
		image = imageBitmap;
	}
	
	public static Bitmap cropBitmap(int screenWidth, int screenHeight){
		int width = image.getWidth();
		int height = image.getHeight();
		int nodeXOffset = node1.getWidth() / 2;
		int nodeYOffset = node1.getHeight() / 2;
		
		/* Read original bitmap file to Pix object */
		Pix pix = ReadFile.readBitmap(image);
		width = pix.getWidth();
		height = pix.getHeight();
		
		
		int x1 = (width * ((int) node1.getX() + nodeXOffset)) / screenWidth;
		int y1 = (height * ((int) node1.getY() + nodeYOffset)) / screenHeight;
		int x2 = (width * ((int) node2.getX() + nodeXOffset)) / screenWidth;
		int y2 = (height * ((int) node3.getY() + nodeYOffset)) / screenHeight;
		int cropWidth = x2 - x1;
		int cropHeight = y2 - y1;
		
		
		Box box = new Box(x1, y1, cropWidth, cropHeight);
		Pix croppedPix = Clip.clipRectangle(pix, box);
		Bitmap croppedBitmap = WriteFile.writeBitmap(croppedPix);
		
		return croppedBitmap;
	}

	public static void setNodes(ImageView n1, ImageView n2, ImageView n3, ImageView n4) {
		node1 = n1;
		node2 = n2;
		node3 = n3;
		node4 = n4;
		
	}

}
