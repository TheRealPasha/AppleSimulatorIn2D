package main;

import data.AutoSave;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        String[] frameTitles = {"Симулятор Яблок", "Яблоки!11!", "хм?", "Яблоки в 2D"};
        Random ran = new Random();
        frame.setTitle(frameTitles[ran.nextInt(4)]);

        JLayeredPane layeredPane = new JLayeredPane();

        Panel panel = new Panel(layeredPane);
        MapsPanel mapsPanel = new MapsPanel(panel);
        TipPanel tipPanel = new TipPanel();
        InventoryPanel inventoryPanel = new InventoryPanel(panel, tipPanel);
        InventoryButton inventoryButton = new InventoryButton(panel,inventoryPanel);
        ResourcesPanel resourcesPanel = new ResourcesPanel();

        layeredPane.setPreferredSize(new Dimension(panel.SCREEN_WIDTH, panel.SCREEN_HEIGHT));
        layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(mapsPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(inventoryButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(inventoryPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(tipPanel, JLayeredPane.POPUP_LAYER);
        layeredPane.add(resourcesPanel, JLayeredPane.PALETTE_LAYER);

        panel.setBounds(0, 0, panel.SCREEN_WIDTH, panel.SCREEN_HEIGHT);

        Runtime.getRuntime().addShutdownHook(new AutoSave());

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        panel.setupGame();
        panel.startGameThread();
    }
}