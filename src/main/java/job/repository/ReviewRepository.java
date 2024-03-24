package job.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import job.model.Review;
import job.repository.mappers.ReviewRowMapper;


@Repository
public class ReviewRepository 
{
    private final JdbcTemplate jdbc;

    public ReviewRepository(JdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    public void storeReview(Review review) 
    {
        String sql = "INSERT INTO reviews (post_id, user_id, title, description, rating) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql, review.getPostId(), review.getUserId(), review.getTitle(), 
        review.getDescription(), review.getRating());
    }

    public List<Review> getReviewsByPostId(long post_id) 
    {
        String sql = "SELECT * FROM reviews WHERE post_id = ?";
        return jdbc.query(sql, new ReviewRowMapper(), post_id);
    }

    public Review findReviewById(long id) 
    {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        return jdbc.queryForObject(sql, new ReviewRowMapper(), id);
    }

    public Review findReviewByPostId(long post_id) 
    {
        String sql = "SELECT * FROM reviews WHERE post_id = ?";
        return jdbc.queryForObject(sql, new ReviewRowMapper(), post_id);
    }

    public void deleteReviewById(long id) 
    {
        String sql = "DELETE FROM reviews WHERE id = ?";
        jdbc.update(sql, id);
    }

    public void deleteReviewByUserId(long post_id, long user_id) 
    {
        String sql = "DELETE FROM reviews WHERE post_id = ? AND user_id = ?";
        jdbc.update(sql, post_id, user_id);
    }

    public Review findReviewByPostIdAndUserId(long post_id, long user_id) 
    {
        String sql = "SELECT * FROM reviews WHERE post_id = ? AND user_id = ?";
        return jdbc.queryForObject(sql, new ReviewRowMapper(), post_id, user_id);
    }  

    public void updateReviewTitle(long post_id, long user_id, String title) 
    {
        String sql = "UPDATE reviews SET title = ? WHERE post_id = ? AND user_id = ?";
        jdbc.update(sql, title, post_id, user_id);
    }

    public void updateReviewDescription(long post_id, long user_id, String description) 
    {
        String sql = "UPDATE reviews SET description = ? WHERE post_id = ? AND user_id = ?";
        jdbc.update(sql, description, post_id, user_id);
    }

    public void updateReviewRating(long post_id, long user_id, int rating)
    {
        String sql = "UPDATE reviews SET rating = ? WHERE post_id = ? AND user_id = ?";
        jdbc.update(sql, rating, post_id, user_id);
    }
}
