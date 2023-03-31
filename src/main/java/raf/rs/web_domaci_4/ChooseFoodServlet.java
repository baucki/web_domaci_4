package raf.rs.web_domaci_4;

import raf.rs.web_domaci_4.sessions.SessionManager;
import raf.rs.web_domaci_4.database.ServerDatabase;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChooseFoodServlet", value = "/ChooseFoodServlet")
public class ChooseFoodServlet extends HttpServlet {

    private ServerDatabase database;

    private SessionManager sessionManager;

    @Override
    public void init() throws ServletException {
        super.init();
        database = ServerDatabase.getInstance();
        sessionManager = SessionManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        List<String> monday = fileLoader("monday");
        List<String> tuesday = fileLoader("tuesday");
        List<String> wednesday = fileLoader("wednesday");
        List<String> thursday = fileLoader("thursday");
        List<String> friday = fileLoader("friday");

        List<List<String>> days = new ArrayList<>();

        days.add(monday);
        days.add(tuesday);
        days.add(wednesday);
        days.add(thursday);
        days.add(friday);

        String session = request.getSession().getId();

        if (sessionManager.getInvalidIds().contains(session)) {
            request.getSession().invalidate();
            session = request.getSession().getId();
            sessionManager.getValidIds().add(session);
        } else {
            sessionManager.getValidIds().add(session);
        }

        PrintWriter out = response.getWriter();

        if (request.getSession().getAttribute("food0") != null &&
                request.getSession().getAttribute("food1") != null &&
                request.getSession().getAttribute("food2") != null &&
                request.getSession().getAttribute("food3") != null &&
                request.getSession().getAttribute("food4") != null) {

            out.println("<html><body>");
            out.println("<h1>You have ordered your food correctly!</h1>");
            out.println("<p>Monday:"+request.getSession().getAttribute("food0")+"</p>");
            out.println("<p>Tuesday:"+request.getSession().getAttribute("food1")+"</p>");
            out.println("<p>Wednesday:"+request.getSession().getAttribute("food2")+"</p>");
            out.println("<p>Thursday:"+request.getSession().getAttribute("food3")+"</p>");
            out.println("<p>Friday:"+request.getSession().getAttribute("food4")+"</p>");
            out.println("</body></html>");

        } else {

            out.println("<html><body>");
            out.println("<form method=\"POST\" action=\"/ChooseFoodServlet\">");
            out.println("<h1> " + "Choose your food" + "</h1>");

            int i = 0;
            String currDay;

            for (List<String> day : days) {
                if (i == 0) currDay = "Monday";
                else if (i == 1) currDay = "Tuesday";
                else if (i == 2) currDay = "Wednesday";
                else if (i == 3) currDay = "Thursday";
                else currDay = "Friday";

                out.println("<p>" + currDay + "</p>");
                out.println("<select name=\"food"+i+"\" id=\"food"+i+"\">");
                for (String food : day) {
                    out.println("<option>" + food + "</option>");
                }
                out.println("</select>");
                i++;
            }

            out.println("<button type=\"submit\">Confirm</button>");

            out.println("</form></body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("food0") == null || request.getParameter("food0").equals("") ||
                request.getParameter("food1") == null || request.getParameter("food1").equals("") ||
                request.getParameter("food2") == null || request.getParameter("food2").equals("") ||
                request.getParameter("food3") == null || request.getParameter("food3").equals("") ||
                request.getParameter("food4") == null || request.getParameter("food4").equals("")) {
            response.setStatus(403);
            return;
        }

        if (request.getSession().getAttribute("food0") == null &&
                request.getSession().getAttribute("food1") == null &&
                request.getSession().getAttribute("food2") == null &&
                request.getSession().getAttribute("food3") == null &&
                request.getSession().getAttribute("food4") == null) {

            request.getSession().setAttribute("food0", request.getParameter("food0"));
            database.getMonday().put(request.getParameter("food0"), database.getMonday().containsKey(request.getParameter("food0")) ? database.getMonday().get(request.getParameter("food0")) + 1 : 1);

            request.getSession().setAttribute("food1", request.getParameter("food1"));
            database.getTuesday().put(request.getParameter("food1"), database.getTuesday().containsKey(request.getParameter("food1")) ? database.getTuesday().get(request.getParameter("food1")) + 1 : 1);

            request.getSession().setAttribute("food2", request.getParameter("food2"));
            database.getWednesday().put(request.getParameter("food2"), database.getWednesday().containsKey(request.getParameter("food2")) ? database.getWednesday().get(request.getParameter("food2")) + 1 : 1);

            request.getSession().setAttribute("food3", request.getParameter("food3"));
            database.getThursday().put(request.getParameter("food3"), database.getThursday().containsKey(request.getParameter("food3")) ? database.getThursday().get(request.getParameter("food3")) + 1 : 1);

            request.getSession().setAttribute("food4", request.getParameter("food4"));
            database.getFriday().put(request.getParameter("food4"), database.getFriday().containsKey(request.getParameter("food4")) ? database.getFriday().get(request.getParameter("food4")) + 1 : 1);
        }

        response.sendRedirect("/ChooseFoodServlet");
    }

    private List<String> fileLoader(String path) {

        List<String> listToReturn = new ArrayList<>();

        try {
            InputStream is = this.getClass().getResourceAsStream("/menu/" + path + ".txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                listToReturn.add(line);
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return listToReturn;
    }
}
