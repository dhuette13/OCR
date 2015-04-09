package com.ocrapp.imageui;

import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.ImageView;


public class NodeDragListener implements OnDragListener {
	TextImageView target;
	ImageView dragged;
	ImageView node1, node2, node3, node4;
	float x, y;
	
	public NodeDragListener(ImageView n1, ImageView n2, ImageView n3, ImageView n4){
		node1 = n1;
		node2 = n2;
		node3 = n3;
		node4 = n4;
	}
	
	public void updateNodes(){
		target.updateNode(1, node1.getX(), node1.getY());
		target.updateNode(2, node2.getX(), node2.getY());
		target.updateNode(3, node3.getX(), node3.getY());
		target.updateNode(4, node4.getX(), node4.getY());
		target.invalidate();
	}

	@Override
	public boolean onDrag(View view, DragEvent event) {
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_ENTERED:
			System.out.println("DRAG ENTERED: " + event.getX() + ", " + event.getY());
			dragged = (ImageView) event.getLocalState();
			target = (TextImageView) view;
			x = event.getX();
			y = event.getY();
			updateNodes();
			break;
		case DragEvent.ACTION_DRAG_LOCATION:
			x = event.getX();
			y = event.getY();
			System.out.println("DRAG LOCATION: " + x + ", " + y);
			if(dragged == node1){
				node2.setY(y);
				node3.setX(x);
				updateNodes();
				target.updateNode(1, x, y);
			}
			else if(dragged == node2){
				node1.setY(y);
				node4.setX(x);
				updateNodes();
				target.updateNode(2, x, y);
			}
			else if(dragged == node3){
				node4.setY(y);
				node1.setX(x);
				updateNodes();
				target.updateNode(3, x, y);
			}
			else if(dragged == node4){
				node3.setY(y);
				node2.setX(x);
				updateNodes();
				target.updateNode(4, x, y);
			}
			else{
				System.out.println("Something else is being dragged...");
			}

//			System.out.println(event.getClipDescription().getLabel().toString());
//			if(event.getClipDescription().getLabel().toString().equals("node")){
//				System.out.println(event.getClipData());
////				System.out.println(event.getClipData().getItemCount());
////				System.out.println(event.getClipData().getItemAt(0));
//				if(event.getClipDescription().getLabel().toString().equals("NODE1")){
//					System.out.println("Node1");
//					target.updateNode(1, x, y);
//				}
//				else if(event.getClipDescription().getLabel().toString().equals("NODE2")){
//					System.out.println("Node2");
//					target.updateNode(2, x, y);
//				}
//				else if(event.getClipDescription().getLabel().toString().equals("NODE3")){
//					System.out.println("Node3");
//					target.updateNode(3, x, y);
//				}
//				else if(event.getClipDescription().getLabel().toString().equals("NODE4")){
//					System.out.println("Node4");
//					target.updateNode(4, x, y);
//				}
//			}

			break;
		case DragEvent.ACTION_DROP:
			dragged = (ImageView) event.getLocalState();
			target = (TextImageView) view;
			x = event.getX();
			y = event.getY();
			System.out.println("New Coordinates: " + x + ", " + y);
			dragged.setX(x);
			dragged.setY(y);
			dragged.setVisibility(View.VISIBLE);
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			System.out.println("DRAG EXITED: " + event.getX() + ", " + event.getY());
			break;
		default:
			break;
		}
		return true;
	}
}
