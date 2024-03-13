package main;

import pets.Pet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class TipPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel damageLabel;
    private JLabel levelLabel;
    private Timer timer;

    public TipPanel() {
        this.setOpaque(false);
        //setPreferredSize(new Dimension(500, 300));
        this.setBounds(0, 0, 400, 200);
        this.setVisible(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        nameLabel = new JLabel();
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Finlandica Medium", Font.BOLD, 60));
        nameLabel.setForeground(new Color(0x000000));
        this.add(nameLabel);


        damageLabel = new JLabel();
        damageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        damageLabel.setFont(new Font("Arial", Font.BOLD, 55));
        damageLabel.setLocation(damageLabel.getX(), damageLabel.getY()-55);
        damageLabel.setForeground(new Color(0x000000));
        this.add(damageLabel);

        levelLabel = new JLabel();
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 45));
        levelLabel.setForeground(new Color(0x000000));
        this.add(levelLabel);

        timer = new Timer(300, e -> setVisible(true));
        timer.setRepeats(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        Color startColor = Color.WHITE;
        Color endColor = new Color(0xABB4FF);
        GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2d.setPaint(gradient);
        g2d.fill(new RoundRectangle2D.Double(0, 0, width, height, 25, 25));

        g2d.setColor(new Color(0x3BA7FF));
        int borderThickness = 5;
        g2d.setStroke(new BasicStroke(borderThickness));
        int arcSize = 20;
        g2d.draw(new RoundRectangle2D.Double(
                borderThickness / 2,
                borderThickness / 2,
                width - borderThickness,
                height - borderThickness,
                arcSize,
                arcSize));

        g2d.dispose();
    }


    public void setPet(Pet pet) {
        nameLabel.setText(pet.getName());
        damageLabel.setText("Урон " + pet.getDamage());
        levelLabel.setText("Уровень " + pet.getLevel());

        // Point p = MouseInfo.getPointerInfo().getLocation();
        //setLocation(p.x + 20, p.y + 20);
    }


    public void showTooltip() {
        //timer.restart();
        this.setVisible(true);
       // Point p = e.getLocationOnScreen();
       // this.setLocation(p);
    }

    public void hideTooltip() {
       // timer.stop();
        this.setVisible(false);
    }
}
