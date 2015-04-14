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
		
		/* Read original bitmap file to Pix object */
		Pix pix = ReadFile.readBitmap(image);
		width = pix.getWidth();
		height = pix.getHeight();
		
		
		/* Calculate the x and y pixel locations of the original, not scaled image:
		 * node position relative to scaled image = (absolute position of node within image view) - [[[(width of image view) - (width of scaled image)] / 2]]
		 * [(width of not scaled image) * (node position relative to scaled image)] / (width of scaled image)*/
		int x1 = (width * ((int) node1.getX() - (imageView.getWidth() - actualWidth) / 2)) / actualWidth;
		int y1 = (height * ((int) node1.getY() - (imageView.getHeight() - actualHeight) /  2)) / actualHeight;
		int x2 = (width * ((int) node2.getX() + nodeXOffset - (imageView.getWidth() - actualWidth) / 2)) / actualWidth;
		int y2 = (height * ((int) node3.getY() + nodeYOffset - (imageView.getHeight() - actualHeight) / 2)) / actualHeight;
		int cropWidth = x2 - x1;
		int cropHeight = y2 - y1;
		
		if((x1 >= 0) && (y1 >=0) && (x2 >= 0) && (y2 >=0)){
			Box box = new Box(x1, y1, cropWidth, cropHeight);
			Pix croppedPix = Clip.clipRectangle(pix, box);
			Bitmap croppedBitmap = WriteFile.writeBitmap(croppedPix);
			
			return croppedBitmap;
		}
		else {
			System.out.println("Invalid selection");
			return image;
		}
		
	}
}
