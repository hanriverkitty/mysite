package com.poscodx.mysite.repository;

import java.util.List;
import java.util.Map;

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
	
	public List<BoardVo> findAll(int p) {
		return sqlSession.selectList("board.findAll", Integer.valueOf(p));
	}
	
	public List<BoardVo> findAll(String keyword,int p ){
		return sqlSession.selectList("board.searchAll",Map.of("keyword",keyword,"p",Integer.valueOf(p)));
	}
	
	public BoardVo findByNo(int no) {
		return sqlSession.selectOne("board.findByNo",Integer.valueOf(no));
	}
	
	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}
	
	public int updateOrderNo(BoardVo vo) {
		return sqlSession.update("board.updateOrderNo",vo);
	}
	
	public int reply(BoardVo vo) {
		sqlSession.update("board.updateOrderNo",vo);
		return sqlSession.insert("board.reply",vo);
	}
	
	public int delete(int no) {
		return sqlSession.delete("board.delete", Integer.valueOf(no));
	}
	
	public int countTotal() {
		Integer count =  sqlSession.selectOne("board.countTotal");
		return count;
	}
	
	
	
	public int searchCount(String keyword) {
		return sqlSession.selectOne("board.searchCount","%"+keyword+"%");
	}
	
	public int updateHit(int no) {
		return sqlSession.update("board.updateHit", Integer.valueOf(no));
	}
}
