package data;

public class AutoSave extends Thread {
    @Override
    public void run() {
        try {
            Data.save();
            System.out.println("Данные сохранены");
        }
        catch (Exception e) {
            System.out.println("Ошибка сохранения");
            e.printStackTrace();
        }
    }
}
