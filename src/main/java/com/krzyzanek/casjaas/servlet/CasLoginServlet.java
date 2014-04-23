package com.krzyzanek.casjaas.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Libor Krzyzanek
 */
@WebServlet(name = "CasLoginServlet", urlPatterns = {"/login/cas"})
public class CasLoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = "/";
		if (req.getUserPrincipal() == null) {
			url = "/login/cas";
		}
		if (!resp.isCommitted()) {
			resp.sendRedirect(url);
		}

	}

}
