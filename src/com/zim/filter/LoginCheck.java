package com.zim.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.zim.member.MemberVO;

/**
 * Servlet Filter implementation class LoginCheck
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
					, description = "로그인 체크"
					, urlPatterns = { 
							"/member/edit/*",
							"/member/quit/*",
							"/member/retrieve/*",
							"/quit/*",
							"/host/*",
							"/join/*",
							"/wishlist/*",
							"/retrieve/*",
							"/member/member.do*",
							"/writing/*",
							"/product/*"
					})
public class LoginCheck implements Filter {
	final Logger LOG = Logger.getLogger(this.getClass());
    /**
     * Default constructor. 
     */
    public LoginCheck() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		LOG.debug("destroy");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("-----------------------------");
		LOG.debug("doFilter");
		LOG.debug("-----------------------------");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		
		boolean isLogin = false;
		if(null!=session){
			if(null != session.getAttribute("user")){
				MemberVO memberVO = (MemberVO) session.getAttribute("user");
				LOG.debug("-----------------------------");
				LOG.debug("MemberVO : "+memberVO);
				LOG.debug("-----------------------------");
				isLogin = true;
			}
		}
		//Login되어 있는 경우
		if(isLogin==true){
			chain.doFilter(request, response);
			
		}else{//Login 안되어 있는 경우
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인이 필요합니다.'); location.href='/ZIMZALABIM/mainpage/mainpage_view.jsp';</script>");
			out.flush();
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("destroy");
	}

}
