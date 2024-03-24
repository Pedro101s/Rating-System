package job.service;

import java.util.List;

import org.springframework.stereotype.Service;

import job.model.Review;
import job.repository.ReviewRepository;

@Service
public class ReviewService 
{
    private final ReviewRepository reviewRepository;
    
    public ReviewService(ReviewRepository reviewRepository)
    {
        this.reviewRepository = reviewRepository;
    }

    public Review findReviewById(long id)
    {
        return reviewRepository.findReviewById(id);
    }
    public List<Review> getReviewsByPostId(long post_id)
    {
        return reviewRepository.getReviewsByPostId(post_id);
    }

    public void storeReview(Review review)
    {
        reviewRepository.storeReview(review);
    }

    public Review findReviewByPostIdAndUserId(long post_id, long user_id)
    {
        return reviewRepository.findReviewByPostIdAndUserId(post_id, user_id);
    }
    public boolean reviewExists(long post_id, long user_id)
    {
        try 
        {
           reviewRepository.findReviewByPostIdAndUserId(post_id, user_id);
           return true;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

    public void updateReviewTitle(long post_id, long user_id, String title)
    {
        reviewRepository.updateReviewTitle(post_id, user_id, title);
    }

    public void updateReviewDescription(long post_id, long user_id, String description)
    {
        reviewRepository.updateReviewDescription(post_id, user_id, description);
    }

    public void updateReviewRating(long post_id, long user_id, int rating)
    {
        reviewRepository.updateReviewRating(post_id, user_id, rating);
    }

    public void deleteReviewByUserId(long post_id, long user_id)
    {
        reviewRepository.deleteReviewByUserId(post_id, user_id);
    }

    public void deleteReviewById(long id)
    {
        reviewRepository.deleteReviewById(id);
    }
}
