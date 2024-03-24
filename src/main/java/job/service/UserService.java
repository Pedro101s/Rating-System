package job.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import job.model.User;
import job.repository.UserRepository;

@Service
public class UserService 
{
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public byte[] findImageById(long id)
    {
        return userRepository.findImageById(id);
    }

    public long findIdByEmail(String email)
    {
        return userRepository.findIdByEmail(email);
    }

    public User findUserById(long id)
    {
        return userRepository.findUserById(id);
    }

    public User findUserByName(String name)
    {
      
        return userRepository.findUserByName(name);
    }

    public List<User> findAllUsers()
    {
        return userRepository.findAllUsers();
    }

    public User findUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    public boolean emailExists(String email)
    {
        try 
        {
            // Attempt to find an employee with the given email
            userRepository.findUserByEmail(email);
            return true; // If found, email already exists
        } 
        catch (Exception e) 
        {
            return false; // If not found, email does not exist
        }
    }

    public boolean emailAndPasswordExists(String email, String password)
    {
        try 
        {
            // Attempt to find an employee with the given email
            userRepository.findUserByEmailAndPassword(email, password);
            return true; // If found, email already exists
        } 
        catch (Exception e) 
        {
            return false; // If not found, email does not exist
        }
    }

    public void storeUser(User user) throws IOException 
    {
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());

        // Check if the image is provided
        if (user.getImage() == null || user.getImage().length == 0) 
        {
            // Set a default image if none is provided
            user.setImage(getDefaultImageBytes());
        } 
        else 
        {
            // Resize and compress image before storing
            byte[] resizedImageBytes = resizeAndCompressImage(user.getImage());
            user.setImage(resizedImageBytes);
        }
        
        userRepository.storeUser(user);
    }

    public void updateUserName(long id, String newName)
    {
        userRepository.updateUserName(id, newName);
    }

    public void updateUserEmail(long id, String newEmail)
    {
        userRepository.updateUserEmail(id, newEmail);
    }

    public void updateUserPassword(long id, String newPassword)
    {
        userRepository.updateUserPassword(id, newPassword);
    }

    public void updateUserImage(long id, byte[] newImage) throws IOException 
    {
        byte[] resizedImageBytes = resizeAndCompressImage(newImage);
        userRepository.updateUserImage(id, resizedImageBytes);
    }

    public void updateUserImageNoResize(long id, byte[] newImage) throws IOException 
    {
        userRepository.updateUserImage(id, newImage);
    }
    
    public byte[] getDefaultImageBytes() throws IOException 
    {
        // Provide your default image as a byte array
        BufferedImage defaultImage = ImageIO.read(getClass().getResourceAsStream("/static/images/default_image.jpg"));
    
        // Compress default image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(defaultImage, "png", outputStream);
    
        return outputStream.toByteArray();
    }
    private byte[] resizeAndCompressImage(byte[] originalImageBytes) throws IOException 
    {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(originalImageBytes));
        
        
        // Resize image to desired dimensions
        BufferedImage resizedImage = Scalr.resize(originalImage, 400, 400); // Example dimensions
        
        // Compress image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", outputStream);
        
        return outputStream.toByteArray();
    }
}
