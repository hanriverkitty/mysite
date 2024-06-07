package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;

	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert",vo);
	}
	
	public List<BoardVo> findAll(Long p) {
		return sqlSession.selectList("board.findAll", (p-1)*5);
	}
	
	public BoardVo findByNo(int no) {
		return sqlSession.selectOne("board.findByNo",Integer.valueOf(no));
	}
	
	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}
	
	public int updateOrderNo(BoardVo vo) {
		return 
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
	
	public void updateHit(int no) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("update board set hit=hit+1 where no=?");
			) {
				
				pstmt1.setInt(1,no);
				pstmt1.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
	}
}
