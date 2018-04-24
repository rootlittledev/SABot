
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Config {

    static final String url = "jdbc:mysql://localhost:3306/users";
    static final String login = "ethero";
    static final String password = "root";

    static final int user_id = 436072897;
    static final String telegram_auth_token = "453864386:AAEqxCtp6HGthXzIDV0tukRMg9jIKm5j_S8";
    static final String bot_username = "SomeWhereUderTheStoneIsABot";

    static final String PENDING = "pending";
    static final String GRANTED = "granted";
    static final String DENIED = "denied";

    static final String NONE = "none";

    static List<String> usernamesM = new ArrayList<>(Arrays.asList("Agent X.323", "Alex Rider", "Ali Imran", "Basil Argyros",
                                                  "Carl Hamilton", "Daniel Marchant", "David Shirazi", "Drongo",
                                                  "George Smiley", "Hal Ambler", "Jack Ryan", "James Adams"));
    static HashMap<Integer, String> tryed = new HashMap<>();
}
