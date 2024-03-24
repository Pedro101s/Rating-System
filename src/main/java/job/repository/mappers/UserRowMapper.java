package job.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import job.model.User;

public class UserRowMapper implements RowMapper<User>
{
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException 
    {
    User e = new User();
    e.setId(resultSet.getInt("id"));
    e.setName(resultSet.getString("name"));
    e.setEmail(resultSet.getString("email"));
    e.setPassword(resultSet.getString("password"));
    e.setImage(resultSet.getBytes("image"));
    return e;
  }
}
