package job.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import job.model.LoginProcessor;
import job.model.Post;
import job.model.Review;
import job.model.User;
import job.service.PostService;
import job.service.ReviewService;

@Controller
public class PostController 
{
    private final PostService postService;
    private final ReviewService reviewService;
    private final LoginProcessor loginProcessor;
    public PostController(PostService postService, ReviewService reviewService, LoginProcessor loginProcessor)
    {
        this.postService = postService;
        this.reviewService = reviewService;
        this.loginProcessor = loginProcessor;
    }


    @GetMapping("/main")
    public String post(@RequestParam(required = false) String logout, Model model) 
    {
        List<Post> posts = new ArrayList<>();

        // Populate predefined posts
        postService.populatePredefinedPosts(posts);

        posts = postService.findAllPosts();


        // Add posts to the model
        model.addAttribute("posts", posts);

        // Add number of reviews for each post to the model
        for (Post post : posts) 
        {
            long postId = post.getId();
            int reviewCount = reviewService.getReviewsByPostId(postId).size();
            model.addAttribute("reviewCount_" + postId, reviewCount);
        }

        if (logout != null) 
        {
            loginProcessor.setUsername(null);
            loginProcessor.setImage(null);
        }
        
        String username = loginProcessor.getUsername();

        if (username == null) 
        {
            return "main.html";
        } 
        else 
        {
            String currentImage = loginProcessor.getImageBase64();
            model.addAttribute("username" , username);
            model.addAttribute("image", currentImage);
        }
        return "main.html";
    }

    @GetMapping("/logout")
    public String logout(@RequestParam(required = false) String logout,
    Model model)   
    {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        // Add number of reviews for each post to the model
        for (Post post : posts) 
        {
            long postId = post.getId();
            int reviewCount = reviewService.getReviewsByPostId(postId).size();
            model.addAttribute("reviewCount_" + postId, reviewCount);
        }
        loginProcessor.setUsername(null);
        loginProcessor.setImage(null);
        return "main.html";
    }

    @GetMapping("/view-post/{postId}")
    public String viewPost(@RequestParam(required = false) String logout, @PathVariable Long postId, Model model) 
    {
        // Assuming you have a ReviewService to handle review-related operations
        List<Review> reviews = reviewService.getReviewsByPostId(postId);
        

        int totalReviews = reviews.size();
        int totalRatings = reviews.stream().mapToInt(Review::getRating).sum();
 
        double average = totalReviews != 0 ? (double) totalRatings / totalReviews : 0;
 
        String formattedAverage = String.format("%.1f", average);
        // Count ratings for each star
        int rating1Count = 0;
        int rating2Count = 0;
        int rating3Count = 0;
        int rating4Count = 0;
        int rating5Count = 0;
        for (Review review : reviews) 
        {
            User user = loginProcessor.findUserById(review.getUserId()); // Assuming you have a UserService
            review.setUser(user);
            int rating = review.getRating();
            switch (rating) 
            {
                case 1:
                    rating1Count++;
                    break;
                case 2:
                    rating2Count++;
                    break;
                case 3:
                    rating3Count++;
                    break;
                case 4:
                    rating4Count++;
                    break;
                case 5:
                    rating5Count++;
                    break;
            }
        }
        model.addAttribute("reviews", reviews);
        model.addAttribute("average", formattedAverage);
        model.addAttribute("totalReviews", totalReviews);
        model.addAttribute("rating1Count", rating1Count);
        model.addAttribute("rating2Count", rating2Count);
        model.addAttribute("rating3Count", rating3Count);
        model.addAttribute("rating4Count", rating4Count);
        model.addAttribute("rating5Count", rating5Count);


        // Assuming you have a PostService to handle database operations
        Post post = postService.getPostById(postId);

        String postImage = post.getImageBase64();
        String postTitle = post.getTitle();

        model.addAttribute("postId", postId);
        model.addAttribute("postImage", postImage);
        model.addAttribute("postTitle", postTitle);
        if (logout != null) 
        {
            loginProcessor.setUsername(null);
            loginProcessor.setImage(null);
        }
        
        String username = loginProcessor.getUsername();

        if (username == null) 
        {
            return "view_post.html";
        }
        else
        {
            long id = loginProcessor.getId();
            String currentImage = loginProcessor.getImageBase64();
            model.addAttribute("username" , username);
            model.addAttribute("image", currentImage);
            model.addAttribute("userId", id);

            return "view_post.html";
        }
    }

