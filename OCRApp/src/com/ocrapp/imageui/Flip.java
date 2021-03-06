package com.ocrapp.imageui;

import android.graphics.Bitmap;

import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Rotate;
import com.googlecode.leptonica.android.WriteFile;

public class Flip {
	private Bitmap image;
	
	
	public void setImage(Bitmap i){
		image = i;
	}

	public Bitmap rotateBitmap(float degrees){
		
		/* Read original bitmap file to Pix object */
		Pix pix = ReadFile.readBitmap(image);
		Pix rotatedPix = Rotate.rotate(pix, degrees);
		image= WriteFile.writeBitmap(rotatedPix);
		
		return image;
	}
}
