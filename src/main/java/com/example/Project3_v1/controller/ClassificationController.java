package com.example.Project3_v1.controller;

import com.example.Project3_v1.entity.Classification;
import com.example.Project3_v1.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classification")
public class ClassificationController {
    @Autowired
    ClassificationService classificationService;

    @PostMapping("/create")
    Classification createClassification(
            @RequestBody Classification classification) {
        return classificationService.createClassification(classification);
    }

    @GetMapping
    List<Classification> getClassificationList(){
        return classificationService.getAllClassifications();
    }

}
