package org.edwith.webbe.guestbook.dao;

import org.edwith.webbe.guestbook.dto.Guestbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestbookDao {
  private static String dburl = "jdbc:mysql://localhost:3306/connectdb?useUnicode=true&characterEncoding=utf8&useSSL=false";
  private String dbUser = "connectuser";
  private String dbpasswd = "connectuser123!@#";

  public List<Guestbook> getGuestbooks(){
    List<Guestbook> list = new ArrayList<>();
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    String sql = "SELECT * FROM guestbook order by regdate";
    try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
         PreparedStatement ps = conn.prepareStatement(sql))
    {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Long id = rs.getLong("id");
          String name = rs.getString("name");
          String content = rs.getString("content");
          Date regdate = rs.getDate("regdate");
          Guestbook guestbook = new Guestbook(id,name,content,regdate);
          list.add(guestbook);
        }
      } catch (Exception e){
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public void addGuestbook(Guestbook guestbook){
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    String sql = "insert into guestbook (name, content) values (?, ?)";

    try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
         PreparedStatement ps = conn.prepareStatement(sql))
    {
      ps.setString(1, guestbook.getName());
      ps.setString(2, guestbook.getContent());
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
