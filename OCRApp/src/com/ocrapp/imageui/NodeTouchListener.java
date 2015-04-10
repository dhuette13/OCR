package com.ocrapp.imageui;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class NodeTouchListener implements OnTouchListener {
	Nodes node;
	float x, y;
	
	public NodeTouchListener(Nodes node, float x, float y){
		this.node = node;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		view = (ImageView) view;
		if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
			ClipData.Item item = new ClipData.Item((CharSequence) node.toString());
			String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
//			ClipData data = ClipData.newPlainText("node", node.toString());
			ClipData data = new ClipData("node", mimeTypes, item);
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			System.out.println("ACTION DOWN");
			view.startDrag(data, shadowBuilder, view, 0);
			view.setVisibility(View.INVISIBLE);
			return true;
		}
		else {
			return false;
		}
	}

}
