package com.ocrapp.imageui;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.googlecode.leptonica.android.Box;
import com.googlecode.leptonica.android.Clip;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.WriteFile;

public class Crop {

	private ImageView node1, node2, node3, node4;
	private Bitmap image;
	private TextImageView imageView;
	
	public void setImage(Bitmap imageBitmap, TextImageView view) {
		image = imageBitmap;
		imageView = view;
	}

	public void setNodes(ImageView n1, ImageView n2, ImageView n3, ImageView n4) {
		node1 = n1;
		node2 = n2;
		node3 = n3;
		node4 = n4;
	}
	
	public Bitmap cropBitmap(){
		int actualWidth = imageView.getActualWidth();
		int actualHeight = imageView.getActualHeight();
		int width = image.getWidth();
		int height = image.getHeight();
		int nodeXOffset = node1.getWidth();
		int nodeYOffset = node1.getHeight();
		
		int node1X = (int) node1.getX();
		int node1Y = (int) node1.getY();
		int node2X = (int) node2.getX() + nodeXOffset;
		int node2Y = (int) node2.getY();
		int node3X = (int) node3.getX();
		int node3Y = (int) node3.getY() + nodeYOffset;
		
		int startX = (imageView.getWidth() - actualWidth) / 2;
		int startY = (imageView.getHeight() - actualHeight) / 2;
		int endX = startX + actualWidth;
		int endY = startY + actualHeight;
		
		/* Read original bitmap file to Pix object */
		Pix pix = ReadFile.readBitmap(image);
		width = pix.getWidth();
		height = pix.getHeight();
		
		
		/* Calculate the x and y pixel locations of the original, not scaled image:
		 * node position relative to scaled image = (absolute position of node within image view) - [[[(width of image view) - (width of scaled image)] / 2]]
		 * [(width of not scaled image) * (node position relative to scaled image)] / (width of scaled image)*/
		int x1 = (width * (node1X - startX) / actualWidth);
		int y1 = (height * (node1Y - startY) / actualHeight);
		int x2 = (width * (node2X - startX) / actualWidth);
		int y2 = (height * (node3Y - startY) / actualHeight);
		int actualCropWidth = x2 - x1;
		int actualCropHeight = y2 - y1;
		
//		int scaledCropWidth = (int) (node2.getX() - node1.getX());
//		int scaledCropHeight = (int) (node3.getY() - node1.getY());
		
		
		System.out.println("StartX : " + startX + " StartY: " + startY + " EndX: " + endX + " EndY: " + endY);
		
		boolean validSelection = 	(node1X >= startX) && (node1X <= endX) && 
									(node1Y >= startY) && (node1Y <= endY) &&
									(node2X >= startX) && (node2X <= endX) &&
									(node2Y >= startY) && (node2Y <= endY) &&
									(node3X >= startX) && (node3X <= endX) &&
									(node3Y >= startY) && (node3Y <= endY);
		/* Ensure crop selection is inside the bounds of the image */
//		if( (x1 >= 0) && (scaledCropWidth >= 0) &&
//			(y1 >=0) && (scaledCropHeight >= 0) &&
//			(x2 >= 0) && (scaledCropWidth <= actualWidth) &&
//			(y2 >=0) && (scaledCropHeight <= actualHeight) &&
//			(node1.getX() >= 0) && (node1.getY() >= 0) &&
//			(node2.getX() >= 0) && (node2.getY() >= 0) &&
//			(node3.getX() >= 0) && (node3.getY() >= 0)){
		if(validSelection){
			/* Crop based on selection */
			Box box = new Box(x1, y1, actualCropWidth, actualCropHeight);
			Pix croppedPix = Clip.clipRectangle(pix, box);
			Bitmap croppedBitmap = WriteFile.writeBitmap(croppedPix);
			
			return croppedBitmap;
		}
		else {
			return null;
		}
		
	}
}
