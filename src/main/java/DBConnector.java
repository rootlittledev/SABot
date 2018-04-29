import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DBConnector {


    private Connection connection;

    DBConnector() throws SQLException, ClassNotFoundException {
        //Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.connection = DriverManager.getConnection(Config.url, Config.login, Config.password);
    }
    void addUser(String username, String password, String status) throws SQLException {
        String sqlSelect = "INSERT INTO accounts (username, password, status, telegram_id) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sqlSelect);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, status);
        preparedStatement.setString(4, null);
        preparedStatement.executeUpdate();

    }

    void registerUser(int id, int telegramId, String status) throws SQLException {
        String sqlUpdate = "UPDATE accounts SET telegram_id = ?, status = ? WHERE id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sqlUpdate);
        preparedStatement.setInt(1, telegramId);
        preparedStatement.setString(2, status);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    List<Users> getUsers() throws SQLException {
        List<Users> users = new ArrayList<>();

        String sqlSelect = "SELECT * FROM accounts";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
        users.add(new Users(resultSet.getInt(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getString(4),
                resultSet.getInt(5)));
    }

        return users;
}

    List<Users> getRegisteredUsers() throws SQLException {
        List<Users> users = new ArrayList<>();

        String sqlSelect = "SELECT * FROM accounts WHERE telegram_id IS NULL";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            users.add(new Users(resultSet.getInt(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getString(4),
                                resultSet.getInt(5)));
        }
        return users;
    }
}
