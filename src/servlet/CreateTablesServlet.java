package servlet;

import backend.Database.DatabaseFactory;
import backend.Entities.Account;
import backend.Entities.Player;
import backend.Entities.Table;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class CreateTablesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uid = null;
    private DatabaseFactory db = new DatabaseFactory();
    ArrayList<String> accountInfo = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        uid = (String) req.getSession().getAttribute("uid"); //session stuff
        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {



			//req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);
            req.getRequestDispatcher("/_view/createtable.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        uid = (String) req.getSession().getAttribute("uid"); //session stuff

        if (uid == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            Account account = new Account();
            account.populateAccountData(Integer.parseInt(uid));
            Player player = account.getPlayerFromAccount();

            Table table = new Table();

            String tableName = req.getParameter("tableName");
            //System.out.println(tableName);

            //Takes string and splits into players
            /*ArrayList<String> playerNames = new ArrayList<String>(Arrays.asList(req.getParameter("players").split(",")));
            for (String test:playerNames) {
                System.out.println(test);
            }*/

            int plunkLimit = Integer.parseInt(req.getParameter("plunk"));
            System.out.println(plunkLimit);

            table = table.createTable(tableName, Integer.parseInt(uid), plunkLimit);
            int tableID;

            if(table == null){
                System.out.println("FAILED TABLE CREATE.");
            }else{
                tableID = table.getTID();
                //System.out.println(tableID);

                resp.sendRedirect(req.getContextPath() + "/table");
                req.getSession().setAttribute("tid", Integer.toString(tableID));
            }

        }

    }
}