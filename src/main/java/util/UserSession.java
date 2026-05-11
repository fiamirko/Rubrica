package util;

public class UserSession {
	private static int userId;
    private static String username;

    public static void setInstance(int id, String name) {
        userId = id;
        username = name;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void cleanSession() {
        userId = 0;
        username = null;
    }
}
