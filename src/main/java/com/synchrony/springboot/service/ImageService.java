package com.synchrony.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synchrony.springboot.repository.ImageRepository;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
}
