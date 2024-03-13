package main;

import data.Data;
import pets.Pet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static main.Panel.panel;

public class PetDropPanel extends JPanel implements PhaseInterface {
    static JLabel dropLabel;
    static JLabel petInfo;

    boolean isPhasing = false;
    boolean isPhased = false;

    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            phaseOut();
        }
    };

    public PetDropPanel() {
        int width = (int) (panel.SCREEN_WIDTH / 2.3);
        int height = (int) (panel.SCREEN_HEIGHT / 1.8);
        int x = (panel.SCREEN_WIDTH - width) / 2;
        int y = panel.SCREEN_HEIGHT + panel.SCREEN_HEIGHT / 60;

        this.setBounds(x,y,width,height);
        this.setVisible(false);

        this.setLayout(null);
        //this.setOpaque(false);
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder((Color.decode("#FF726D")), 20));
        this.addMouseListener(mouseAdapter);
    }

    public void showPetDrop(Pet pet) {
        ImageIcon icon = new ImageIcon(Data.getPetImage(pet));
        //Image image = icon.getImage();
        int scale = (int) (this.getWidth()/2.5);
        Image scaleImage = icon.getImage().getScaledInstance(scale,scale, Image.SCALE_DEFAULT);
        int labelX = (this.getWidth() - scale) / 2;
        int labelY = (this.getHeight() - scale) / 2;

        dropLabel = new JLabel();
        dropLabel.setBounds(0,-45, this.getWidth(), this.getHeight());
        dropLabel.setIcon(new ImageIcon(scaleImage));
        dropLabel.addMouseListener(mouseAdapter);

        dropLabel.setHorizontalAlignment(JLabel.CENTER);
        dropLabel.setVerticalAlignment(JLabel.CENTER);
        dropLabel.setIconTextGap(10);

        dropLabel.setText(pet.getDefaultName());
        dropLabel.setFont(new Font("Arial", Font.BOLD, 80));
        //dropLabel.setHorizontalAlignment(JLabel.TOP);
        dropLabel.setHorizontalTextPosition(JLabel.CENTER);
        dropLabel.setVerticalTextPosition(JLabel.TOP);

        petInfo = new JLabel();
        petInfo.setBounds(0,-33, this.getWidth(), this.getHeight());
        petInfo.addMouseListener(mouseAdapter);
        petInfo.setText("Урон " + pet.getDamage());
        petInfo.setFont(new Font("Arial", Font.BOLD, 70));
        petInfo.setHorizontalAlignment(JLabel.CENTER);
        petInfo.setVerticalAlignment(JLabel.BOTTOM);

        this.add(dropLabel);
        this.add(petInfo);
        phaseIn();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


    }

    @Override
    public void phaseIn() { // int x, int y
        int x = this.getX();
        int y = this.getY();
        if (!isPhasing) {
            this.setVisible(true);
            int targetY = (panel.SCREEN_HEIGHT - this.getHeight()) / 2;
            Thread t = new Thread(() -> {
                isPhasing = true;
                for (int i = y; i >= targetY; i -= 100) {
                    if (i <= targetY+100) i = targetY;
                    this.setLocation(x, i);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                isPhasing = false;
                isPhased = true;
            });
            if (!t.isAlive()) {
                t.start();
            }
        }
    }

    @Override
    public void phaseOut() {
        int x = this.getX();
        int y = this.getY();
        if (!isPhasing) {
            int targetY = panel.SCREEN_HEIGHT + panel.SCREEN_HEIGHT / 60;
            Thread t = new Thread(() -> {
                isPhasing = true;
                for (int i = y; i <= targetY; i += 80) {
                    if (i >= targetY-80) i = targetY;
                    this.setLocation(x, i);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                isPhasing = false;
                isPhased = false;
                this.removeAll(); // <----
                this.setVisible(false);
            });
            if (!t.isAlive()) {
                t.start();
            }
        }
    }
}
