package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseThing implements MouseListener {
    MapsPanel mapsPanel;
    Panel panel;
    int x;
    int y;
    int width;
    int height;
    public boolean isPhased;
    public MouseThing(MapsPanel mapsPanel, Panel panel, int width, int height, int x, int y) {
        this.mapsPanel = mapsPanel;
        this.panel = panel;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //mapsPanel.setBackground(Color.RED);
        //mapsPanel.setLocation(0,y);
        if (!isPhased && !mapsPanel.isPhasing) {
            mapsPanel.phaseIn();
            isPhased = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Point cursorPos = e.getPoint();
        boolean isCursorOverPanel = cursorPos.x >= 0 && cursorPos.x <= mapsPanel.getWidth()
                && cursorPos.y >= 0 && cursorPos.y <= mapsPanel.getHeight();
        boolean isCursorOverButton = false;
        Component[] components = mapsPanel.getComponents();
        for (Component component : components) {
            if (component instanceof MapsButton) {
                Point buttonPos = component.getLocation();
                int buttonWidth = component.getWidth();
                int buttonHeight = component.getHeight();
                if (cursorPos.x >= buttonPos.x && cursorPos.x <= buttonPos.x + buttonWidth
                        && cursorPos.y >= buttonPos.y && cursorPos.y <= buttonPos.y + buttonHeight) {
                    isCursorOverButton = true;
                    break;
                }
            }
        }
        if (!isCursorOverPanel && !isCursorOverButton && isPhased && !mapsPanel.isPhasing) {
            mapsPanel.phaseOut();
            isPhased = false;
        }
    }
}
