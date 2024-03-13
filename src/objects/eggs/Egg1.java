package objects.eggs;

import data.Data;
import main.Panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Egg1 extends Eggs {

    public Egg1(Panel panel) {
        name = "Обычное Яйцо";
        mapOn = "map0.txt";
        this.setCost(1);
        this.setPetNames(new String[]{"Кот", "Собака", "Овечка", "Кролик", "Свинка"});
       // clickToBuy = new MouseAdapter() {
         //   @Override
          //  public void mouseReleased(MouseEvent e) {
              //  if (Data.apple >= getCost() && !petDropPanel.isVisible()) {
              //      //Data.apple -= getCost();
               //     petDrops();
               // }
              //  else if (Data.apple >= getCost() && petDropPanel.isVisible()) JOptionPane.showMessageDialog(null, "Получите предыдущего питомца, предже чем купить новое яйцо");
               // else JOptionPane.showMessageDialog(null, "Не хватает яблок для покупки: " + (getCost() - Data.apple));
           // }
      //  };

        worldX = 5 * panel.TILE_SIZE;
        worldY = 5 * panel.TILE_SIZE;
        solidArea.width *= 2.5;
        solidArea.height *= 2.5;
        collision = true;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/eggs/egg1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        clickReceiver = new JButton();
        clickReceiver.setBorderPainted(false);
        clickReceiver.setContentAreaFilled(false);
        clickReceiver.setFocusPainted(false);
        clickReceiver.setOpaque(false);
        clickReceiver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clickReceiver.addMouseListener(clickToBuy);
    }

    @Override
    public String[] getPetNames() {
        return super.getPetNames();
    }

    @Override
    public void setPetNames(String[] petNames) {
        super.setPetNames(petNames);
    }

    @Override
    public int getCost() {
        return super.getCost();
    }

    @Override
    public void setCost(int cost) {
        super.setCost(cost);
    }
}
