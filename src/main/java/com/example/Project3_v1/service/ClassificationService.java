package com.example.Project3_v1.service;

import com.example.Project3_v1.entity.Classification;
import com.example.Project3_v1.repository.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {
    @Autowired private ClassificationRepository classficationRepository;

    public Classification createClassification(Classification classification) {
        classification.setName(classification.getName());
        return classficationRepository.save(classification);
    }

//        classification1.setName("Food");
//        classficationRepository.save(classification1);
//
//        Classification classification2 = new Classification();
//        classification2.setName("Beverage & Drink");
//        classficationRepository.save(classification2);
//
//        Classification classification3 = new Classification();
//        classification3.setName("Fashion & Accessories");
//        classficationRepository.save(classification3);
//
//        Classification classification4 = new Classification();
//        classification4.setName("Home Appliances & Electronics");
//        classficationRepository.save(classification4);
//
//        Classification classification5 = new Classification();
//        classification5.setName("Vehicles & Motorcycles");
//        classficationRepository.save(classification5);
//
//        Classification classification6 = new Classification();
//        classification6.setName("Handmade Goods & Crafts");
//        classficationRepository.save(classification6);


    public List<Classification> getAllClassifications() {
        return classficationRepository.findAll();
    }


}
