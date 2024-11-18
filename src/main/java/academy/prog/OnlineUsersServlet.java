package academy.prog;

import com.google.gson.Gson;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Set;

public class OnlineUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Set<String> onlineUsers = UserStore.getOnlineUsers();

        String json = new Gson().toJson(onlineUsers);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
