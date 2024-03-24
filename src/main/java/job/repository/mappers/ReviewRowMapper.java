package job.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import job.model.Review;

public class ReviewRowMapper implements RowMapper<Review>
{
    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException 
    {
        Review review = new Review();
        review.setId(resultSet.getInt("id"));
        review.setPostId(resultSet.getInt("post_id"));
        review.setUserId(resultSet.getInt("user_id"));
        review.setTitle(resultSet.getString("title"));
        review.setDescription(resultSet.getString("description"));
        review.setRating(resultSet.getInt("rating"));
         // Retrieve date as Timestamp from ResultSet
        Timestamp timestamp = resultSet.getTimestamp("date");

        // Convert Timestamp to LocalDateTime
        LocalDateTime dateTime = null;
        if (timestamp != null) {
            dateTime = timestamp.toLocalDateTime();
            // Format LocalDateTime to String
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = dateTime.format(formatter);
            // Set formatted date in the Review object
            review.setDate(formattedDate);
        }

        return review;
    }
}
