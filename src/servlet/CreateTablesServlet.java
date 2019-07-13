package servlet;

import backend.DatabaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateTablesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uid = null;
    private DatabaseController db = new DatabaseController();
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

            String tableName = req.getParameter("tableName");
            System.out.println(tableName);

            //Takes string and splits into players
            ArrayList<String> playerNames = new ArrayList<String>(Arrays.asList(req.getParameter("players").split(",")));
            for (String test:playerNames) {
                System.out.println(test);
            }

            int plunkLimit = Integer.parseInt(req.getParameter("plunk"));
            System.out.println(plunkLimit);

            Object[] resultArray = new Object[]{false, -1};
            try{
                resultArray = db.createTable(tableName, Integer.parseInt(uid), plunkLimit);
            }catch(Exception e){
                System.out.println("FAILED TABLE CREATE.");
            }

            //set the table id from the array
            int tableID = (int)resultArray[1];

            if((boolean)resultArray[0] == false){
                System.out.println("FAILED TABLE CREATE.");
            }else{
                System.out.println(tableID);

                resp.sendRedirect(req.getContextPath() + "/table");
                req.getSession().setAttribute("tableID", Integer.toString(tableID));
            }
           // req.setAttribute("username", usernameCap);
            //req.setAttribute("idea", response);

        }

    }
}