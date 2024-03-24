package job.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import job.model.User;
import job.repository.mappers.UserRowMapper;

@Repository
public class UserRepository
{

  private final JdbcTemplate jdbc;

  public UserRepository(JdbcTemplate jdbc) 
  {
    this.jdbc = jdbc;
  }


  public byte[] findImageById(long id) 
  {
    String sql = "SELECT image FROM account WHERE id = ?";
    return jdbc.queryForObject(sql, byte[].class, id);
  }
  
  public long findIdByEmail(String email)
  {
    String sql = "SELECT id FROM account WHERE email = ?";
    return jdbc.queryForObject(sql, Long.class, email);
  }

  public User findUserById(long id) 
  {
    String sql = "SELECT * FROM account WHERE id = ?";
    return jdbc.queryForObject(sql, new UserRowMapper(), id);
  }

  public User findUserByName(String name) 
  {
    String sql = "SELECT * FROM account WHERE name = ?";
    return jdbc.queryForObject(sql, new UserRowMapper(), name);
  }

  public User findUserByEmail(String email)
  {
    String sql = "SELECT * FROM account WHERE email = ?";
    return jdbc.queryForObject(sql, new UserRowMapper(), email);
  }

  public User findUserByEmailAndPassword(String email, String password) 
  {
    String sql = "SELECT * FROM account WHERE email = ? AND password = ?";
    return jdbc.queryForObject(sql, new UserRowMapper(), email, password);
  } 

  public List<User> findAllUsers() 
  {
    String sql = "SELECT * FROM account";
    return jdbc.query(sql, new UserRowMapper());
  }

  public void storeUser(User user) 
  {
    String sql = "INSERT INTO account (name, email, password, image) VALUES (?, ?, ?, ?)";
    jdbc.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getImage());
  }
  
  public void updateUserName(long id, String newName) 
  {
    String sql = "UPDATE account SET name = ? WHERE id = ?";
    jdbc.update(sql, newName, id);
  }

  public void updateUserEmail(long id, String newEmail) 
  {
    String sql = "UPDATE account SET email = ? WHERE id = ?";
    jdbc.update(sql, newEmail, id);
  }

  public void updateUserPassword(long id, String newPassword) 
  {
    String sql = "UPDATE account SET password = ? WHERE id = ?";
    jdbc.update(sql, newPassword, id);
  }

  public void updateUserImage(long id, byte[] newImage) 
  {
    String sql = "UPDATE account SET image = ? WHERE id = ?";
    jdbc.update(sql, newImage, id);
  }

  public void deleteUserById(long id) 
  {
    String sql = "DELETE FROM account WHERE id = ?";
    jdbc.update(sql, id);
  }
}