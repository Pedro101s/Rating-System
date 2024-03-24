package job.model;

import java.util.Base64;
import java.util.Objects;

public class Post 
{
    private long id;
    private String title;
    private byte[] image;   

    public long getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public byte[] getImage() 
    {
        return image;
    }

    public void setImage(byte[] image) 
    {
        this.image = image;
    }

    public String getImageBase64()
    {
        return Base64.getEncoder().encodeToString(image);
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post e = (Post) o;
        return id == e.id && Objects.equals(title, e.title) && Objects.equals(image, e.image);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(id, title, image);
    }
}
