package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.0.197:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;
	}

	public int insert(BoardVo vo) {
		int result = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into board values(null,?,?,0,now(),("
					+ "select ifnull(max(g_no),0)+1 from board as b),1,0,?)");

		) {
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setInt(3, vo.getUserNo());
			result = pstmt1.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
		
	}

	public List<BoardVo> findAll(int p) {
		List<BoardVo> result = new ArrayList<>();
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select a.no, title, contents, hit, "
						+ "reg_date, g_no, o_no, depth, user_no, name "
						+ "from board a, user b where a.user_no = b.no order by g_no DESC, o_no ASC "
						+ "limit ?,5");
				) {
			pstmt.setInt(1, (p-1)*5);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String name = rs.getString(10);
			

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(name);
				result.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	public BoardVo findByNo(int no) {
		BoardVo result = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("select title, contents, hit, g_no, o_no, depth, user_no, b.name "
						+ " from board a, user b where a.user_no = b.no and a.no=?");
			) {
				pstmt1.setInt(1, no);
				ResultSet rs = pstmt1.executeQuery();
				if(rs.next()) {
					String title = rs.getString(1);
					String contents = rs.getString(2);
					int hit = rs.getInt(3);
					int gNo = rs.getInt(4);
					int oNo = rs.getInt(5);
					int depth = rs.getInt(6);
					int userNo = rs.getInt(7);
					String name = rs.getString(8);
				

					BoardVo vo = new BoardVo();
					vo.setNo(no);
					vo.setTitle(title);
					vo.setContents(contents);
					vo.setHit(hit);
					vo.setgNo(gNo);
					vo.setoNo(oNo);
					vo.setDepth(depth);
					vo.setUserNo(userNo);
					vo.setUserName(name);
					result = vo;
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return result;
	}
	
	public int update(BoardVo vo) {
		int result = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("update board set title=?, contents=?, reg_date=now() where no=?");
			) {
				
				pstmt1.setString(1,vo.getTitle());
				pstmt1.setString(2,vo.getContents());
				pstmt1.setInt(3,vo.getNo());
				result = pstmt1.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return result;
	}
	
	public int reply(BoardVo vo) {
		int result = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt2 = conn.prepareStatement("update board set o_no=o_no+1 where g_no = ? and o_no >= ?+1");
				PreparedStatement pstmt1 = conn.prepareStatement("insert into board values(null,?, ?, 0,now(),?,?+1,?+1,?)");
			) {
				pstmt2.setInt(1,vo.getgNo());
				pstmt2.setInt(2,vo.getoNo());
				result = pstmt2.executeUpdate();
				
				pstmt1.setString(1,vo.getTitle());
				pstmt1.setString(2,vo.getContents());
				pstmt1.setInt(3,vo.getgNo());
				pstmt1.setInt(4,vo.getoNo());
				pstmt1.setInt(5,vo.getDepth());
				pstmt1.setInt(6,vo.getUserNo());
				result = pstmt1.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return result;
	}
	
	public int delete(int no) {
		int result = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("delete from board where no=?");
			) {
				
				pstmt1.setInt(1,no);
				result = pstmt1.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return result;
	}
	
	public int countTotal() {
		int result = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("select count(*) from board");
			) {
				ResultSet rs = pstmt1.executeQuery();
				if(rs.next()) {
					result = rs.getInt(1);
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return result;
	}
	
	public List<BoardVo> searchAll(String keyword,int p){
		List<BoardVo> result = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select a.no, title, contents, hit, "
						+ "reg_date, g_no, o_no, depth, user_no, name "
						+ "from board a, user b where a.user_no = b.no and title like ? order by g_no DESC, o_no ASC "
						+ "limit ?,5");
				) {
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, (p-1)*5);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String name = rs.getString(10);
			

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(name);
				result.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}
	public int searchCount(String keyword) {
		int result = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("select count(*) from board where title like ?");
			) {
				
				pstmt1.setString(1,"%"+keyword+"%");
				ResultSet rs = pstmt1.executeQuery();
				if(rs.next()) {
					result = rs.getInt(1);
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		return result;
	}
}
