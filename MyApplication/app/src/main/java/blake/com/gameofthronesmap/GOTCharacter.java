package blake.com.gameofthronesmap;

/**
 * Created by Raiders on 3/14/16.
 */
public class GOTCharacter {
    String name;
    String sex;
    String continent;
    String house;

    public GOTCharacter(String name, String sex, String continent, String house) {
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
