package pets;

import data.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class Pet implements Serializable {
    private int id;
    private int level = 0;
    private Long damage;
    private String name; // �������� ������� �������������� ����� ����� ������
    private String defaultName;

    private boolean isEquipped = false;
    public Pet(String pet, Long damage) {
        this.setDamage(damage);
        this.setDefaultName(pet); // ����������� ��� ����
        this.setName(pet); // ��� ������� ����� ������� ������ ����
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public Long getDamage() {
        return damage;
    }

    public void setDamage(Long damage) {
        this.damage = damage;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDefaultName() {
        return defaultName;
    }
}
