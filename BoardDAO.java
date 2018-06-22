package dmirae.lim.model;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class BoardDAO {
	public BoardDAO() {
		System.out.println("BaordDAO()");
	}
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		Connection con=null;
		String url="jdbc:oracle:thin:@localhost:1521:paul";
		String user="jsp3c";
		String password="1234";
		try {
			con=DriverManager.getConnection(url,user,password);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}
	public static void close(Connection con,PreparedStatement st) {
		try {
				st.close();
				con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(Connection con,PreparedStatement st,ResultSet rs) {
		try {
				rs.close();
				st.close();
				con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static int insert(BoardDTO boardDTO) { 
		
		String sql="insert into board values(board_seq.nextval,?,?,?,default,default)";
		Connection con=null;
		PreparedStatement st=null;
		int result=0;
		try {
			
			System.out.println("드라이버 로딩성공");
			con=getConnection();
			st=con.prepareStatement(sql);
			st.setString(1, boardDTO.getId());
			st.setString(2, boardDTO.getTitle());
			st.setString(3, boardDTO.getContent());
			result=st.executeUpdate();
			System.out.println("sql명령어 성공");
			return result;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return result;
		}finally {
			close(con,st);
		}
		
	}
	public static ArrayList<BoardDTO> selectAll() {
			ArrayList<BoardDTO> al=new ArrayList<>();
			String sql="select * from board order by num desc";
			Connection con=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			BoardDTO boardDTO=null;
			try {
				con=getConnection();
				st=con.prepareStatement(sql);
				rs=st.executeQuery();
				if(rs.next()) {
					int num=rs.getInt(1);
					String id=rs.getString(2);
					String title=rs.getString(3);
					String content=rs.getString(4);
					int readCount=rs.getInt(5);
					Date writeDate=rs.getDate(6);
					boardDTO=new BoardDTO(num, id, title, content, readCount, writeDate);
					al.add(boardDTO);
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				close(con, st, rs);
			}
		
		return al;
		
	}
	public static BoardDTO selectByNum(int number) {
		String sql="select * from board where num =?";
		Connection con=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		BoardDTO boardDTO=null;
		try {
			con=getConnection();
			st=con.prepareStatement(sql);
			st.setInt(1, number);
			rs=st.executeQuery();
			if(rs.next()) {
				int num=rs.getInt(1);
				String id=rs.getString(2);
				String title=rs.getString(3);
				String content=rs.getString(4);
				int readCount=rs.getInt(5);
				Date writeDate=rs.getDate(6);
				boardDTO=new BoardDTO(num, id, title, content, readCount, writeDate);	
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(con,st,rs);
		}
	
		return boardDTO;
	}
	
	public static void update(BoardDTO boardDTO) {
		Connection con=null;
		PreparedStatement st=null;
		String sql="update board set title=? ,content=? where num=?";
		try {
			con=getConnection();
			st=con.prepareStatement(sql);
			st.setString(1,boardDTO.getTitle());
			st.setString(2, boardDTO.getContent());
			st.setInt(3, boardDTO.getNum());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(con, st);
		}
	}
	public void deleteByNum() {
		
	}
}
