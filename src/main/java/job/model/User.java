package job.model;


import java.util.Base64;
import java.util.Objects;

public class User 
{
    private long id;
    private String name;
    private String email;
    private String password;
    private byte[] image;

    public long getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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
        User e = (User) o;
        return id == e.id && Objects.equals(name, e.name) && Objects.equals(email, e.email) &&
        Objects.equals(password, e.password) && Objects.equals(image, e.image);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(id, name, email, password, image);
    }
}
