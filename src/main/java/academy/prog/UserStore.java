package academy.prog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserStore {
    private static final Set<String> onlineUsers = new HashSet<>();
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("User1", "password1");
        users.put("User2", "password2");
        users.put("User3", "password3");
    }

    public static boolean authenticate(String login, String password) {
        String storedPassword = users.get(login);
        if (storedPassword != null && storedPassword.equals(password)) {
            onlineUsers.add(login);
            return true;
        }
        return false;
    }

    public static Set<String> getOnlineUsers() {
        return new HashSet<>(onlineUsers);
    }
}
