package app.servlets;

import app.dtos.UserHeroDto;
import app.models.UserHero;
import app.dtos.UserDtoResponse;
import app.repos.CharacterRepo;
import app.repos.CharacterRepoImpl;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;

public class DuelRoomServlet extends HttpServlet {
    @Resource(name="jdbc/test_game")
    private DataSource dataSource;
    private CharacterRepo charRepo;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            charRepo = new CharacterRepoImpl(dataSource);
        }
        catch (Exception ex){
            throw new ServletException(ex);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserDtoResponse userDtoResponse = (UserDtoResponse)session.getAttribute("user");
        int userId = userDtoResponse.getId();
        UserHero userHero = this.charRepo.getCharacterByUserId(userId);
        if (userHero == null) {
            userHero = this.charRepo.createCharacter(userId);
        }
        userDtoResponse.setUserHero(new UserHeroDto(userHero.getRating(),userHero.getDamageMultiplier(), userHero.getHealth()));
        session.setAttribute("user", userDtoResponse);
        req.setAttribute("userHero", userHero);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/duels.jsp");
        requestDispatcher.forward(req, resp);
    }


}

