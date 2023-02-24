package com.synchrony.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.synchrony.springboot.model.Image;
import com.synchrony.springboot.model.User;
import com.synchrony.springboot.repository.ImageRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.synchrony.springboot.response.ImgurApiResponse;
import java.io.File;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    private final String LOGIN_ENDPOINT = "https://api.imgur.com/oauth2/token";
    private final String REGISTER_ENDPOINT = "https://api.imgur.com/3/account";
    private final String REFRESH_TOKEN = "b02b1a8164fd73cbe0c1189f236bd03794922b6e";
    private final String CLIENT_ID = "0db31a4457dcdc0";
    private final String CLIENT_SECRET = "40c3d3c9e7051cc379f4fbf77c7aedec096c0f60";
    
    public Image uploadImage(User user, MultipartFile file) {
        //TODO: Code to upload image to Imgur API
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(REFRESH_TOKEN);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FileSystemResource fileResource = new FileSystemResource(new File("PATH_TO_IMAGE_FILE"));
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", fileResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ImgurApiResponse> responseEntity = restTemplate.exchange("https://api.imgur.com/3/image", HttpMethod.POST, requestEntity, ImgurApiResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            try {
                //Image was uploaded successfully, TODO: do something with the response
                ImgurApiResponse imgurApiResponse = responseEntity.getBody();
                String imageUrl = imgurApiResponse.getData().getLink();
                return new Image(imageUrl, user);
            } catch (Exception e) {
                throw new Error(e);
            }
            //TODO: Do something with the image URL
        } else {
            //Image upload failed
            //TODO: Handle the error
            throw new Error();
        }
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
}
