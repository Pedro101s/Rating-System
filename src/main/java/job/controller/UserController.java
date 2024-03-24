package job.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import job.model.User;
import job.model.LoginProcessor;
import job.service.UserService;

@Controller
public class UserController 
{
    private final UserService userService;
    private final LoginProcessor loginProcessor;

    public UserController(UserService userService, LoginProcessor loginProcessor)
    {
        this.userService = userService;
        this.loginProcessor = loginProcessor;
    } 

    /* 
    @GetMapping("/main")
    public String home(
      @RequestParam(required = false) String logout,
      Model model) 
    {
        if (logout != null) 
        {
            loggedUserManagementService.setUsername(null);
            loggedUserManagementService.setImage(null);
        }
        
        String username = loggedUserManagementService.getUsername();

        if (username == null) 
        {
            return "main.html";
        }
        else
        {
            String image = loggedUserManagementService.getImageBase64();
            model.addAttribute("username" , username);
            model.addAttribute("image", image);
        }
        return "main.html";
    }
    */

    @PostMapping("/register")
    public String storeUser(@RequestParam(required = false) String logout, @RequestParam("name") String name, 
        @RequestParam("email") String email, @RequestParam("password") String password, 
        @RequestParam("confirmPassword") String confirmPassword,
        @RequestParam(name = "image", required = false) MultipartFile image, Model model) 
    {
        try 
        {
            if (logout != null) {
                loginProcessor.setUsername(null); //loggedUserManagementService
                loginProcessor.setImage(null); //loggedUserManagementService
            }
    
            String username = loginProcessor.getUsername(); //loggedUserManagementService
    
            if (username != null) {
                String userImage = loginProcessor.getImageBase64(); //loggedUserManagementService
                model.addAttribute("username", username);
                model.addAttribute("image", userImage);
            }
    
            // Check if email already exists in the database
            if (userService.emailExists(email)) {
                model.addAttribute("warningMsg", "Email already taken!");
                return "register.html";
            }
    
            // Check if password and confirm password match
            if (!password.equals(confirmPassword)) {
                model.addAttribute("warningMsg", "Confirm password not matched!");
                return "register.html";
            }
    
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setImage(image.getBytes());
            
            userService.storeUser(user);
    
            model.addAttribute("successMsg", "Registered successfully!");
            
        } 
        catch (IOException e) 
        {
            model.addAttribute("error", "Error storing employee image");
        }
        return "register.html";
    }

    @GetMapping("/register")
    public String register(@RequestParam(required = false) String logout, Model model) 
    {
        if (logout != null) 
        {
            loginProcessor.setUsername(null); //loggedUserManagementService
            loginProcessor.setImage(null); //loggedUserManagementService
        }
        
        String username = loginProcessor.getUsername(); //loggedUserManagementService

        if (username == null) 
        {
            return "register.html";
        }
        else
        {
            String image = loginProcessor.getImageBase64(); //loggedUserManagementService
            model.addAttribute("username" , username);
            model.addAttribute("image", image);
        }
        return "register.html";
    }

    @GetMapping("/login")
    public String loginGet(@RequestParam(required = false) String logout,
    Model model)   
    {
        if (logout != null) 
        {
            loginProcessor.setUsername(null);
            loginProcessor.setImage(null);
        }
        
        String username = loginProcessor.getUsername();

        if (username == null) 
        {
            return "login.html";
        }
        else
        {
            String image = loginProcessor.getImageBase64();
            model.addAttribute("username" , username);
            model.addAttribute("image", image);
        }
        return "login.html";
    }

    @PostMapping("/login")
    public String loginPost(
      @RequestParam String email,
      @RequestParam String password,
      Model model) 
    {
        if (userService.emailAndPasswordExists(email, password))
        {
            loginProcessor.setEmail(email);
            loginProcessor.setPassword(password);
            boolean loggedIn = loginProcessor.login();
            if (loggedIn) 
            {

                return "redirect:/main";
            }
        }

        model.addAttribute("message", "Login failed!");
        return "login.html";
    }

    /*@GetMapping("/logout")
    public String logout(@RequestParam(required = false) String logout,
    Model model)   
    {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        loginProcessor.setUsername(null);
        loginProcessor.setImage(null);
        return "main.html";
    }
    */


    @GetMapping("/update_profile")
    public String profile(@RequestParam(required = false) String logout, Model model) 
    {
        if (logout != null) 
        {
            loginProcessor.setUsername(null); //loggedUserManagementService
            loginProcessor.setImage(null); //loggedUserManagementService
        }
        
        String username = loginProcessor.getUsername(); //loggedUserManagementService

        if (username == null) 
        {
            return "redirect:/login";
        }
        String image = loginProcessor.getImageBase64(); //loggedUserManagementService

        model.addAttribute("username" , username);
        model.addAttribute("image", image);
        return "update_profile.html";
    }



