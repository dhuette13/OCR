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
			//			DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
			NodeDragShadow shadowBuilder = new NodeDragShadow(view, nodeNumber);
			System.out.println("ACTION DOWN");
			view.startDrag(data, shadowBuilder, view, 0);
			view.setVisibility(View.INVISIBLE);
			return true;
		}
		else {
			return false;

		}

	}

	class NodeDragShadow extends View.DragShadowBuilder {

		int dragPointX, dragPointY;
		
		public NodeDragShadow(View view, int nodeNumber){
			super(view);

			switch(nodeNumber){
			case 1:
				System.out.println("Shadow BUilder case 1");
				dragPointX = 0;
				dragPointY = 0;
				break;
			case 2:
				System.out.println("Shadow BUilder case 2");
				dragPointX = 0;
				dragPointY = 0;
				break;
			case 3:
				System.out.println("Shadow BUilder case 3");
				dragPointX = 0;
				dragPointY = 0;
				break;
			case 4:
				System.out.println("Shadow BUilder case 4");
				dragPointX = 0;
				dragPointY = 0;
				break;
			default:
				System.out.println("Shadow BUilder case default");
				dragPointX = view.getWidth();
				dragPointY = view.getHeight();
			}
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
			System.out.println("DRAG POINT SET TO: " + dragPointX + " " + dragPointY);
			shadowTouchPoint.set(dragPointX, dragPointY);
		}
	}
}
