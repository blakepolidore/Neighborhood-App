package blake.com.gameofthronesmap;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Test;

import blake.com.gameofthronesmap.otherFiles.DatabaseHelper;
import blake.com.gameofthronesmap.otherFiles.MusicStateSingleton;

/**
 * Created by Raiders on 3/22/16.
 * Tests the java within the application. Making sure OOP are adhered to and the logic of the application makes sense.
 */
public class ApplicationUnitTests extends AndroidTestCase {

    private DatabaseHelper db;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        //db = new ApplicationUnitTests(context);
    }

    public void testAddEntry(){
        // Here I have my new database which is not connected to the standard database of the App
    }

    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }

    @Test
    public void testMusicStateSingletonSetIsPlayingAndIsPlaying() {
        MusicStateSingleton musicState = MusicStateSingleton.getInstance();
        boolean expected = true;
        musicState.setIsPlaying(true);
        boolean actual = musicState.isPlaying();
        assertEquals(expected, actual);
    }

//    @Test
//    public void checkIfInsertAndGetBooleanWork() {
//        DatabaseHelper instance = DatabaseHelper.getInstance(getInstrumentation().getContext());
//        instance.insert("Blake Polidore", "Male", "North America", "Polidore",
//                "This dude is awesome", true, R.drawable.got, "Most badass", " ");
//        boolean expected = true;
//        boolean actual = instance.getCharacterIsLikedBoolean(0);
//        assertEquals(expected, actual);
//    }
}
