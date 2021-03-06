
package org.onebeartoe.quatro.engrave.settings;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.onebeartoe.io.ObjectSaver;
import org.onebeartoe.quatro.engrave.ApplicationProfile;
import org.onebeartoe.quatro.engrave.NejeEngraver;
import static org.onebeartoe.neje.engrave.StartEngraverServlet.APPLICTION_PROFILE_CONTEXT_KEY;

/**
 * This servlet handles requests to display and update the settings for the 
 * application.
 */
@WebServlet(urlPatterns = {"/settings/*"})
public class SettingsServlet extends HttpServlet
{
    private Logger logger;
    
    private ApplicationProfile applicationProfile;
    
    private NejeEngraver engraver;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {      
        doResponse(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String stillsDirtory = request.getParameter("EzGraverCli");
        engraver.setCliExecutable(stillsDirtory);

        
        File outfile = new File(applicationProfile.getBaseDirectory(), ApplicationProfile.CONFIGURATION_FILENAME);
        ObjectSaver.encodeObject(engraver, outfile);
        
        String saveMessages = "Settings changes were saved.";        
        request.setAttribute("saveMessages", saveMessages);
        
        doResponse(request, response);
    }

    private void doResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        File bd = applicationProfile.getBaseDirectory();
        String imageDirectory = bd.getAbsolutePath();
        request.setAttribute("baseDir", imageDirectory);
        
        request.setAttribute("EzGraverCli", engraver.getCliExecutable());
                
        ServletContext c = getServletContext();
        RequestDispatcher rd = c.getRequestDispatcher("/WEB-INF/jsp/settings/index.jsp");
        rd.forward(request, response);        
    }

    @Override
    public void init() throws ServletException
    {
        super.init();
        
        logger = Logger.getLogger(getClass().getName());                
        
        ServletContext servletContext = getServletContext();

        applicationProfile = (ApplicationProfile) servletContext.getAttribute(APPLICTION_PROFILE_CONTEXT_KEY);
        
        engraver = applicationProfile.getEngraver();
    }
}