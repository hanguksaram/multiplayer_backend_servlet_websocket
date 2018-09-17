package app.servlets;

import app.Utils.Utils;
import app.dtos.UserDtoRequest;
import app.dtos.UserDtoResponse;
import app.models.DbModel;
import app.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import app.repos.BaseRepo;
import app.repos.UserRepoImpl;
import org.apache.commons.codec.digest.Crypt;


public class IndexServlet extends HttpServlet {

    private BaseRepo<User, UserDtoRequest> userRepo;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userRepo = new UserRepoImpl();
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
        HttpSession session = req.getSession(true);
        if (name == "" || password == "")
            handleError("Fill empty form fields", req, resp, session, null);
        if (name.length() > 45) //varchar(45) in db
            handleError("Dont hack our html :D", req, resp, session, null);
        UserDtoRequest userDto = new UserDtoRequest(name, password);
        DbModel<User> userModel = getUser(userDto);
        if (!userModel.isSuccess())
            handleError("Db error", req, resp, session, userModel);
        User user = userModel.getEntity();
        if (user.getId() != 0){
            if (!checkPassword(user, password))
                handleError("Wrong Password", req, resp, session, userModel);
        }
        else
            userModel = signUp(userDto);
        if (!userModel.isSuccess())
            handleError("Db error", req, resp, session, userModel);

        UserDtoResponse userDtoResponse = new UserDtoResponse(userModel.getEntity().getId(), userModel.getEntity().getLogin());
        session.setAttribute("user", userDtoResponse);
        session.setMaxInactiveInterval(20 * 60);
        Cookie sessionCookie = new Cookie("userSession", String.valueOf(userDtoResponse.getId()));
        sessionCookie.setMaxAge(20 * 60);
        resp.addCookie(sessionCookie);
        Utils.saveSqlLogs(session, userModel);
        resp.sendRedirect("/menu");

    }
    private DbModel<User> getUser(UserDtoRequest userDto) {
         return this.userRepo.getEntityByName(userDto.getLogin());
    }
    private void handleError(String error, HttpServletRequest req, HttpServletResponse resp, HttpSession sess, DbModel<User> model) throws ServletException, IOException  {
        req.setAttribute("error", error);
        if (model != null)
            Utils.saveSqlLogs(sess, model);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/index.jsp");
        requestDispatcher.forward(req, resp);
    }
    private boolean checkPassword(User user, String password) {
        String encriptedPassword = Crypt.crypt(password, "secretSalt");
        return user.getPassword().equals(encriptedPassword);
    }
    private DbModel<User> signUp(UserDtoRequest userDto) {
        userDto.setPassword(Crypt.crypt(userDto.getPassword(), "secretSalt"));
        return this.userRepo.createEntityByDto(userDto);
    }


}
