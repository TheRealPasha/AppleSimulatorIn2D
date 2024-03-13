package main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Flow;

import static data.Data.pets;
import static data.Data.equippedPets;
import static main.Panel.panel;

import data.Data;
import pets.Pet;
public class InventoryPanel extends JPanel {
    static int buttonWidth = 150;
    static int buttonHeight = 150;
    static double gap = 31.5; // 11

    static int x;
    static int inX;
    static int inWidth;
    static int inY;
    static int inHeight;
    static TipPanel tipPanel;
    static Timer timer = new Timer(300, l -> {
        tipPanel.setLocation(MouseInfo.getPointerInfo().getLocation().x + 20, MouseInfo.getPointerInfo().getLocation().y - tipPanel.getHeight() - 20);
        tipPanel.showTooltip();
    });
    static JScrollPane scrollPane;
    static JPanel innerPanel;
    public InventoryPanel(Panel panel, TipPanel tipPanel) {
        InventoryPanel.tipPanel = tipPanel;
        timer.setRepeats(false);
        int width = (int) (panel.SCREEN_WIDTH / 1.4);
        int height = (int) (panel.SCREEN_HEIGHT / 1.4);
        x = (panel.SCREEN_WIDTH - width) / 2;
        int y = panel.SCREEN_HEIGHT + panel.SCREEN_HEIGHT / 60;

        this.setBounds(x, y, width, height);
        this.setBackground(Color.white);
        this.setLayout(null);

        innerPanel = new JPanel();
        innerPanel.setBackground(Color.WHITE);
        scrollPane = new JScrollPane(innerPanel);

        inWidth = width - 70;
        inHeight = (int) (height / 1.3);
        inX = (width - inWidth) / 2;
        inY = (height - inHeight) / 2 + 50;

        innerPanel.setBounds(inX, inY, inWidth, inHeight);
        innerPanel.setLayout(null);
        innerPanel.setBorder(null);

        updateInventory();

        this.add(scrollPane);
    }

    public static void updateInventory() {
        innerPanel.removeAll();
        int row = 0;
        int col = 0;

        Arrays.sort(pets, Comparator.nullsLast(Comparator.comparing(Pet::isEquipped).reversed().thenComparing(Comparator.comparingLong(Pet::getDamage).reversed())));

        for (Pet pet : pets) {
            JLabel label = new JLabel();
            if (pet != null) {
                //label.setText(pet.getName());
                ImageIcon icon = new ImageIcon(Data.getPetImage(pet));
                int scale = buttonWidth - buttonWidth/10;
                Image scaleImage = icon.getImage().getScaledInstance(scale,scale, Image.SCALE_DEFAULT);
                label.setIcon(new ImageIcon(scaleImage));
            } else {
                //label.setText("Пусто");
            }
            int gapX = (int) (buttonWidth * row + gap * (row + 1));
            if (gapX + buttonWidth > inWidth) {
                col++;
                row = 0;
                gapX = (int) gap;
            }
            int gapY = (int) (buttonHeight * col + gap * (col + 1));
            label.setBounds(gapX, gapY, buttonWidth, buttonHeight);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setFocusable(true);
            if (pet != null && pet.isEquipped()) {
                label.setUI(new GradientLabelUI(innerPanel.getBackground(), new Color(0x41FF51)));
                label.setBorder(new LineBorder(new Color(0x4D41FF51, true), 5));
            }
            else if (pet == null) label.setUI(new GradientLabelUI(innerPanel.getBackground(), new Color(0xC4C4C4)));
            else {
                label.setUI(new GradientLabelUI(innerPanel.getBackground(), new Color(0x7979FF)));
            }
            label.setOpaque(false);
            label.setFocusable(true);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (pet != null) {
                       tipPanel.setPet(pet);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (pet != null) {
                        tipPanel.hideTooltip();
                        timer.stop();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (pet != null) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!pet.isEquipped() && equippedPets >= 6) {
                                JOptionPane.showMessageDialog(label.getParent().getParent(), "Нет места");
                            } else if (!pet.isEquipped() && equippedPets < 6) { // Экипирование питомца
                                pet.setEquipped(true);
                                equippedPets++;
                                label.setUI(new GradientLabelUI(innerPanel.getBackground(), new Color(0x41FF51)));
                                label.setBorder(new LineBorder(new Color(0x4D41FF51, true), 5));
                                panel.playSound(5);
                            } else if (pet.isEquipped()) { // Де-Экипирование питомца
                                pet.setEquipped(false);
                                equippedPets--;
                                label.setUI(new GradientLabelUI(innerPanel.getBackground(), new Color(0x7979FF)));
                                label.setBorder(null);
                                panel.playSound(6);
                            }
                            Data.updateDamage();
                            //System.out.println(Data.damage);
                        }
                        else if (e.getButton() == MouseEvent.BUTTON3) { // Удаление питомца
                            for (int i = 0; i < pets.length; i++) {
                                if (pets[i] == pet) {
                                    //pet.setEquipped(false);
                                    if (pet.isEquipped()) equippedPets--;
                                    pet.setEquipped(false);
                                    pets[i] = null;
                                    Data.updateDamage();
                                    tipPanel.hideTooltip();
                                    panel.playSound(7);
                                    //updateInventory();
                                    break;
                                }
                            }
                        }
                        updateInventory();
                    }
                }
            });
            label.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {

                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    if (pet != null) {
                        tipPanel.setPet(pet);
                        if (!tipPanel.isVisible()) {
                            timer.start();
                        } else {
                            tipPanel.setLocation(MouseInfo.getPointerInfo().getLocation().x + 20, MouseInfo.getPointerInfo().getLocation().y - tipPanel.getHeight() - 20);
                        }
                    }
                }

            });
            innerPanel.add(label);
            row++;
        }
        JLabel lastLabel = (JLabel) innerPanel.getComponent(innerPanel.getComponentCount()-1);
        int prefWidth = (int) (lastLabel.getX() + lastLabel.getWidth() + gap);
        int prefHeight = (int) (lastLabel.getY() + lastLabel.getHeight() + gap);
        innerPanel.setPreferredSize(new Dimension(prefWidth, prefHeight));

        scrollPane.setBounds(inX, inY, inWidth, inHeight);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // Внешн линия
        g2d.setColor(Color.decode("#FF726D"));
        g2d.setStroke(new BasicStroke(25, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 50, 50);

        // Внутр линия
        g2d.setColor(Color.decode("#FF726D"));
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int gapSize = 20; // Change the size of the gap here
        int innerWidth = getWidth() - gapSize * 2;
        int innerHeight = getHeight() - gapSize * 2;
        g2d.drawRoundRect(gapSize, gapSize, innerWidth, innerHeight, 40, 40);

        // Текст
        String text = "Инвентарь";
        g2d.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 95);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = metrics.getHeight() - 5;

        int outlineWidth = 15;
        g2d.setStroke(new BasicStroke(outlineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(234, 53, 67, 250)); // Increased opacity
               // g2d.drawString(text, x + i*outlineWidth/2 - 4, y + j*outlineWidth/2 + 5);
                g2d.drawString(text, x + i*outlineWidth/2, y + j*outlineWidth/2);
            }
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);

        g2d.dispose();
    }
}

