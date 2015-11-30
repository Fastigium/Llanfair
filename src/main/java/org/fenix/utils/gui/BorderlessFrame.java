//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.fenix.utils.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BorderlessFrame extends JFrame implements MouseListener, MouseMotionListener {
	private int snapSize;
	private int startMouseX;
	private int startMouseY;
	private int startFrameX;
	private int startFrameY;
	private int startHeight;
	private int startWidth;
	private boolean doDrag;
	private boolean doResize;

	public BorderlessFrame(String title) {
		this();
		this.setTitle(title);
	}

	public BorderlessFrame() {
		this.doDrag = false;
		this.doResize = false;
		this.snapSize = 10;
		this.setUndecorated(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public int getSnapSize() {
		return this.snapSize;
	}

	public void setSnapSize(int snapSize) {
		if(snapSize < 0) {
			throw new IllegalArgumentException("Illegal snap: " + snapSize);
		} else {
			this.snapSize = snapSize;
		}
	}

	public void mousePressed(MouseEvent event) {
		if(this.getCursor().getType() != 0) {
			this.doResize = true;
			this.startFrameX = this.getX();
			this.startFrameY = this.getY();
			this.startHeight = this.getHeight();
			this.startWidth = this.getWidth();
		} else {
			this.doDrag = true;
			this.startMouseX = event.getX();
			this.startMouseY = event.getY();
		}

	}

	public void mouseDragged(MouseEvent event) {
		if(SwingUtilities.isLeftMouseButton(event)) {
			int type;
			int width;
			if(this.doDrag) {
				type = this.getX() + event.getX() - this.startMouseX;
				width = this.getY() + event.getY() - this.startMouseY;
				this.setLocation(type, width);
			}

			if(this.doResize) {
				type = this.getCursor().getType();
				width = this.getWidth();
				int height = this.getHeight();
				int frameX = this.startFrameX;
				int frameY = this.startFrameY;
				if(type == 5) {
					width = event.getXOnScreen() - this.startFrameX;
					height = event.getYOnScreen() - this.startFrameY;
				} else if(type == 11) {
					width = event.getXOnScreen() - this.startFrameX;
				} else if(type == 7) {
					width = event.getXOnScreen() - this.startFrameX;
					height = this.startFrameY + this.startHeight - event.getYOnScreen();
					frameY = event.getYOnScreen();
				} else if(type == 8) {
					height = this.startFrameY + this.startHeight - event.getYOnScreen();
					frameY = event.getYOnScreen();
				} else if(type == 6) {
					width = this.startFrameX + this.startWidth - event.getXOnScreen();
					height = this.startFrameY + this.startHeight - event.getYOnScreen();
					frameY = event.getYOnScreen();
					frameX = event.getXOnScreen();
				} else if(type == 10) {
					width = this.startFrameX + this.startWidth - event.getXOnScreen();
					frameX = event.getXOnScreen();
				} else if(type == 4) {
					width = this.startFrameX + this.startWidth - event.getXOnScreen();
					height = event.getYOnScreen() - this.startFrameY;
					frameX = event.getXOnScreen();
				} else if(type == 9) {
					height = event.getYOnScreen() - this.startFrameY;
				}

				Dimension minSize = this.getMinimumSize();
				if(width > minSize.width && height > minSize.height) {
					this.setSize(width, height);
					this.setLocation(frameX, frameY);
				} else if(width > minSize.width) {
					this.setSize(width, minSize.height);
					this.setLocation(frameX, this.getY());
				} else if(height > minSize.height) {
					this.setSize(minSize.width, height);
					this.setLocation(this.getX(), frameY);
				}
			}

		}
	}

	public void mouseMoved(MouseEvent event) {
		int farSnapX = this.getX() + this.getWidth() - this.snapSize;
		int farSnapY = this.getY() + this.getHeight() - this.snapSize;
		int lowSnapX = this.getX() + this.snapSize;
		int lowSnapY = this.getY() + this.snapSize;
		int mouseX = event.getXOnScreen();
		int mouseY = event.getYOnScreen();
		if(mouseX > farSnapX) {
			if(mouseY > farSnapY) {
				this.changeCursor(5);
			} else if(mouseY < lowSnapY) {
				this.changeCursor(7);
			} else {
				this.changeCursor(11);
			}
		} else if(mouseX < lowSnapX) {
			if(mouseY < lowSnapY) {
				this.changeCursor(6);
			} else if(mouseY > farSnapY) {
				this.changeCursor(4);
			} else {
				this.changeCursor(10);
			}
		} else if(mouseY > farSnapY) {
			this.changeCursor(9);
		} else if(mouseY < lowSnapY) {
			this.changeCursor(8);
		} else {
			this.changeCursor(0);
		}

	}

	public void mouseReleased(MouseEvent event) {
		this.doDrag = false;
		this.doResize = false;
	}

	public void mouseClicked(MouseEvent event) {
	}

	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}

	private void changeCursor(int cursorType) {
		int type = this.getCursor().getType();
		if(cursorType != type) {
			this.setCursor(Cursor.getPredefinedCursor(cursorType));
		}

	}
}
