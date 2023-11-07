package logic.audio;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {

    private Clip clip;
    private int volume = 100;

    public AudioPlayer(String path){
    	
    	
    	try {
			InputStream is = getClass().getResourceAsStream(path);
			if(is == null)
				System.out.println("Audio non esistente " + path);
			
			
			InputStream bufferedIn = new BufferedInputStream(is);
			AudioInputStream ais =AudioSystem.getAudioInputStream(bufferedIn);
			
			//AudioInputStream ais =AudioSystem.getAudioInputStream(is);
			
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,baseFormat.getSampleRate(),16,baseFormat.getChannels(),baseFormat.getChannels() * 2,baseFormat.getSampleRate(),false);
			AudioInputStream dais =AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e) {
			System.out.println("Impossibile caricare il file audio " + path);
			e.printStackTrace();
		}
    	

    }


    public void play() {
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void playContinuosly() {
        if(clip == null) return;
        stop();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if(clip.isRunning()) clip.stop();
    }

    //Chiudi la traccia audio
    public void close() {
        stop();
        clip.close();
    }

    public void setVolume(int level) {
        
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMinimum();
        float result = range * (1 - level / 100.0f);
        control.setValue(result);
        

    }

    public void playAtFramePosition(int frame) {
        if(clip == null) return;
        stop();
        clip.setFramePosition(frame);
        clip.start();
    }

    public boolean isRunning() {
        return clip.isActive();
    }

    public void increaseVolume(){
       
        System.out.println(volume);
        if(volume >= 90)
            volume = 100;
        else
            volume = volume +10;
        setVolume(volume);
    }

    public void decreaseVolume(){
        if(volume <= 10)
            volume = 0;
        else 
        volume = volume - 10;
        setVolume(volume);
    }

}