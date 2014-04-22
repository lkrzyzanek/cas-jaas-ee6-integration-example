package com.krzyzanek.casjaas.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

/**
 * @author Libor Krzyzanek
 */
@WebServlet(name = "SecuredServlet", urlPatterns = {"/secured-servlet"})
@ServletSecurity(@HttpConstraint(rolesAllowed = {"user"}))
public class SecuredServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Principal authUser = req.getUserPrincipal();

		PrintWriter out = resp.getWriter();
		out.write("<html><body>Secured Servlet, auth user: ");
		out.write(authUser != null ? authUser.getName() : "N/A");
		out.write("</body></html>");
		out.close();
	}

}
