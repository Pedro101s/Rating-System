package job.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import job.model.Post;

@Repository
public class PostRepository 
{
    private final JdbcTemplate jdbc;

    public PostRepository(JdbcTemplate jdbc) 
    {
        this.jdbc = jdbc;
    }

    public void storePost(Post post) 
    {
        String sql = "INSERT INTO posts (title, image) VALUES (?, ?)";
        jdbc.update(sql, post.getTitle(), post.getImage());
    }

    public Post getPostById(long id)
    {
        String sql = "SELECT * FROM Posts WHERE id = ?";
        RowMapper<Post> postRowMapper = (r, i) -> 
        {
            Post rowObject = new Post();
            rowObject.setId(r.getInt("id"));
            rowObject.setTitle(r.getString("title"));
            rowObject.setImage(r.getBytes("image"));
            return rowObject;
        };
        return jdbc.queryForObject(sql, postRowMapper, id);
    }

    public List<Post> findAllPosts() 
    {
        String sql = "SELECT * FROM posts";
        RowMapper<Post> postRowMapper = (r, i) -> 
        {
            Post rowObject = new Post();
            rowObject.setId(r.getInt("id"));
            rowObject.setTitle(r.getString("title"));
            rowObject.setImage(r.getBytes("image"));
            return rowObject;
        };
      
          return jdbc.query(sql, postRowMapper);
    }

    public boolean hasPosts() 
    {
        String sql = "SELECT COUNT(*) FROM posts";
        Integer count = jdbc.queryForObject(sql, Integer.class);
        return count != null && count == 6;
    }
}
