package com.synchrony.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import com.synchrony.springboot.model.Image;
import com.synchrony.springboot.model.User;
import com.synchrony.springboot.repository.ImageRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.synchrony.springboot.response.ImgurApiResponse;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    
    private final String BEARER_TOKEN = "b02b1a8164fd73cbe0c1189f236bd03794922b6e";
    // private final String CLIENT_ID = "0db31a4457dcdc0";
    // private final String CLIENT_ID_SECRET = "0db31a4457dcdc0";
    private final String url = "https://thumbs.dreamstime.com/b/set-elements-fir-tree-branches-christmas-isolated-white-transparent-background-add-png-file--happy-happy-new-year-135296751.jpg";
    
    public Image uploadImage(User user) throws Exception {
        // check if user exists
        // if image exists in the user
        // if session is not expired
        // add the image to the user
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
        return new Image(imageUrl, user);
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
