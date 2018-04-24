class Users {
    private int id;
    private String username;
    private String password;
    private String status;
    private int telegramId;

    public Users(int id, String username, String password, String status, int telegramId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.telegramId = telegramId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(int telegramId) {
        this.telegramId = telegramId;
    }
}
