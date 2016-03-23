package blake.com.gameofthronesmap.otherFiles;

/**
 * Created by Raiders on 3/22/16.
 * Creates one instance of the music player.
 */
public class MusicStateSingleton {
    boolean isPlaying;
    static MusicStateSingleton instance;

    /**
     * Blank constructor
     */
    private MusicStateSingleton() {

    }

    /**
     * Boolean to see if the music player is playing.
     * @return
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Set the boolean for if the music is playing or not.
     * @param isPlaying
     */
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    /**
     * Creates instance of the music player to be used in all activities
     * @return
     */
    public static MusicStateSingleton getInstance() {
        if (instance == null) {
            instance = new MusicStateSingleton();
        }
        return instance;
    }
}
