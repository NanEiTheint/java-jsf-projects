package com.mmit.webfilter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mmit.bean.LoginBean;

@WebFilter({"/public/checkout.xhtml","/public/my-order.xhtml"})
public class CheckOutFilter implements Filter{
	
	@Inject
	private LoginBean loginBean;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			if(loginBean.getLoginUser().getId()==0)
			{
				HttpServletRequest req=(HttpServletRequest) request;
				//HttpServletResponse rep=(HttpServletResponse) response;
				req.getServletContext().getRequestDispatcher("/login.xhtml").forward(request, response);
			}
			else
			{
				chain.doFilter(request, response);
			}
		
	}

}
