package com.synchrony.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synchrony.springboot.model.*;
import com.synchrony.springboot.repository.ImageRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.synchrony.springboot.response.ImgurApiResponse;
import jakarta.persistence.*;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    SessionService sessionService;
    @Autowired
    UserService userService;

    @PersistenceContext
    private EntityManager entityManager;
    
    private final String BEARER_TOKEN = "b02b1a8164fd73cbe0c1189f236bd03794922b6e";
    // private final String CLIENT_ID = "0db31a4457dcdc0";
    // private final String CLIENT_ID_SECRET = "0db31a4457dcdc0";
    
    public Image uploadImage(User user, String url) throws Exception {
        // check if user exists
        // if (userService.getUserById(user.getId()) == null ||  userService.getUserById(user.getId()) != user) {
        //     throw new Error("user does not exist or something went wrong"); // direct to login screen
        // }
        // if image exists in the user
        List<Image> images = getImagesForUser(user).stream()
        .filter(image->image.getUrl().equals(url))
        .collect(Collectors.toList());

        if (images.size() > 0) {
            throw new Error("image is already present");
        }

        // making the API call to imgur
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + BEARER_TOKEN);
        headers.set("Cookie", "IMGURSESSION=8e3ccad0586ddb1bbe23ae652e2d076c; _nc=1");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // new UrlResource(url)
        body.add("image", url);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ImgurApiResponse> response = restTemplate.postForEntity(
            "https://api.imgur.com/3/image", 
            requestEntity, 
            ImgurApiResponse.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("api request failed");
        }
        ImgurApiResponse imgurApiResponse = response.getBody();
        if (imgurApiResponse == null){
            throw new Exception("no image found");
        } 
        String imageUrl = imgurApiResponse.getData().getLink();
        // add the image to the user
        Image image = new Image(imageUrl, user);
        saveOrUpdateImage(image);
        return image;
    }
    
    public Image getImage(String id) {
        //TODO: Code to get image from Imgur API by ID
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("YOUR_ACCESS_TOKEN");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<ImgurApiResponse> responseEntity = restTemplate.exchange("https://api.imgur.com/3/image/IMAGE_ID", HttpMethod.GET, entity, ImgurApiResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            //Image was retrieved successfully, do something with the response
            ImgurApiResponse imgurApiResponse = responseEntity.getBody();
            String imageUrl = imgurApiResponse.getData().getLink();
            //Do something with the image URL
            return new Image(imageUrl);
        } else {
            //Image retrieval failed
            //Handle the error
            throw new Error();
        }
    }
    
    public boolean deleteImage(User user, String id) {
        //TODO: Code to delete image from Imgur API by ID
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("YOUR_ACCESS_TOKEN");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<ImgurApiResponse> responseEntity = restTemplate.exchange("https://api.imgur.com/3/image/IMAGE_ID", HttpMethod.DELETE, entity, ImgurApiResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            //Image was deleted successfully
            return true;
        } else {
            //Image deletion failed
            //Handle the error
            return false;
        }
    }

    public List<Image> getAllImages() {
        List<Image> images = new ArrayList<Image>();
        imageRepository.findAll().forEach(image -> images.add(image));
        return images;
    }

    public Image getImageById(int id) {
        return imageRepository.findById(id).get();
    }

    // using CRUD functions to create and delete user
    public void saveOrUpdateImage(Image image) {
        imageRepository.save(image);
    }

    public void deleteImageById (int id) {
        imageRepository.deleteById(id);
    }

    public List<Image> getImagesForUser(User user) {
        return getAllImages().stream()
        .filter(cur_image->user.getId() == (cur_image.getUser().getId()))
        .collect(Collectors.toList());
    }
}
