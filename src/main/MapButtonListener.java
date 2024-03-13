package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapButtonListener implements ActionListener {
    String mapName;
    int tpX;
    int tpY;
    Panel panel;
    public MapButtonListener(String mapName, int tpX, int tpY, Panel panel) {
        this.mapName = mapName;
        this.tpX = tpX;
        this.tpY = tpY;
        this.panel = panel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        panel.tileManager.teleportPlayerToMap(mapName,tpX,tpY);
        //panel.assetSetter.setObject();
    }
}
