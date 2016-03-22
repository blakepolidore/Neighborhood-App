package blake.com.gameofthronesmap.OtherFiles;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/21/16.
 * <h1>Song Service</h1>
 * Creates a service that allows a song to be played across all activites. Allows the user to stop
 * and play the song on any activity.
 */
public class SongService extends Service {

    MediaPlayer themeMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Creates the media player and makes it loop.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        themeMediaPlayer = MediaPlayer.create(this, R.raw.gottheme);
        themeMediaPlayer.setLooping(true);
    }

    /**
     * Stops the song when called and changes the boolean to false.
     * The boolean notifies the activities if the song is playing or not.
     */
    @Override
    public void onDestroy() {
        themeMediaPlayer.stop();
        MusicStateSingleton.getInstance().setIsPlaying(false);
        super.onDestroy();

    }

    /**
     * Starts the song.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        themeMediaPlayer.start();
        MusicStateSingleton.getInstance().setIsPlaying(true);
        return super.onStartCommand(intent, flags, startId);
    }

}
