package data;
import main.Panel;
import main.ResourcesPanel;
import pets.Pet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.io.*;
//import static Eggs.EggShop.PET_MULTIPLIERS;
public class Data {

    public static String userInput;

    public static long apple;
    public static long money;
    public static Pet[] pets;
    public static int tool;
    public static long damage; // Общий дамаг из всех петов

    public static final int[] TOOL_COST = {0,1000,5000,50000,100000,300000};
    public static final String[] PET_NAMES = {"Кот", "Собака", "Овечка", "Кролик", "Свинка", "Гусь", "Курочка", "Рыбка", "Пчёлка", "Ёж", "Лиса", "Хорек", "Мышь", "Зебра", "Пони"};
    public static final long[] PET_DAMAGE = {1,1,5,7,20,   7,8,14,20,50,    16,17,28,46,90};
    public static final HashMap<String, Long> PET_INFO = new HashMap<>();
    public static int equippedPets = 0;
    Panel panel;

    // Дата петов
    public static BufferedImage placeholder;
    static {
        try {
            placeholder = (ImageIO.read(Data.class.getResourceAsStream("/placeholder.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Data(Panel panel) throws Exception {
        setPetInfo();
        this.panel = panel;
        load();
        //System.out.println(pets[0].getId());
        Random random = new Random();
        if (pets == null) {
            pets = new Pet[35];
            int chosenPet = random.nextInt(2);
            String startPet = (chosenPet == 0) ? "Кот" : "Собака";
            pets[0] = new Pet(startPet, PET_INFO.get(startPet));
        }
       // System.out.println(pets[0].getName());
        //System.out.println(pets[0].getDamage());
        for (Pet pet : pets) {
            if (pet != null && pet.isEquipped()) equippedPets++;
        }
        updateDamage();
        //pets[4] = new Pet("Собака", 1L);
    }
    public static void updateDamage() {
        damage = 0;
        for (Pet pet : pets) {
            if (pet != null && pet.isEquipped()) {
                damage += pet.getDamage();
            }
        }
    }
    public static void updateApple() {
        //ResourcesPanel.appleLabel.setText("Яблоки: " + apple);
        ResourcesPanel.updateAppleLabel();
    }
    private void setPetInfo() {
        PET_INFO.put("Кот", 2L);
        PET_INFO.put("Собака", 2L);
        PET_INFO.put("Овечка", 5L);
        PET_INFO.put("Кролик", 10L);
        PET_INFO.put("Свинка", 20L);

        PET_INFO.put("Гусь", 7L);
        PET_INFO.put("Курочка", 8L);
        PET_INFO.put("Рыбка", 14L);
        PET_INFO.put("Пчёлка", 30L);
        PET_INFO.put("Ёж", 60L);

        PET_INFO.put("Лиса",21L);
        PET_INFO.put("Хорек", 22L);
        PET_INFO.put("Мышь", 35L);
        PET_INFO.put("Зебра", 65L);
        PET_INFO.put("Пони", 130L);
    }

    public static BufferedImage getPetImage(Pet pet) {
        BufferedImage petImage;
        try {
            petImage = ImageIO.read(Data.class.getResourceAsStream("/pets/"+pet.getDefaultName()+ "" + pet.getLevel() + ".png"));
        } catch (Exception e) {
            petImage = placeholder;
        }
        return petImage;
    }

    public static void save() {
        try {
            DataSave d1 = new DataSave(apple,money,tool,pets);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));
            oos.writeObject(d1);
            //System.out.println("Сейв создан");
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void load() throws Exception {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));
            DataSave d1 = (DataSave) ois.readObject();
            ois.close();
            apple = d1.getAPPLE();
            money = d1.getMONEY();
            tool = d1.getTOOL();
            pets = d1.getPETS();
        }
        catch (Exception e) {
            DataSave d1 = new DataSave(0,0, 1, null);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));
            oos.writeObject(d1);
            System.out.println("Новое сохранение создано");
            oos.close();
            load();
        }
    }
    public static String moneyCases(Long input) {
        String stringCost = Long.toString(input);
        String str = stringCost.length() > 2 ? stringCost.substring(stringCost.length() - 2) : stringCost;
        String lastNumber = (stringCost.substring(stringCost.length()-1));
        long stringLastNumber = Long.parseLong(lastNumber);
        long twoLastNumbers = Long.parseLong(str);
        if ((stringLastNumber == 1) && (twoLastNumbers!=11)) return "рубль";
        else if ((input>10) && (input<20)) return "рублей";
        else if ((stringLastNumber > 1) && (stringLastNumber < 5)) return "рубля";
        else return "рублей";
    }
    public static void mWait(int milSec) {
        try {
            Thread.sleep(milSec);
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

}
