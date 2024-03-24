package job.service;

import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class LoggedUserManagementService 
{
    private String username;
    private byte[] image;
    private long id;

    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
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

    public long getId() 
    {
        return id;
    }

    public void setId(long id) 
    {
        this.id = id;
    }
}
