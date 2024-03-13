package data;

import pets.Pet;

import java.io.Serializable;

public class DataSave implements Serializable {
    private final long APPLE;
    private final long MONEY;
    private final int TOOL;
    private final Pet[] PETS;
    DataSave(long ap, long mo, int to, Pet[] pe)  {
        this.APPLE = ap;
        this.MONEY = mo;
        this.TOOL = to;
        this.PETS = pe;
    }
    public long getAPPLE() {
        return APPLE;
    }
    public long getMONEY() {
        return MONEY;
    }
    public int getTOOL() {
        return TOOL;
    }
    public Pet[] getPETS() {
        return PETS;
    }
}
