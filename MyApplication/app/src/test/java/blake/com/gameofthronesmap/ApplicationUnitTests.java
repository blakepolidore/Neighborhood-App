package blake.com.gameofthronesmap;

import org.junit.Test;

import blake.com.gameofthronesmap.OtherFiles.GOTCharacter;

import static org.junit.Assert.assertEquals;

/**
 * Created by Raiders on 3/22/16.
 * Tests the java within the application. Making sure OOP are adhered to and the logic of the application makes sense.
 */
public class ApplicationUnitTests {

    /**
     * Makes sure the character's name is correct when the character is created.
     */
    @Test
    public void testNameOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is aweseome", true, R.drawable.got, "Most badass", " ");
        String expected = "Blake Polidore";
        String actual = character.getName();
        assertEquals(expected, actual);
    }

    /**
     * Makes sure the character's sex is correct when the character is created.
     */
    @Test
    public void testSexOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is aweseome", true, R.drawable.got, "Most badass", " ");
        String expected = "Male";
        String actual = character.getSex();
        assertEquals(expected, actual);
    }

    /**
     * Makes sure the character's continent is correct when the character is created.
     */
    @Test
    public void testContinentOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is aweseome", true, R.drawable.got, "Most badass", " ");
        String expected = "North America";
        String actual = character.getContinent();
        assertEquals(expected, actual);
    }

    /**
     * Makes sure the character's house is correct when the character is created.
     */
    @Test
    public void testHouseOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is aweseome", true, R.drawable.got, "Most badass", " ");
        String expected = "Polidore";
        String actual = character.getHouse();
        assertEquals(expected, actual);
    }

    /**
     * Makes sure the character's description is correct when the character is created.
     */
    @Test
    public void testDescriptionOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is awesome", true, R.drawable.got, "Most badass", " ");
        String expected = "This dude is awesome";
        String actual = character.getDescription();
        assertEquals(expected, actual);
    }

    /**
     * Makes sure the character's isLiked boolean is correct when the character is created.
     */
    @Test
    public void testIsLikedOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is awesome", true, R.drawable.got, "Most badass", " ");
        Boolean expected = true;
        Boolean actual = character.getIsLiked();
        assertEquals(expected, actual);
    }

    /**
     * Makes sure the character's image is correct when the character is created.
     */
    @Test
    public void testImageOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is awesome", true, R.drawable.got, "Most badass", " ");
        int expected = R.drawable.got;
        int actual = character.getLargeImage();
        assertEquals(expected, actual);
    }

    /**
     * Makes sure the character's badass level is correct when the character is created.
     */
    @Test
    public void testBadassOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is awesome", true, R.drawable.got, "Most badass", " ");
        String expected = "Most badass";
        String actual = character.getBadAss();
        assertEquals(expected, actual);
    }

    /**
     * Makes sure the character's reviews is correct when the character is created.
     */
    @Test
    public void testReviewsOfCharacter() {
        GOTCharacter character = new GOTCharacter("Blake Polidore", "Male", "North America", "Polidore",
                "This dude is awesome", true, R.drawable.got, "Most badass", " ");
        String expected = " ";
        String actual = character.getCharacterReviews();
        assertEquals(expected, actual);
    }
}
