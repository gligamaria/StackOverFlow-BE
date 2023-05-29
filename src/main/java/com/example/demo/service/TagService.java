package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.TagDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    QuestionService questionService;


    public List<Tag> retrieveTags() {
        return (List<Tag>) tagRepository.findAll();
    }

    public Tag retrieveTagById(Long id) {

        return tagRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tag does not exist")
        );
    }

    public TagDTO retrieveTagByIdDTO(Long cnp) {

        Optional<Tag> tag = tagRepository.findById(cnp);

        if(tag.isPresent()) {
            return new TagDTO(tag.get().getTitle());
        } else {
            return null;
        }
    }

    public String deleteById(Long id){
        try{
            tagRepository.deleteById(id);
            return "Success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    public Tag saveTag(Tag tag){
        return tagRepository.save(tag);
    }

    public Tag getByTitle(String title) {
        List<Tag> allTags = this.retrieveTags();

        for(Tag tag: allTags){
            if(tag.getTitle().equals(title)){
                return tag;
            }
        }

        return null;
    }

    public List<String> getTitles(){
        List<String> titles = new ArrayList<>();
        List<Tag> tags = this.retrieveTags();
        for(Tag tag:tags){
            titles.add(tag.getTitle());
        }
        return titles;
    }

    public Set<Tag> retrieveTagByQuestionId(Long id) {
        Question question = questionService.retrieveQuestionById(id);
        return question.getQuestionTags();
    }
}
