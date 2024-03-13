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
    private String name; // Возможно добавлю переименование петов самим юзером
    private String defaultName;

    private boolean isEquipped = false;
    public Pet(String pet, Long damage) {
        this.setDamage(damage);
        this.setDefaultName(pet); // Стандартное имя пета
        this.setName(pet); // Имя которым игрок назовет своего пета
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
