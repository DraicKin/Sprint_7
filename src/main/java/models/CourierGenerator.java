package models;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public static Courier getRandom() {
       return new Courier(RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10));
    }

    public static Courier getRandomWithoutLogin() {
        return new Courier(RandomStringUtils.randomAlphabetic(10),
                null,
                RandomStringUtils.randomAlphabetic(10));
    }

    public static Courier getRandomWithoutPassword() {
        return new Courier(RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                null);
    }
}
