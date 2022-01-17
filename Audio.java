import java.io.File;
import java.io.IOException;
  
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
  
public class Audio {
  Clip clip; 

  AudioInputStream audioInput;
  public static String filePath;
  
  // constructor to initialize streams and clip
  public Audio(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

    filePath = fileName;
    
    // create AudioInputStream object
    audioInput = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
          
    // create clip reference
    clip = AudioSystem.getClip();
          
    // open audioInputStream to the clip
    clip.open(audioInput);
          
    clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void play() throws InterruptedException{
      // play clip for 2 seconds
      clip.start();
      Thread.sleep(2000);
    }

    public void stop() throws UnsupportedAudioFileException,
    IOException, LineUnavailableException 
    {
        // stop clip
        clip.stop();
        clip.close();
    }
}