    @PostMapping("/deleteReview")
    public String deleteReview(@RequestParam("delete_id") Long deleteId,@RequestParam("viewPostId") Long post_id, Model model) {
        Review review = reviewService.findReviewById(deleteId);
        if (review != null) {
            reviewService.deleteReviewById(deleteId);
            model.addAttribute("successMsg", "Review deleted!");
        } else {
            model.addAttribute("warningMsg", "Review already deleted!");
        }
        return "redirect:/view-post/" + post_id;
    }

    @GetMapping("/add-review/{postId}")
    public String showReviewForm(@RequestParam(required = false) String logout, @PathVariable Long postId, Model model)
    {

        if (logout != null) 
        {
            loginProcessor.setUsername(null);
            loginProcessor.setImage(null);
        }
        
        String username = loginProcessor.getUsername();

        if (username == null) 
        {
            return "add_review.html";
        }
        else
        {
            String currentImage = loginProcessor.getImageBase64();
            model.addAttribute("username" , username);
            model.addAttribute("image", currentImage);
        }

        return "add_review.html";
    }

    @PostMapping("/add-review/{postId}")
    public String postReviewForm(@RequestParam(required = false) String logout, @PathVariable Long postId, Model model,
    @RequestParam int rating,
    @RequestParam String title,
    @RequestParam String description)
    {

        if (logout != null) 
        {
            loginProcessor.setUsername(null);
            loginProcessor.setImage(null);
        }
        
        String username = loginProcessor.getUsername();

        if (username == null) 
        {
            model.addAttribute("warningMsg", "Please Login First!");
            return "add_review.html";
        }
        else
        {
            String currentImage = loginProcessor.getImageBase64();
            model.addAttribute("username" , username);
            model.addAttribute("image", currentImage);
        }

        if (reviewService.reviewExists(postId, loginProcessor.getId()))
        {
            model.addAttribute("secondWarningMsg", "Your review already added!");
            return "add_review.html";
        }
        Review review = new Review();
        review.setPostId(postId);
        review.setUserId(loginProcessor.getId());
        review.setTitle(title);
        review.setDescription(description);
        review.setRating(rating);

        reviewService.storeReview(review);

        model.addAttribute("successMsg", "Review Added!");


        return "add_review.html";
    }

    @GetMapping("/update-review/{postId}/{userId}")
    public String showUpdateFrom(@RequestParam(required = false) String logout,@PathVariable Long postId, 
    @PathVariable Long userId, Model model)
    {
        if (logout != null) 
        {
            loginProcessor.setUsername(null);
            loginProcessor.setImage(null);
        }
        String username = loginProcessor.getUsername();
        String currentImage = loginProcessor.getImageBase64();
        Review review = reviewService.findReviewByPostIdAndUserId(postId, userId);
        String title = review.getTitle();
        String description = review.getDescription();
        int rating = review.getRating();
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("rating", rating);
        model.addAttribute("username" , username);
        model.addAttribute("image", currentImage);
        return "update_review.html";
    }

    @PostMapping("/update-review/{postId}/{userId}")
    public String postUpdateFrom(@RequestParam(required = false) String logout,@PathVariable Long postId, 
    @PathVariable Long userId, Model model,@RequestParam int rating,
    @RequestParam String title,
    @RequestParam String description)
    {
        if (logout != null) 
        {
            loginProcessor.setUsername(null);
            loginProcessor.setImage(null);
        }
        String username = loginProcessor.getUsername();
        String currentImage = loginProcessor.getImageBase64();
        Review review = reviewService.findReviewByPostIdAndUserId(postId, userId);
        reviewService.updateReviewTitle(postId, userId, title);
        reviewService.updateReviewDescription(postId, userId, description);
        reviewService.updateReviewRating(postId, userId, rating);
        title = review.getTitle();
        description = review.getDescription();
        rating = review.getRating();
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("rating", rating);
        model.addAttribute("username" , username);
        model.addAttribute("image", currentImage);
        model.addAttribute("successMsg", "Review Updated!");
        return "update_review.html";
    }
}