    @PostMapping("/update_profile")
    public String updateProfile(@RequestParam(required = false) String logout, @RequestParam("name") String name, 
            @RequestParam("email") String email, @RequestParam("oldPassword") String oldPassword, 
            @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam(value = "deleteImage", required = false) boolean deleteImage,
            @RequestParam("image") MultipartFile image, Model model) 
    {

        String username = loginProcessor.getUsername(); //loggedUserManagementService
        String currentImage = loginProcessor.getImageBase64(); //loggedUserManagementService

        if (username == null) 
        {   
            return "update_profile.html";
        } 
        else 
        {
            if (logout != null) 
            {
                loginProcessor.setUsername(null); //loggedUserManagementService
                loginProcessor.setImage(null); //loggedUserManagementService
                return "redirect:/login"; // Redirect to login page after logout
            }

            // Assuming you have a UserService instance available
            User user = userService.findUserById(loginProcessor.getId()); //loggedUserManagementService

            if (user == null) 
            {
                return "update_profile.html";
            }

            // Update name
            if (name != null && !name.isEmpty()) 
            {
                userService.updateUserName(user.getId(), name);
                loginProcessor.setUsername(name); //loggedUserManagementService
                user.setName(name);
            }

            // Update email
            if (userService.emailExists(email))
            {
                model.addAttribute("warningMsg", "Email already taken!");
                model.addAttribute("username", username);
                model.addAttribute("image", currentImage);
                return "update_profile.html";
            }
            else if(email != null && !email.isEmpty()) 
            {
                userService.updateUserEmail(user.getId(), email);
                user.setEmail(email);
            }

            // Update password
            if ((!oldPassword.isEmpty() && !user.getPassword().equals(oldPassword)) || 
            (!newPassword.isEmpty() && !user.getPassword().equals(oldPassword)))
            {
                model.addAttribute("warningMsg", "Old Password not matched!");
                model.addAttribute("username", username);
                model.addAttribute("image", currentImage);
                return "update_profile.html";
            }
            else if (!oldPassword.isEmpty() && !confirmPassword.equals(newPassword))
            {
                model.addAttribute("warningMsg", "Confirm Password not matched!");
                model.addAttribute("username", username);
                model.addAttribute("image", currentImage);
                return "update_profile.html";
            }
            if (newPassword != null && !newPassword.isEmpty() && user.getPassword().equals(oldPassword) && confirmPassword.equals(newPassword)) 
            {
                userService.updateUserPassword(user.getId(), newPassword);
            }
            

            // Delete image
        if (deleteImage) 
        {
            // Set image to null
            try {
                if (Arrays.equals(user.getImage(), userService.getDefaultImageBytes())) {
                    model.addAttribute("warningMsg", "Image already deleted!");
                    model.addAttribute("username", username);
                    model.addAttribute("image", currentImage);
                    return "update_profile.html";
                } 
                else 
                {
                    // Set default image
                    try 
                    {
                        byte[] defaultImageBytes = userService.getDefaultImageBytes();
                        userService.updateUserImageNoResize(user.getId(), defaultImageBytes);
                        user.setImage(defaultImageBytes);
                        loginProcessor.setImage(defaultImageBytes); //loggedUserManagementService
                        currentImage = loginProcessor.getImageBase64(); //loggedUserManagementService
                        model.addAttribute("username", username);
                        model.addAttribute("image", currentImage);
                        return "update_profile.html";
                    } 
                    catch (IOException e) 
                    {
                        // Handle the exception as needed
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                
                e.printStackTrace();
            }
        } 
            else if (image != null && !image.isEmpty()) 
            {
                // Update image
                try 
                {
                    userService.updateUserImage(user.getId(), image.getBytes());
                    user.setImage(userService.findImageById(loginProcessor.getId())); //loggedUserManagementService
                    loginProcessor.setImage(user.getImage()); //loggedUserManagementService
                } 
                catch (IOException e) 
                {
                    // Handle the exception as needed
                    e.printStackTrace();
                }
            }
            username = loginProcessor.getUsername(); //loggedUserManagementService

            currentImage = loginProcessor.getImageBase64(); //loggedUserManagementService

            model.addAttribute("username", username);
            model.addAttribute("image", currentImage);
            model.addAttribute("successMsg", "Profile Updated!");
        }
        return "update_profile.html";
    }
}
