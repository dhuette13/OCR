package com.ocrapp.imageui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class TextImageView extends ImageView {
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private float node1X, node1Y, node2X, node2Y, node3X, node3Y, node4X, node4Y;

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
		paint.setAlpha(70);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(10);
	}
	
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
}
