
package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
    @Autowired
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    private final String BOARD_INSERT = "insert into Board1 (title, writer, content) values (?,?,?)";
    private final String BOARD_UPDATE = "update Board1 set title=?, writer=?, content=? where seq=?";
    private final String BOARD_DELETE = "delete from Board1  where seq=?";
    private final String BOARD_GET = "select * from Board1  where seq=?";
    private final String BOARD_LIST = "select * from Board1 order by seq desc";





    public int insertBoard(BoardVO vo) {
        System.out.println("===> JDBC로 insertBoard() 기능 처리");
        return template.update(BOARD_INSERT,new Object[]{vo.getTitle(), vo.getWriter(), vo.getContent()});
    }

    public int deleteBoard(int id) {
        System.out.println("===> JDBC로 deleteBoard() 기능 처리");
        return template.update(BOARD_DELETE, new Object[]{id});
    }
    public int updateBoard(BoardVO vo) {
        System.out.println("===> JDBC로 updateBoard() 기능 처리");
        return template.update(BOARD_UPDATE,new Object[]{vo.getTitle(),vo.getWriter(),vo.getContent(), vo.getSeq()});
    }

    public BoardVO getBoard(int seq) {
        System.out.println("===> JDBC로 getBoard() 기능 처리");
        return template.queryForObject(BOARD_GET,
                new Object[]{seq},
                new BeanPropertyRowMapper<BoardVO>(BoardVO.class));
    }

    public List<BoardVO> getBoardList(){
        System.out.println("===> JDBC로 getBoardList() 기능 처리");
        return template.query(BOARD_LIST, new RowMapper<BoardVO>(){

            public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                BoardVO vo = new BoardVO();
                vo.setSeq(rs.getInt("seq"));
                vo.setTitle(rs.getString("title"));
                vo.setWriter(rs.getString("writer"));
                vo.setContent(rs.getString("content"));
                vo.setRegdate(rs.getDate("regdate"));
                //System.out.println(vo.getSeq());
                //System.out.println(vo.getTitle());
                //System.out.println(vo.getWriter());
                return vo;
            }
        });
    }
}