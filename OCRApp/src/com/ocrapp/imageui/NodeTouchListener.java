package com.ocrapp.imageui;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class NodeTouchListener implements OnTouchListener {
	float x, y;
	int nodeNumber;

	public NodeTouchListener(float x, float y, int nodeNumber){
		this.nodeNumber = nodeNumber;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		view = (ImageView) view;
		if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

			ClipData data = ClipData.newPlainText("", "");
			NodeDragShadow shadowBuilder = new NodeDragShadow(view);
			shadowBuilder.onProvideShadowMetrics(new Point(view.getWidth(), view.getHeight()), new Point(0, 0));
			System.out.println("ACTION DOWN");
			view.startDrag(data, shadowBuilder, view, 0);
			view.setVisibility(View.INVISIBLE);
			return true;
		}
		else {
			return false;

		}

	}
	
	class NodeDragShadow extends DragShadowBuilder {
		
		public NodeDragShadow(View view){
			super(view);
		}

		@Override
		public void onDrawShadow(Canvas canvas){
			super.onDrawShadow(canvas);
		}

		@Override
		public void onProvideShadowMetrics (Point shadowSize, Point shadowTouchPoint){
			View v = getView();
			
			int height = (int) v.getHeight();
			int width = (int) v.getWidth();
			
			shadowSize.set(width, height);
			shadowTouchPoint.set(0, 0);
		}
	}
}
