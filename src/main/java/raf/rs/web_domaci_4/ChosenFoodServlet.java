package raf.rs.web_domaci_4;

import raf.rs.web_domaci_4.sessions.SessionManager;
import raf.rs.web_domaci_4.database.ServerDatabase;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "ChosenFoodServlet", value = "/ChosenFoodServlet")
public class ChosenFoodServlet extends HttpServlet {

    private SessionManager sessionManager;

    @Override
    public void init() throws ServletException {
        super.init();
        sessionManager = SessionManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        InputStream is = this.getClass().getResourceAsStream("/password.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String password = "";

        String line;
        while((line=br.readLine()) != null) {
            password = line;
        }

        PrintWriter out = response.getWriter();
        ServerDatabase database = ServerDatabase.getInstance();

        Iterator <Map.Entry<String, Integer>> it;

        if (request.getParameter("password") != null && request.getParameter("password").equals(password)) {
            out.println("<html><body>");

            out.println("<p>Monday</p>");
            it = database.getMonday().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                out.println("<p>"+entry.getKey()+" : "+entry.getValue()+"</p>");
            }

            out.println("<p>Tuesday</p>");
            it = database.getTuesday().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                out.println("<p>"+entry.getKey()+" : "+entry.getValue()+"</p>");
            }

            out.println("<p>Wednesday</p>");
            it = database.getWednesday().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                out.println("<p>"+entry.getKey()+" : "+entry.getValue()+"</p>");
            }

            out.println("<p>Thursday</p>");
            it = database.getThursday().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                out.println("<p>"+entry.getKey()+" : "+entry.getValue()+"</p>");
            }

            out.println("<p>Friday</p>");
            it = database.getFriday().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                out.println("<p>"+entry.getKey()+" : "+entry.getValue()+"</p>");
            }

            out.println("<form method=\"POST\" action=\"/ChosenFoodServlet\">");
            out.println("<button>Restart</button></form>");

            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h1>No</h1>");
            out.println("</body></html>");
        }

        br.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServerDatabase.getInstance().clear();

        Iterator<String> iterator = sessionManager.getValidIds().iterator();

        while (iterator.hasNext()) {
            sessionManager.getInvalidIds().add(iterator.next());
        }

        sessionManager.getValidIds().clear();

        response.sendRedirect("/ChooseFoodServlet");
    }

}
