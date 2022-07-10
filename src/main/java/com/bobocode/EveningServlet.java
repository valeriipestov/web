package com.bobocode;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/evening")
public class EveningServlet extends HttpServlet {

    private static final String CUSTOM_SESSION_ID = "custom_idasdasd";

    private Map<String, String> sessionParams = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var name = req.getParameter("name");
        String session_id;
        if (req.getCookies() != null) {
            var cookie_session = Arrays.stream(req.getCookies())
                    .filter(c -> c.getName().equals(CUSTOM_SESSION_ID))
                    .findFirst();
            session_id = cookie_session.map(Cookie::getValue)
                    .orElse(UUID.randomUUID().toString());
        } else {
            session_id = UUID.randomUUID().toString();
        }
        resp.addCookie(new Cookie(CUSTOM_SESSION_ID, session_id));

        if (name == null) {
            name = sessionParams.get(session_id) != null ? sessionParams.get(session_id) : "Buddy";
        } else {
            sessionParams.put(session_id, name);
        }

        var writer = resp.getWriter();
        writer.printf("Good evening, %s", name);
    }
}
