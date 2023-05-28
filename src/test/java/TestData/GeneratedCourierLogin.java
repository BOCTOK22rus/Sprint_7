package TestData;

import java.util.Random;

public class GeneratedCourierLogin {

    public String courierLogin = generateRandomLogin();

    public String generateRandomLogin() {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
