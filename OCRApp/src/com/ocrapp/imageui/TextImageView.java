package com.ocrapp.imageui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class TextImageView extends ImageView {
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private float node1X, node1Y, node2X, node2Y, node3X, node3Y, node4X, node4Y;
	private int actualWidth, actualHeight;

	public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setPaintOptions();
	}

	public TextImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setPaintOptions();
	}

	public TextImageView(Context context) {
		super(context);
		setPaintOptions();
	}

	private void setPaintOptions(){
		paint.setColor(Color.BLACK);
		paint.setAlpha(255);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeJoin(Paint.Join.MITER);
		paint.setStrokeCap(Paint.Cap.SQUARE);
		paint.setStrokeWidth(5);
	}
	
	public int getActualWidth() { return actualWidth; };
	public int getActualHeight() { return actualHeight; };

	public void updateNode(int node, float x, float y){
		switch(node){
		case 1:
			node1X = x;
			node1Y = y;
			break;
		case 2:
			node2X = x;
			node2Y = y;
			break;
		case 3:
			node3X = x;
			node3Y = y;
			break;
		case 4:
			node4X = x;
			node4Y = y;
			break;
		}
	}
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		System.out.println("DRAWING TEXT VIEW");
		canvas.drawLine(node1X, node1Y, node2X, node2Y, paint);
		canvas.drawLine(node1X, node1Y, node3X, node3Y, paint);
		canvas.drawLine(node3X, node3Y, node4X, node4Y, paint);
		canvas.drawLine(node2X, node2Y, node4X, node4Y, paint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// Get image matrix values and place them in an array
		float[] f = new float[9];
		getImageMatrix().getValues(f);

		// Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
		final float scaleX = f[Matrix.MSCALE_X];
		final float scaleY = f[Matrix.MSCALE_Y];

		// Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
		final Drawable d = getDrawable();
		final int origW = d.getIntrinsicWidth();
		final int origH = d.getIntrinsicHeight();

		// Calculate the actual dimensions
		actualWidth = Math.round(origW * scaleX);
		actualHeight = Math.round(origH * scaleY);

	}
}
