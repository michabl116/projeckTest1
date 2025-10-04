package model;

public class CurrentUser {

    private static CurrentUser instance;
    private User currentUser;

    private CurrentUser(User user) {
        this.currentUser = user;
    }

    public static void set(User user) {
        instance = new CurrentUser(user);
    }

    public static User get() {
        if (instance == null) {
            return null;
        }
        return instance.currentUser;
    }
}
