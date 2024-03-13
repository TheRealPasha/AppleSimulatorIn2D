package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upHold, downHold, leftHold, rightHold;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upHold = true;
        }
        if (code == KeyEvent.VK_A) {
            leftHold = true;
        }
        if (code == KeyEvent.VK_S) {
            downHold = true;
        }
        if (code == KeyEvent.VK_D) {
            rightHold = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upHold = false;
        }
        if (code == KeyEvent.VK_A) {
            leftHold = false;
        }
        if (code == KeyEvent.VK_S) {
            downHold = false;
        }
        if (code == KeyEvent.VK_D) {
            rightHold = false;
        }
    }
}
