package blake.com.gameofthronesmap.OtherFiles;

/**
 * Created by Raiders on 3/14/16.
 */
public class GOTCharacter {
    String name;
    String sex;
    String continent;
    String house;
    String description;
    Boolean isLiked;
    int largeImage;
    String badAss;
    //Set icon and full size image

    public GOTCharacter(String name, String sex, String continent, String house, String description, Boolean isLiked, int largeImage, String badAss) {
        this.name = name;
        this.sex = sex;
        this.continent = continent;
        this.house = house;
        this.description = description;
        this.isLiked = isLiked;
        this.largeImage = largeImage;
        this.badAss = badAss;
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
}
