package com.zim.cmn;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



public interface WishHandler {

Logger LOG = Logger.getLogger(WishHandler.class);
	
	public void doServiceHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;


    public void do_delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void do_selectone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void do_retrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void do_insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
   
    
}
