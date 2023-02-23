package com.synchrony.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synchrony.springboot.service.ImageService;

@RestController
@RequestMapping("imgur/")
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    
}
