package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundUrl = new URL[15];

    public Sound() {

        soundUrl[0] = getClass().getResource("/sound/bgmusic1.wav");

        soundUrl[1] = getClass().getResource("/sound/apple-hit.wav");
        soundUrl[2] = getClass().getResource("/sound/apple-kill.wav");

        soundUrl[3] = getClass().getResource("/sound/inventory-open.wav");
        soundUrl[4] = getClass().getResource("/sound/inventory-close.wav");

        soundUrl[5] = getClass().getResource("/sound/pet-equip.wav");
        soundUrl[6] = getClass().getResource("/sound/pet-unequip.wav");
        soundUrl[7] = getClass().getResource("/sound/pet-delete.wav");

        soundUrl[8] = getClass().getResource("/sound/egg-buy.wav");

        soundUrl[9] = getClass().getResource("/sound/walk-1.wav");
        soundUrl[10] = getClass().getResource("/sound/walk-2.wav");
    }
    public void setFile(int i, float volume) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}
