package job.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import job.service.UserService;
import job.service.LoggedUserManagementService;

@Component
@RequestScope
public class LoginProcessor 
{
    private final LoggedUserManagementService loggedUserManagementService;
    private final UserService userService;

    private String email;
    private String password;
    public LoginProcessor(LoggedUserManagementService loggedUserManagementService, UserService userService) 
    {
        this.loggedUserManagementService = loggedUserManagementService;
        this.userService = userService;
    }

    public boolean login() 
    {
        String email = this.getEmail();
        String password = this.getPassword();

        boolean loginResult = false;

        User user = userService.findUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) 
        {
            loginResult = true;
            loggedUserManagementService.setUsername(user.getName());
            loggedUserManagementService.setImage(user.getImage());
            loggedUserManagementService.setId(user.getId());
        }

        return loginResult;
    }

    public long getId() 
    {
        return loggedUserManagementService.getId();
    }

    public void setId(long id) 
    {
        loggedUserManagementService.setId(id);
    }

    public String getUsername()
    {
        return loggedUserManagementService.getUsername();
    }

    public void setUsername(String username)
    {
        loggedUserManagementService.setUsername(username);
    }

    public byte[] getImage()
    {
        return loggedUserManagementService.getImage();
    }

    public void setImage(byte[] image)
    {
        loggedUserManagementService.setImage(image);
    }

    public String getImageBase64()
    {
        return loggedUserManagementService.getImageBase64();
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

    public User findUserByEmail(String email)
    {
        return userService.findUserByEmail(email);
    }

    public User findUserById(long id)
    {
        return userService.findUserById(id);
    }


}
