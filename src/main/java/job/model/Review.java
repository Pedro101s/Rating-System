package job.model;

import java.util.Objects;

public class Review 
{
    private long id;
    private long post_id;
    private long user_id;
    private String title;
    private String description;
    private int rating;
    private String date;
    private User user; // Reference to the user who posted the review

    public long getId() 
    {
        return id;
    }

    public void setId(long id) 
    {
        this.id = id;
    }

    public long getPostId()
    {
        return post_id;
    }

    public void setPostId(long post_id)
    {
        this.post_id = post_id;
    }

    public long getUserId()
    {
        return user_id;
    }

    public void setUserId(long user_id)
    {
        this.user_id = user_id;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public User getUser() 
    {
        return user;
    }

    public void setUser(User user) 
    {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review e = (Review) o;
        return id == e.id && Objects.equals(post_id, e.post_id) && Objects.equals(user_id, e.user_id) && 
        Objects.equals(title, e.title) && Objects.equals(description, e.description) &&
        Objects.equals(rating, e.rating) && Objects.equals(date, e.date) && Objects.equals(user, e.user);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(id, post_id, user_id, title, description, rating, date, user);
    }
}
