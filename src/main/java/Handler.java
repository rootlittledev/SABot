import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.ADMIN;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class Handler extends AbilityBot {
    private String username;
    private String password;

    private DBConnector dbConnector;

    Handler() {
        super(Config.telegram_auth_token, Config.bot_username);

        try {
            dbConnector = new DBConnector();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int creatorId() {
        return Config.user_id;
    }

    public Ability startCommand(){
        return Ability
                .builder()
                .name("start")
                .info("want to start")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Want to participate in the Quest?", ctx.chatId()))
                .build();
    }

    public Ability yesCommand(){

        return Ability
                .builder()
                .name("yes")
                .info("want to start")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    try {
                        newUser(ctx);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }

    public Ability addCommand(){

        return Ability
                .builder()
                .name("add")
                .info("want to start")
                .input(2)
                .locality(USER)
                .privacy(ADMIN)
                .action(ctx -> addAccount(ctx))
                .build();
    }

    private void getCredits(){
        Random random = new Random();

        int index = random.nextInt(Config.usernamesM.size());

        username = Config.usernamesM.get(index);
        Config.usernamesM.remove(index);

        int len = 12;
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );

        password = sb.toString();
    }

    private void addAccount(MessageContext ctx){
        try {
            DBConnector dbConnector = new DBConnector();
            getCredits();
            dbConnector.addUser(ctx.firstArg() + " " + ctx.secondArg(), password, Config.NONE);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void newUser(MessageContext ctx) throws SQLException {

        List<Users> users = dbConnector.getUsers();
        List<Users> registeredUsers = dbConnector.getRegisteredUsers();

        for (Users user : users) {
            if (user.getTelegramId() == ctx.user().id()){

                silent.send("You already have a account!", ctx.chatId());
                return;

                /*switch (Config.tryed.get(ctx.chatId())){
                    case Config.GRANTED:{
                        silent.send("You already have a account!", ctx.chatId());
                        return;
                    }
                    case Config.PENDING:{
                        silent.send("Your request is in process.", ctx.chatId());
                        return;
                    }
                    case Config.DENIED:{
                        silent.send("You don't match the requirements, maybe try later.", ctx.chatId());
                        return;
                    }
                }*/
            }
        }

            System.out.println(registeredUsers.size());

            Random random = new Random();
            int index = random.nextInt(users.size());



            dbConnector.registerUser(registeredUsers.get(index).getId(), ctx.user().id(), Config.GRANTED);

            silent.send("Your login is and password are: " + registeredUsers.get(index).getUsername() + ", " + registeredUsers.get(index).getPassword() + ". " +
                    "Don't share this information!", ctx.chatId());
    }
}
