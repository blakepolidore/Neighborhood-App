package blake.com.gameofthronesmap.OtherFiles;

/**
 * Created by Raiders on 3/14/16.
 */
public class GOTCharacter {
    int id;
    String name;
    String sex;
    String continent;
    String house;
    //Set icon and full size image and description

    public GOTCharacter(int id, String name, String sex, String continent, String house) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.continent = continent;
        this.house = house;
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
}
