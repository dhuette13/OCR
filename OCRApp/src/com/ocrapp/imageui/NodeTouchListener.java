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
	Nodes node;
	float x, y;
	int nodeNumber;

	public NodeTouchListener(Nodes node, float x, float y, int nodeNumber){
		this.node = node;
		this.nodeNumber = nodeNumber;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		view = (ImageView) view;
		if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
			//			ClipData.Item item = new ClipData.Item((CharSequence) node.toString());
			//			String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
			//			ClipData data = new ClipData("node", mimeTypes, item);

			ClipData data = ClipData.newPlainText("node", node.toString());
			NodeDragShadow shadowBuilder = new NodeDragShadow(view);
//			if(nodeNumber == 1 || nodeNumber == 3){
//				shadowBuilder.setTouchPoint(view.getWidth(), view.getHeight());
//			}
//			else if(nodeNumber == 2 || nodeNumber == 4){
//				shadowBuilder.setTouchPoint(0, 0);
//			}
//			DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
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
		private int touchX;
		private int touchY;
		
		public NodeDragShadow(View view){
			super(view);
		}

		public void setTouchPoint(int x, int y){
			touchX = x;
			touchY = y;
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
