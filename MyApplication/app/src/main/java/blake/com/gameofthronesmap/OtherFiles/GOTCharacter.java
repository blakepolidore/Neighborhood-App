package blake.com.gameofthronesmap.otherFiles;

/**
 * Created by Raiders on 3/14/16.
 * <h1>Game of Thrones Characters</h1>
 * Default character object that gets put into the database.
 * @author Blake Polidore
 * @version 1.0
 * @since 2016-03-20
 */
public class GOTCharacter {

    /**
     * This class creates the parameters necessary for each character.
     * This class makes a series of methods that allows you to manipulate and search through the database of these characters.
     * @param name Name of character
     * @param sex Sex of character
     * @param continent Continent the character is currently on. Sorry for spoilers
     * @param house House allegiances for character
     * @param description Description of the character
     * @param isLiked Boolean for whether or not the user has "liked" the character
     * @param largeImage integer id for character's picture in database
     * @param badAss my (The developer's) opinion of how badass the character is
     * @param characterReviews User can leave reviews of the character
     */
    String name;
    String sex;
    String continent;
    String house;
    String description;
    Boolean isLiked;
    int largeImage;
    String badAss;
    String characterReviews;

    /*
    Constructor for character. Each character must have all of these traits.
    This constructor is not used for the creation of the database
     */
    public GOTCharacter(String name, String sex, String continent, String house, String description,
                        Boolean isLiked, int largeImage, String badAss, String characterReviews) {
        this.name = name;
        this.sex = sex;
        this.continent = continent;
        this.house = house;
        this.description = description;
        this.isLiked = isLiked;
        this.largeImage = largeImage;
        this.badAss = badAss;
        this.characterReviews = characterReviews;
    }

    public GOTCharacter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public int getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(int largeImage) {
        this.largeImage = largeImage;
    }

    public String getBadAss() {
        return badAss;
    }

    public void setBadAss(String badAss) {
        this.badAss = badAss;
    }

    public String getCharacterReviews() {
        return characterReviews;
    }

    public void setCharacterReviews(String characterReviews) {
        this.characterReviews = characterReviews;
    }
}
