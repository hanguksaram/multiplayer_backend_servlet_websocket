package app.servlets;

import app.Utils.Utils;
import app.dtos.UserHeroDto;
import app.models.DbModel;
import app.models.UserHero;
import app.dtos.UserDtoResponse;
import app.repos.BaseRepo;
import app.repos.CharacterRepoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class DuelRoomServlet extends HttpServlet {

    private BaseRepo<UserHero, UserDtoResponse> charRepo;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            charRepo = new CharacterRepoImpl();
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
        DbModel<UserHero> heroModel = this.charRepo.getEntityById(userId);
        if (heroModel.getEntity().getId() == 0) {
            heroModel = this.charRepo.createEntityById(userId);
        }
        userDtoResponse.setUserHero(new UserHeroDto(heroModel.getEntity().getRating(),heroModel.getEntity().getDamageMultiplier(), heroModel.getEntity().getHealth()));
        session.setAttribute("user", userDtoResponse);
        req.setAttribute("userHero", heroModel.getEntity());
        Utils.saveSqlLogs(session, heroModel);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/duels.jsp");
        requestDispatcher.forward(req, resp);
    }


}

