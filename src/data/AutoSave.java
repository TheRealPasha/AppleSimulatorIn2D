package data;

public class AutoSave extends Thread {
    @Override
    public void run() {
        try {
            Data.save();
            System.out.println("������ ���������");
        }
        catch (Exception e) {
            System.out.println("������ ����������");
            e.printStackTrace();
        }
    }
}
