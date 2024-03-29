/**
* @Class Name : WishlistDao.java
* @Description : 
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ---------   ---------   -------------------------------
* @ 2019. 7. 29.           최초생성
*
* @author Zimzalabim
* @since 2019. 7. 29. 
* @version 1.0
* @see
*
*  Copyright (C) by HR. KIM All right reserved.
*/
package com.zim.wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zim.cmn.JDBCReturnReso;
import com.zim.cmn.ConnectionMaker;
import com.zim.cmn.DTO;
import com.zim.cmn.SearchVO;
import com.zim.cmn.WorkDiv;
import com.zim.product.ProductVO;

/**
 * @author sist
 *
 */
public class WishlistDao implements WorkDiv {

	private final Logger LOG = Logger.getLogger(WishlistDao.class);
	private ConnectionMaker connectionMaker;

	public WishlistDao() {
		connectionMaker = new ConnectionMaker();
	}

	@Override
	public int do_insert(DTO dto) {
		WishlistVO vo = (WishlistVO)dto;
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO WISHLIST (   \n");
			sb.append("            USER_ID      \n");
			sb.append("            ,PRODUCT_NO  \n");
			sb.append("            ,REG_DT      \n");
			sb.append("            ) VALUES(    \n");
			sb.append("            ?,           \n");
			sb.append("            ?,           \n");
			sb.append("            SYSDATE      \n");
			sb.append("            )            \n");
			
			LOG.debug("1.============================");
			LOG.debug("1.query\n" + sb.toString());
			LOG.debug("1.============================");
			
			conn = connectionMaker.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getUser_id());
			pstmt.setString(2, vo.getProduct_no());
			
			LOG.debug("2.============================");
			LOG.debug("2.param:" + vo.toString());
			LOG.debug("2.============================");
			
			flag = pstmt.executeUpdate();
			
			LOG.debug("3.============================");
			LOG.debug("3.flag:" + flag);
			LOG.debug("3.============================");
		} catch(SQLException e){
			LOG.debug("======================");
			LOG.debug("SQLException:" + e.toString());
			LOG.debug("======================");
		}finally{
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}
		return flag;
	}

	@Override
	public int do_update(DTO dto) {

		return 0;
	}

	@Override
	public int do_delete(DTO dto) {
		WishlistVO vo = (WishlistVO) dto;
		// System.out.println("vo2312312321321======="+vo);
		int flag = 0;
		Connection conn = null;

		PreparedStatement pstmt = null;

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM WISHLIST  \n");
		sb.append("WHERE PRODUCT_NO = ?  \n");

		try {
			conn = connectionMaker.getConnection();
			conn.setAutoCommit(false);

			LOG.debug("1======================");
			LOG.debug("query:\n" + sb.toString());
			LOG.debug("1======================");
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, vo.getProduct_no());
			LOG.debug("2======================");
			LOG.debug("param, produNO=" + vo.getProduct_no());
			LOG.debug("2======================");

			flag = pstmt.executeUpdate();
			if (flag > 0) {
				LOG.debug("3======================");
				LOG.debug("transaction=" + conn);
				LOG.debug("3======================");

			} else {

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}
		LOG.debug("3=====================");
		LOG.debug("flag:" + flag);
		LOG.debug("3=====================");

		return flag;
	}

	@Override
	public WishlistVO do_selectOne(DTO dto) {
		WishlistVO vo = (WishlistVO) dto;
		WishlistVO outVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT   A.product_no,           \n");                                                
			sb.append("      B.product_nm,              \n");                          
			sb.append("      A.reg_dt                   \n");                                                                   
			sb.append("FROM WISHLIST A JOIN PRODUCT B   \n");
			sb.append("ON A.product_no =B.product_no    \n");
			sb.append("WHERE A.product_no = ?           \n");

			conn = connectionMaker.getConnection();
			LOG.debug("1.============================");
			LOG.debug("1.query: \n" + sb.toString());
			LOG.debug("1.============================");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, vo.getProduct_no());
			LOG.debug("2.============================");
			LOG.debug("2.param product_no=" + vo);
			LOG.debug("2.============================");

			rs = pstmt.executeQuery();
			if (rs.next()) {
				outVO = new WishlistVO();
				outVO.setProduct_no(rs.getString("product_no"));
				outVO.setProductNm(rs.getString("product_nm"));
				outVO.setReg_dt(rs.getString("reg_dt"));
				LOG.debug("--------------------------------------------------------------");
				LOG.debug("3 do_retrieve outVO \n:"+outVO);
				LOG.debug("--------------------------------------------------------------");
			}

		} catch (SQLException e) {
			LOG.debug("===================");
			LOG.debug("SQLException=" + e.getMessage());
			LOG.debug("===================");
		} finally {
			JDBCReturnReso.close(rs);
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}
		return outVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zim.cmn.WorkDiv#do_retrieve(com.zim.cmn.DTO)
	 */
	@Override
	public List<WishlistVO> do_retrieve(DTO dto) {
		List<WishlistVO> list = new ArrayList<>();
		SearchVO vo = (SearchVO) dto;
		WishlistVO outVO=null;

		Connection conn = null;// db 연결
		PreparedStatement pstmt = null;// query수행
		ResultSet rs = null;// 결과처리



		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT B.rnum as num,                                 	   \n"); 
		sb.append("      B.product_no,                                         \n");
		sb.append("      p.product_nm,                                         \n");
		sb.append("      B.reg_dt                                              \n");
		sb.append("   FROM(                                                    \n");
		sb.append("         SELECT ROWNUM AS rnum,                             \n");
		sb.append("                   A.*                                      \n");
		sb.append("         FROM(                                              \n");
		sb.append("            SELECT *                                        \n");
		sb.append("            FROM wishlist                                   \n");
		sb.append("            --SEARCH CONDITION                              \n");
		sb.append("            WHERE user_id like ?                            \n");
		sb.append("            ) A                                             \n");
		sb.append("            WHERE ROWNUM <=(?*(?-1)+?)   )B JOIN product p  \n");
		sb.append("            ON B.product_no = p.product_no                  \n");
		sb.append("   WHERE rnum >=(?*(?-1)+1)                                 \n");
		sb.append("            ORDER BY num asc                        	       \n");

		try {
			conn = connectionMaker.getConnection();
			pstmt = conn.prepareStatement(sb.toString());

				pstmt.setString(1, vo.getSearchWord());
				pstmt.setInt(2, vo.getPageSize());
				pstmt.setInt(3, vo.getPageNum());
				pstmt.setInt(4, vo.getPageSize());
				pstmt.setInt(5, vo.getPageSize());
				pstmt.setInt(6, vo.getPageNum());
				
				
			LOG.debug("3 param \n:" + vo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				outVO = new WishlistVO();
				outVO.setNum(rs.getInt("num"));
				outVO.setProduct_no(rs.getString("product_no"));
				outVO.setProductNm(rs.getString("product_nm"));
				outVO.setReg_dt(rs.getString("reg_dt"));
				LOG.debug("--------------------------------------------------------------");
				LOG.debug("3 do_retrieve outVO \n:"+outVO);
				LOG.debug("--------------------------------------------------------------");
				list.add(outVO);
			}

		} catch (SQLException e) {
			LOG.debug("===============================");
			LOG.debug("SQLException=" + e.toString());
			LOG.debug("===============================");
		} finally {
			JDBCReturnReso.close(rs);
			JDBCReturnReso.close(pstmt);
			JDBCReturnReso.close(conn);
		}
		LOG.debug("4 list \n:" + list);
		return list;
	}

}
