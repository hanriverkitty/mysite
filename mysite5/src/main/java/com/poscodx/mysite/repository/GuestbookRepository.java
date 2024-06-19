package com.poscodx.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	private SqlSession sqlSession;
	
	public GuestbookRepository(SqlSession sqlSession){
		this.sqlSession = sqlSession;
	}
//	private Connection getConnection() throws SQLException {
//		Connection conn = null;
//		
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//			String url = "jdbc:mariadb://192.168.0.197:3306/webdb?charset=utf8";
//			conn = DriverManager.getConnection(url, "webdb", "webdb");
//		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		}
//		
//		return conn;
//	}
	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert",vo);		
	}
	public int deleteByNo(Long no, String password) {
		return sqlSession.delete("guestbook.deleteByNo",Map.of("no",no,"password",password));

//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where no=? and password=?");
//		) {
//			pstmt.setLong(1, no);
//			pstmt.setString(2, password);
//			result = pstmt.executeUpdate();
	//			ResultSet rs = pstmt.executeQuery();
	//			String answer = rs.next() ? rs.getString(1) : null;
	//			if(answer.equals(password)) {
	//				PreparedStatement pstmt1 = conn.prepareStatement("delete from guestbook where no=?");
	//				pstmt1.setLong(1, no);
	//			}
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		}
//		return result;
		
	}
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
}
