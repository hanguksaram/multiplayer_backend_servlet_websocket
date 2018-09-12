package app.servlets;

import app.dtos.UserDtoResponse;
import app.models.UserHero;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public class DuelFieldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserDtoResponse userDtoResponse = (UserDtoResponse)session.getAttribute("user");
        if (userDtoResponse != null) {
            req.setAttribute("rating", userDtoResponse.getUserHero().getRating());
            req.setAttribute("name", userDtoResponse.getName());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/duelfield.jsp");
            requestDispatcher.forward(req, resp);
        }
        resp.sendRedirect("/index");

    }
}
