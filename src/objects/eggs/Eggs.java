package objects.eggs;

import data.Data;
import main.InventoryPanel;
import main.Panel;
import main.PetDropPanel;
import objects.Objects;
import pets.Pet;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import static main.Panel.panel;

public class Eggs extends Objects {
    private String[] petNames;
    private int cost;
    protected MouseAdapter clickToBuy = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (Data.apple >= getCost() && !petDropPanel.isVisible()) {
                //Data.apple -= getCost();
                //panel.playSound(8);
                petDrops();
            }
            else if (Data.apple >= getCost() && petDropPanel.isVisible()) JOptionPane.showMessageDialog(null, "Получите предыдущего питомца, предже чем купить новое яйцо");
            else JOptionPane.showMessageDialog(null, "Не хватает яблок для покупки: " + (getCost() - Data.apple));
            Data.updateApple();
        }
    };
    PetDropPanel petDropPanel = new PetDropPanel();
   // Random random = new Random();

    public void petDrops() {
        int randomPet = random.nextInt(100) + 1;
        String dropName;
        System.out.println(Arrays.toString(petNames));
        System.out.println(Data.apple);

        if (randomPet <= 60) dropName = (randomPet % 2 == 0) ? petNames[0] : petNames[1];
        else if (randomPet <= 80) dropName = petNames[2];
        else if (randomPet <= 95) dropName = petNames[3];
        else dropName = petNames[4];

        Pet pet = new Pet(dropName, Data.PET_INFO.get(dropName));
        for (int i = 0; i < Data.pets.length; i++) {
                if (Data.pets[i] == null) {
                    panel.playSound(8);
                    Data.pets[i] = pet;
                    Data.apple -= cost;

                    System.out.println(Data.pets[i].getName());
                    System.out.println(Data.pets[i].getDamage());
                    InventoryPanel.updateInventory();
                    panel.layeredPane.add(petDropPanel, JLayeredPane.POPUP_LAYER);
                    petDropPanel.showPetDrop(pet);
                    //petDropPanel.phaseIn(1000,500);
                    break;
                }
        }
        System.out.println(Data.apple);
    }

    public String[] getPetNames() {
        return petNames;
    }

    public void setPetNames(String[] petNames) {
        this.petNames = petNames;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
