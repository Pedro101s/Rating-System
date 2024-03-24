package job.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import job.model.Post;
import job.repository.PostRepository;

@Service
public class PostService 
{
    private final PostRepository postRepository; 
    private final ResourceLoader resourceLoader;

    public PostService(PostRepository postRepository, ResourceLoader resourceLoader)
    {
        this.postRepository = postRepository;
        this.resourceLoader = resourceLoader;
    }

    public List<Post> findAllPosts()
    {
        return postRepository.findAllPosts();
    }

    public Post getPostById(long id)
    {
        return postRepository.getPostById(id);
    }

    public boolean hasPosts()
    {
        return postRepository.hasPosts();
    }

    public void populatePredefinedPosts(List<Post> posts) {
        // Add your predefined posts here
        // Make sure to adjust the paths based on your static/images folder structure
        // Also, ensure that duplicate images are not added twice

        // Example:
        Set<String> usedImages = new HashSet<>();
        addPredefinedPost(posts, "Remember The Titans", "Remember_The_Titans.png", usedImages);
        addPredefinedPost(posts, "Prince Of Egypt", "The-Prince-of-Egypt.jpg", usedImages);
        addPredefinedPost(posts, "A Christmas Carol", "A_Christmas_Carol.jpeg", usedImages);
        addPredefinedPost(posts, "Zack Snyder's Justice League", "Justice_League.jpg", usedImages);
        addPredefinedPost(posts, "Puss In Boots: The Last Wish", "Puss_In_Boots.jpg", usedImages);
        addPredefinedPost(posts, "Captain America 2", "Captain_America.jpg", usedImages);
    }

    private void addPredefinedPost(List<Post> posts, String title, String imageName, Set<String> usedImages) {
        // Check if the image name is already used, if not, add the post
        if (!hasPosts()) 
        {
            String imagePath = "/static/images/" + imageName; // Adjust the path as per your folder structure
            byte[] imageData = loadImage(imagePath);
            if (imageData != null) 
            {
                Post post = new Post();
                post.setTitle(title);
                post.setImage(imageData);
                try 
                {
                    storePost(post);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
                usedImages.add(imageName);
            }
        }
    }

    private byte[] loadImage(String imagePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + imagePath);
            InputStream inputStream = resource.getInputStream();
            return StreamUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            // Handle error
            return null;
        }
    }

    public void storePost(Post post) throws IOException 
    {
        post.setTitle(post.getTitle());
        
        // Resize and compress image before storing
        byte[] resizedImageBytes = resizeAndCompressImage(post.getImage());
        post.setImage(resizedImageBytes);
        
        postRepository.storePost(post);
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
