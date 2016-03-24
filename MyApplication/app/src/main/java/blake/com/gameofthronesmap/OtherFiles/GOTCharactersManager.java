package blake.com.gameofthronesmap.otherFiles;

import android.content.Context;

import java.util.ArrayList;

import blake.com.gameofthronesmap.R;

/**
 * Created by Raiders on 3/24/16.
 * Creates character objects to be put into the database.
 * Characters can be edited from this class.
 */
public class GOTCharactersManager {

    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";

    public static final String CONTINENT_WESTEROS = "Westeros";
    public static final String CONTINENT_ESSOS = "Essos";

    public static final String HOUSE_STARK = "Stark";
    public static final String HOUSE_TARGARYEN = "Targaryen";
    public static final String HOUSE_LANNISTER = "Lannister";
    public static final String HOUSE_MARTELL = "Martell";
    public static final String HOUSE_BARATHEON = "Baratheon";
    public static final String HOUSE_MORMONT = "Mormont";
    public static final String HOUSE_None = "None";
    public static final String HOUSE_TYRELL = "Tyrell";
    public static final String HOUSE_BOLTON = "Bolton";

    /**
     * Creates all the character's objects and places them into an arraylist
     * @param context
     * @return arraylist of all the character's objects
     */
    public static ArrayList<GOTCharacter> getGOTCharacters(Context context) {
        ArrayList<GOTCharacter> characterArrayList = new ArrayList<>();

        GOTCharacter jonSnow = new GOTCharacter();
        jonSnow.setName("Jon Snow");
        jonSnow.setSex(GENDER_MALE);
        jonSnow.setContinent(CONTINENT_WESTEROS);
        jonSnow.setHouse(HOUSE_STARK);
        jonSnow.setDescription(context.getString(R.string.jonSnow));
        jonSnow.setIsLiked(false);
        jonSnow.setLargeImage(R.drawable.jon_snow);
        jonSnow.setBadAss("Brooding Badass");
        jonSnow.setCharacterReviews("");
        characterArrayList.add(jonSnow);

        GOTCharacter aryaStark = new GOTCharacter();
        aryaStark.setName("Arya Stark");
        aryaStark.setSex(GENDER_FEMALE);
        aryaStark.setContinent(CONTINENT_ESSOS);
        aryaStark.setHouse(HOUSE_STARK);
        aryaStark.setDescription(context.getString(R.string.aryaStark));
        aryaStark.setIsLiked(false);
        aryaStark.setLargeImage(R.drawable.arya);
        aryaStark.setBadAss("Badass with a needle");
        aryaStark.setCharacterReviews("");
        characterArrayList.add(aryaStark);

        GOTCharacter daenerys = new GOTCharacter();
        daenerys.setName("Daenerys Targaryen");
        daenerys.setSex(GENDER_FEMALE);
        daenerys.setContinent(CONTINENT_ESSOS);
        daenerys.setHouse(HOUSE_TARGARYEN);
        daenerys.setDescription(context.getString(R.string.danyTargaryen));
        daenerys.setIsLiked(false);
        daenerys.setLargeImage(R.drawable.daenerys);
        daenerys.setBadAss("Badass with Dragons");
        daenerys.setCharacterReviews("");
        characterArrayList.add(daenerys);

        GOTCharacter jaimeLannister = new GOTCharacter();
        jaimeLannister.setName("Jaime Lannister");
        jaimeLannister.setSex(GENDER_MALE);
        jaimeLannister.setContinent(CONTINENT_WESTEROS);
        jaimeLannister.setHouse(HOUSE_LANNISTER);
        jaimeLannister.setDescription(context.getString(R.string.jaimeLannister));
        jaimeLannister.setIsLiked(false);
        jaimeLannister.setLargeImage(R.drawable.jaime);
        jaimeLannister.setBadAss("Recent Badass");
        jaimeLannister.setCharacterReviews("");
        characterArrayList.add(jaimeLannister);

        GOTCharacter cerseiLannister = new GOTCharacter();
        cerseiLannister.setName("Cersei Lannister");
        cerseiLannister.setSex(GENDER_FEMALE);
        cerseiLannister.setContinent(CONTINENT_WESTEROS);
        cerseiLannister.setHouse(HOUSE_LANNISTER);
        cerseiLannister.setDescription(context.getString(R.string.cerseiLannister));
        cerseiLannister.setIsLiked(false);
        cerseiLannister.setLargeImage(R.drawable.cersei);
        cerseiLannister.setBadAss("She's the worst");
        cerseiLannister.setCharacterReviews("");
        characterArrayList.add(cerseiLannister);

        GOTCharacter oberynMartell = new GOTCharacter();
        oberynMartell.setName("Oberyn Martell");
        oberynMartell.setSex(GENDER_MALE);
        oberynMartell.setContinent(CONTINENT_WESTEROS);
        oberynMartell.setHouse(HOUSE_MARTELL);
        oberynMartell.setDescription(context.getString(R.string.oberynMartell));
        oberynMartell.setIsLiked(false);
        oberynMartell.setLargeImage(R.drawable.oberyn);
        oberynMartell.setBadAss("Most Badass");
        oberynMartell.setCharacterReviews("");
        characterArrayList.add(oberynMartell);

        GOTCharacter joffreyBaratheon = new GOTCharacter();
        joffreyBaratheon.setName("Joffrey Baratheon");
        joffreyBaratheon.setSex(GENDER_MALE);
        joffreyBaratheon.setContinent(CONTINENT_WESTEROS);
        joffreyBaratheon.setHouse(HOUSE_BARATHEON);
        joffreyBaratheon.setDescription(context.getString(R.string.jofferyBaratheon));
        joffreyBaratheon.setIsLiked(false);
        joffreyBaratheon.setLargeImage(R.drawable.joffrey);
        joffreyBaratheon.setBadAss("Worst Person Ever");
        joffreyBaratheon.setCharacterReviews("");
        characterArrayList.add(joffreyBaratheon);

        GOTCharacter robertBaratheon = new GOTCharacter();
        robertBaratheon.setName("Robert Baratheon");
        robertBaratheon.setSex(GENDER_MALE);
        robertBaratheon.setContinent(CONTINENT_WESTEROS);
        robertBaratheon.setHouse(HOUSE_BARATHEON);
        robertBaratheon.setDescription(context.getString(R.string.robertBaratheon));
        robertBaratheon.setIsLiked(false);
        robertBaratheon.setLargeImage(R.drawable.robert);
        robertBaratheon.setBadAss("Was a badass");
        robertBaratheon.setCharacterReviews("");
        characterArrayList.add(robertBaratheon);

        GOTCharacter jorahMormont = new GOTCharacter();
        jorahMormont.setName("Jorah Mormont");
        jorahMormont.setSex(GENDER_MALE);
        jorahMormont.setContinent(CONTINENT_ESSOS);
        jorahMormont.setHouse(HOUSE_MORMONT);
        jorahMormont.setDescription(context.getString(R.string.jorahMormont));
        jorahMormont.setIsLiked(false);
        jorahMormont.setLargeImage(R.drawable.jorah);
        jorahMormont.setBadAss("Badass Voice");
        jorahMormont.setCharacterReviews("");
        characterArrayList.add(jorahMormont);

        GOTCharacter tyrionLannister = new GOTCharacter();
        tyrionLannister.setName("Tyrion Lannister");
        tyrionLannister.setSex(GENDER_MALE);
        tyrionLannister.setContinent(CONTINENT_ESSOS);
        tyrionLannister.setHouse(HOUSE_LANNISTER);
        tyrionLannister.setDescription(context.getString(R.string.tyrionLannister));
        tyrionLannister.setIsLiked(false);
        tyrionLannister.setLargeImage(R.drawable.tyrion_crossbow);
        tyrionLannister.setBadAss("Mega Badass");
        tyrionLannister.setCharacterReviews("");
        characterArrayList.add(tyrionLannister);

        GOTCharacter petyrBaelish = new GOTCharacter();
        petyrBaelish.setName("Petyr Baelish");
        petyrBaelish.setSex(GENDER_MALE);
        petyrBaelish.setContinent(CONTINENT_WESTEROS);
        petyrBaelish.setHouse(HOUSE_None);
        petyrBaelish.setDescription(context.getString(R.string.petyrBaelish));
        petyrBaelish.setIsLiked(false);
        petyrBaelish.setLargeImage(R.drawable.baelish);
        petyrBaelish.setBadAss("Sneaky Badass");
        petyrBaelish.setCharacterReviews("");
        characterArrayList.add(petyrBaelish);

        GOTCharacter varys = new GOTCharacter();
        varys.setName("Lord Varys");
        varys.setSex(GENDER_MALE);
        varys.setContinent(CONTINENT_ESSOS);
        varys.setHouse(HOUSE_None);
        varys.setDescription(context.getString(R.string.varys));
        varys.setIsLiked(false);
        varys.setLargeImage(R.drawable.varys);
        varys.setBadAss("Eunuch Badass");
        varys.setCharacterReviews("");
        characterArrayList.add(varys);

        GOTCharacter jeorMormont = new GOTCharacter();
        jeorMormont.setName("Jeor Mormont");
        jeorMormont.setSex(GENDER_MALE);
        jeorMormont.setContinent(CONTINENT_WESTEROS);
        jeorMormont.setHouse(HOUSE_MORMONT);
        jeorMormont.setDescription(context.getString(R.string.jeorMormont));
        jeorMormont.setIsLiked(false);
        jeorMormont.setLargeImage(R.drawable.jeor);
        jeorMormont.setBadAss("Badass-ish");
        jeorMormont.setCharacterReviews("");
        characterArrayList.add(jeorMormont);

        GOTCharacter doranMartell = new GOTCharacter();
        doranMartell.setName("Doran Martell");
        doranMartell.setSex(GENDER_MALE);
        doranMartell.setContinent(CONTINENT_WESTEROS);
        doranMartell.setHouse(HOUSE_MARTELL);
        doranMartell.setDescription(context.getString(R.string.doranMartell));
        doranMartell.setIsLiked(false);
        doranMartell.setLargeImage(R.drawable.doran);
        doranMartell.setBadAss("Ehh");
        doranMartell.setCharacterReviews("");
        characterArrayList.add(doranMartell);

        GOTCharacter aegonTargaryen = new GOTCharacter();
        aegonTargaryen.setName("Aegon Targaryen");
        aegonTargaryen.setSex(GENDER_MALE);
        aegonTargaryen.setContinent(CONTINENT_WESTEROS);
        aegonTargaryen.setHouse(HOUSE_TARGARYEN);
        aegonTargaryen.setDescription(context.getString(R.string.aegonTargaryen));
        aegonTargaryen.setIsLiked(false);
        aegonTargaryen.setLargeImage(R.drawable.aegon);
        aegonTargaryen.setBadAss("Original Badass");
        aegonTargaryen.setCharacterReviews("");
        characterArrayList.add(aegonTargaryen);

        GOTCharacter eddardStark = new GOTCharacter();
        eddardStark.setName("Eddard Stark");
        eddardStark.setSex(GENDER_MALE);
        eddardStark.setContinent(CONTINENT_WESTEROS);
        eddardStark.setHouse(HOUSE_STARK);
        eddardStark.setDescription(context.getString(R.string.eddardStark));
        eddardStark.setIsLiked(false);
        eddardStark.setLargeImage(R.drawable.eddard);
        eddardStark.setBadAss("Loyal Badass");
        eddardStark.setCharacterReviews("");
        characterArrayList.add(eddardStark);

        GOTCharacter melissandre = new GOTCharacter();
        melissandre.setName("Lady Melissandre");
        melissandre.setSex(GENDER_FEMALE);
        melissandre.setContinent(CONTINENT_WESTEROS);
        melissandre.setHouse(HOUSE_None);
        melissandre.setDescription(context.getString(R.string.melissandre));
        melissandre.setIsLiked(false);
        melissandre.setLargeImage(R.drawable.melissandre);
        melissandre.setBadAss("Black Magic Badass");
        melissandre.setCharacterReviews("");
        characterArrayList.add(melissandre);

        GOTCharacter branStark = new GOTCharacter();
        branStark.setName("Bran Stark");
        branStark.setSex(GENDER_MALE);
        branStark.setContinent(CONTINENT_WESTEROS);
        branStark.setHouse(HOUSE_STARK);
        branStark.setDescription(context.getString(R.string.branStark));
        branStark.setIsLiked(false);
        branStark.setLargeImage(R.drawable.bran);
        branStark.setBadAss("Maybe a Badass");
        branStark.setCharacterReviews("");
        characterArrayList.add(branStark);

        GOTCharacter tywinLannister = new GOTCharacter();
        tywinLannister.setName("Tywin Lannister");
        tywinLannister.setSex(GENDER_MALE);
        tywinLannister.setContinent(CONTINENT_WESTEROS);
        tywinLannister.setHouse(HOUSE_LANNISTER);
        tywinLannister.setDescription(context.getString(R.string.tywinLannister));
        tywinLannister.setIsLiked(false);
        tywinLannister.setLargeImage(R.drawable.tywin);
        tywinLannister.setBadAss("Disliked Badass");
        tywinLannister.setCharacterReviews("");
        characterArrayList.add(tywinLannister);

        GOTCharacter viserysTargaryen = new GOTCharacter();
        viserysTargaryen.setName("Viserys Targaryen");
        viserysTargaryen.setSex(GENDER_MALE);
        viserysTargaryen.setContinent(CONTINENT_ESSOS);
        viserysTargaryen.setHouse(HOUSE_TARGARYEN);
        viserysTargaryen.setDescription(context.getString(R.string.viserysTargaryen));
        viserysTargaryen.setIsLiked(false);
        viserysTargaryen.setLargeImage(R.drawable.viserys);
        viserysTargaryen.setBadAss("Sucks");
        viserysTargaryen.setCharacterReviews("");
        characterArrayList.add(viserysTargaryen);

        GOTCharacter jaqenHghar = new GOTCharacter();
        jaqenHghar.setName("Jaqen H'ghar");
        jaqenHghar.setSex(GENDER_MALE);
        jaqenHghar.setContinent(CONTINENT_ESSOS);
        jaqenHghar.setHouse(HOUSE_None);
        jaqenHghar.setDescription(context.getString(R.string.jaqenHghar));
        jaqenHghar.setIsLiked(false);
        jaqenHghar.setLargeImage(R.drawable.jaqen);
        jaqenHghar.setBadAss("Many-Faced Badass");
        jaqenHghar.setCharacterReviews("");
        characterArrayList.add(jaqenHghar);

        GOTCharacter margaeryTyrell = new GOTCharacter();
        margaeryTyrell.setName("Margaery Tyrell");
        margaeryTyrell.setSex(GENDER_FEMALE);
        margaeryTyrell.setContinent(CONTINENT_WESTEROS);
        margaeryTyrell.setHouse(HOUSE_TYRELL);
        margaeryTyrell.setDescription(context.getString(R.string.margaery));
        margaeryTyrell.setIsLiked(false);
        margaeryTyrell.setLargeImage(R.drawable.margaery);
        margaeryTyrell.setBadAss("Badass Queen");
        margaeryTyrell.setCharacterReviews("");
        characterArrayList.add(margaeryTyrell);

        GOTCharacter lorasTyrell = new GOTCharacter();
        lorasTyrell.setName("Loras Tyrell");
        lorasTyrell.setSex(GENDER_MALE);
        lorasTyrell.setContinent(CONTINENT_WESTEROS);
        lorasTyrell.setHouse(HOUSE_TYRELL);
        lorasTyrell.setDescription(context.getString(R.string.loras));
        lorasTyrell.setIsLiked(false);
        lorasTyrell.setLargeImage(R.drawable.loras);
        lorasTyrell.setBadAss("Flowery Badass");
        lorasTyrell.setCharacterReviews("");
        characterArrayList.add(lorasTyrell);

        GOTCharacter stannisBaratheon = new GOTCharacter();
        stannisBaratheon.setName("Stannis Baratheon");
        stannisBaratheon.setSex(GENDER_MALE);
        stannisBaratheon.setContinent(CONTINENT_WESTEROS);
        stannisBaratheon.setHouse(HOUSE_BARATHEON);
        stannisBaratheon.setDescription(context.getString(R.string.stannis));
        stannisBaratheon.setIsLiked(false);
        stannisBaratheon.setLargeImage(R.drawable.stannis);
        stannisBaratheon.setBadAss("Stern Badass");
        stannisBaratheon.setCharacterReviews("");
        characterArrayList.add(stannisBaratheon);

        GOTCharacter sansaStark = new GOTCharacter();
        sansaStark.setName("Sansa Stark");
        sansaStark.setSex(GENDER_FEMALE);
        sansaStark.setContinent(CONTINENT_WESTEROS);
        sansaStark.setHouse(HOUSE_STARK);
        sansaStark.setDescription(context.getString(R.string.sansa));
        sansaStark.setIsLiked(false);
        sansaStark.setLargeImage(R.drawable.sansa);
        sansaStark.setBadAss("Unfortunate Badass");
        sansaStark.setCharacterReviews("");
        characterArrayList.add(sansaStark);

        GOTCharacter ramsayBolton = new GOTCharacter();
        ramsayBolton.setName("Ramsay Bolton");
        ramsayBolton.setSex(GENDER_MALE);
        ramsayBolton.setContinent(CONTINENT_WESTEROS);
        ramsayBolton.setHouse(HOUSE_BOLTON);
        ramsayBolton.setDescription(context.getString(R.string.ramsay));
        ramsayBolton.setIsLiked(false);
        ramsayBolton.setLargeImage(R.drawable.ramsay);
        ramsayBolton.setBadAss("Pure Evil");
        ramsayBolton.setCharacterReviews("");
        characterArrayList.add(ramsayBolton);

        GOTCharacter rooseBolton = new GOTCharacter();
        rooseBolton.setName("Roose Bolton");
        rooseBolton.setSex(GENDER_MALE);
        rooseBolton.setContinent(CONTINENT_WESTEROS);
        rooseBolton.setHouse(HOUSE_BOLTON);
        rooseBolton.setDescription(context.getString(R.string.roose));
        rooseBolton.setIsLiked(false);
        rooseBolton.setLargeImage(R.drawable.roose);
        rooseBolton.setBadAss("Backstabber");
        rooseBolton.setCharacterReviews("");
        characterArrayList.add(rooseBolton);

        GOTCharacter davos = new GOTCharacter();
        davos.setName("Davos Seaworth");
        davos.setSex(GENDER_MALE);
        davos.setContinent(CONTINENT_WESTEROS);
        davos.setHouse(HOUSE_None);
        davos.setDescription(context.getString(R.string.davos));
        davos.setIsLiked(false);
        davos.setLargeImage(R.drawable.davos);
        davos.setBadAss("Captain Badass");
        davos.setCharacterReviews("");
        characterArrayList.add(davos);

        GOTCharacter robbStark = new GOTCharacter();
        robbStark.setName("Robb Stark");
        robbStark.setSex(GENDER_MALE);
        robbStark.setContinent(CONTINENT_WESTEROS);
        robbStark.setHouse(HOUSE_STARK);
        robbStark.setDescription(context.getString(R.string.robb));
        robbStark.setIsLiked(false);
        robbStark.setLargeImage(R.drawable.robb);
        robbStark.setBadAss("Northen Badass");
        robbStark.setCharacterReviews("");
        characterArrayList.add(robbStark);

        GOTCharacter bronn = new GOTCharacter();
        bronn.setName("Bronn");
        bronn.setSex(GENDER_MALE);
        bronn.setContinent(CONTINENT_WESTEROS);
        bronn.setHouse(HOUSE_None);
        bronn.setDescription(context.getString(R.string.bronn));
        bronn.setIsLiked(false);
        bronn.setLargeImage(R.drawable.bronn);
        bronn.setBadAss("Sellsword Badass");
        bronn.setCharacterReviews("");
        characterArrayList.add(bronn);

        GOTCharacter brienne = new GOTCharacter();
        brienne.setName("Lady Brienne");
        brienne.setSex(GENDER_FEMALE);
        brienne.setContinent(CONTINENT_WESTEROS);
        brienne.setHouse(HOUSE_None);
        brienne.setDescription(context.getString(R.string.brienne));
        brienne.setIsLiked(false);
        brienne.setLargeImage(R.drawable.brienne);
        brienne.setBadAss("Badass Knight");
        brienne.setCharacterReviews("");
        characterArrayList.add(brienne);

        GOTCharacter hodor = new GOTCharacter();
        hodor.setName("Hodor");
        hodor.setSex(GENDER_MALE);
        hodor.setContinent(CONTINENT_WESTEROS);
        hodor.setHouse(HOUSE_STARK);
        hodor.setDescription(context.getString(R.string.hodor));
        hodor.setIsLiked(false);
        hodor.setLargeImage(R.drawable.hodor);
        hodor.setBadAss("Badass Hodor");
        hodor.setCharacterReviews("");
        characterArrayList.add(hodor);

        GOTCharacter drogo = new GOTCharacter();
        drogo.setName("Khal Drogo");
        drogo.setSex(GENDER_MALE);
        drogo.setContinent(CONTINENT_ESSOS);
        drogo.setHouse(HOUSE_None);
        drogo.setDescription(context.getString(R.string.khal));
        drogo.setIsLiked(false);
        drogo.setLargeImage(R.drawable.khal);
        drogo.setBadAss("Horselord Badass");
        drogo.setCharacterReviews("");
        characterArrayList.add(drogo);

        GOTCharacter olennaTyrell = new GOTCharacter();
        olennaTyrell.setName("Olenna Tyrell");
        olennaTyrell.setSex(GENDER_FEMALE);
        olennaTyrell.setContinent(CONTINENT_WESTEROS);
        olennaTyrell.setHouse(HOUSE_TYRELL);
        olennaTyrell.setDescription(context.getString(R.string.olenna));
        olennaTyrell.setIsLiked(false);
        olennaTyrell.setLargeImage(R.drawable.olenna);
        olennaTyrell.setBadAss("Badass Grandma");
        olennaTyrell.setCharacterReviews("");
        characterArrayList.add(olennaTyrell);

        GOTCharacter tommenBaratheon = new GOTCharacter();
        tommenBaratheon.setName("Tommen Baratheon");
        tommenBaratheon.setSex(GENDER_MALE);
        tommenBaratheon.setContinent(CONTINENT_WESTEROS);
        tommenBaratheon.setHouse(HOUSE_BARATHEON);
        tommenBaratheon.setDescription(context.getString(R.string.tommen));
        tommenBaratheon.setIsLiked(false);
        tommenBaratheon.setLargeImage(R.drawable.tommen);
        tommenBaratheon.setBadAss("Chode");
        tommenBaratheon.setCharacterReviews("");
        characterArrayList.add(tommenBaratheon);

        return characterArrayList;
    }

}
