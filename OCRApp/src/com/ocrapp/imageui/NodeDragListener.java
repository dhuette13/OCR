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
	int nodeXOffset, nodeYOffset;

	public NodeDragListener(ImageView n1, ImageView n2, ImageView n3, ImageView n4){
		node1 = n1;
		node2 = n2;
		node3 = n3;
		node4 = n4;

	}

	public void updateNodes(){
		target.updateNode(1, node1.getX() + nodeXOffset, node1.getY() + nodeYOffset);
		target.updateNode(2, node2.getX() + nodeXOffset, node2.getY() + nodeYOffset);
		target.updateNode(3, node3.getX() + nodeXOffset, node3.getY() + nodeYOffset);
		target.updateNode(4, node4.getX() + nodeXOffset, node4.getY() + nodeYOffset);
		target.invalidate();
	}

	@Override
	public boolean onDrag(View view, DragEvent event) {
		System.out.println("UPDATING NODE OFFSETS: " + node1.getWidth() + " " + node1.getHeight());
		nodeXOffset= node1.getWidth() / 2;
		nodeYOffset = node1.getHeight() / 2;
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
				target.updateNode(1, x + nodeXOffset, y + nodeYOffset);
				//				dragged.setX(x + nodeXOffset);
				//				dragged.setY(y + nodeYOffset);
				//			node2.setX(node2.getX() - nodeXOffset);
				//			node3.setY(node3.getY() - nodeYOffset);
				//				node2.setY(y - nodeYOffset);
				//				node3.setX(x - nodeXOffset);
				System.out.println("NODE OFFSETS: " + nodeXOffset + ", " + nodeYOffset);
				//				node2.invalidate();
				//				node3.invalidate();
			}
			else if(dragged == node2){
				node1.setY(y);
				node4.setX(x);
				updateNodes();
				target.updateNode(2, x + nodeXOffset, y + nodeYOffset);
			}
			else if(dragged == node3){
				node4.setY(y);
				node1.setX(x);
				updateNodes();
				target.updateNode(3, x + nodeXOffset, y + nodeYOffset);
			}
			else if(dragged == node4){
				node3.setY(y);
				node2.setX(x);
				updateNodes();
				target.updateNode(4, x + nodeXOffset, y + nodeYOffset);
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
			updateNodes();
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
