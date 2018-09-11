package app.servlets;

import app.dtos.UserDtoRequest;
import app.dtos.UserDtoResponse;
import app.models.User;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import app.repos.UserRepo;
import app.repos.UserRepoImpl;
import org.apache.commons.codec.digest.Crypt;


public class IndexServlet extends HttpServlet {

    @Resource(name="jdbc/test_game")
    private DataSource dataSource;
    private UserRepo userRepo;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userRepo = new UserRepoImpl(dataSource);
        }
        catch (Exception ex){
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logout") != null) {
            Cookie[] cookies = req.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                    cookie.setMaxAge(1);
                    resp.addCookie(cookie);
                }
            }
            HttpSession session = req.getSession(false);
            if(session != null){
                session.invalidate();
            }
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("login");
        String password = req.getParameter("password");
        name = name.toLowerCase();

        if (name == "" || password == "")
            handleError("Fill empty form fields", req, resp);
        if (name.length() > 45) //varchar(45) in db
            handleError("Dont hack our html :D", req, resp);
        UserDtoRequest userDto = new UserDtoRequest(name, password);
        User user = getUser(userDto);
        if (user != null){
            if (!checkPassword(user, password))
               handleError("Wrong Password", req, resp);
        }
        else
            user = signUp(userDto);
        if (user == null)
            handleError("Db error during user creating", req, resp);

        UserDtoResponse userDtoResponse = new UserDtoResponse(user.getId(), user.getLogin());
        HttpSession session = req.getSession(true);
        session.setAttribute("user", userDtoResponse);
        session.setMaxInactiveInterval(20 * 60);
        Cookie sessionCookie = new Cookie("userSession", String.valueOf(userDtoResponse.getId()));
        sessionCookie.setMaxAge(20 * 60);
        resp.addCookie(sessionCookie);
        resp.sendRedirect("/menu");

    }
    private User getUser(UserDtoRequest userDto) {
         return this.userRepo.getUserByName(userDto.getLogin());
    }
    private void handleError(String error, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        req.setAttribute("error", error);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/index.jsp");
        requestDispatcher.forward(req, resp);
    }
    private boolean checkPassword(User user, String password) {

            String encriptedPassword = Crypt.crypt(password, "secretSalt");
            return user.getPassword().equals(encriptedPassword);
    }

    private User signUp(UserDtoRequest userDto) {
        userDto.setPassword(Crypt.crypt(userDto.getPassword(), "secretSalt"));
        return this.userRepo.createUser(userDto);
    }


}
