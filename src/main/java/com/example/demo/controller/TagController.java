package com.example.demo.controller;

import com.example.demo.dto.TagDTO;
import com.example.demo.entity.Tag;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping( "/tags")
@CrossOrigin("http://localhost:4200")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping( "/getAll")
    @ResponseBody
    public List<Tag> retrieveTags() {
        return tagService.retrieveTags();
    }

    @GetMapping( "/getTitles")
    @ResponseBody
    public List<String> getTitles() {
        return tagService.getTitles();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public Tag retrieveById(@PathVariable Long id){
        return tagService.retrieveTagById(id);
    }

    @GetMapping("/getByQuestionId/{id}")
    @ResponseBody
    public Set<Tag> retrieveByQuestionId(@PathVariable Long id){
        return tagService.retrieveTagByQuestionId(id);
    }

    @GetMapping("/getByTitle/{title}")
    @ResponseBody
    public Tag getByTag(@PathVariable String title){
        return tagService.getByTitle(title);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public String deleteById(@PathVariable Long id){
        return tagService.deleteById(id);
    }

    @PostMapping("/insertTag")
    @ResponseBody
    public Tag insertTag(@RequestBody Tag tag){
        return tagService.saveTag(tag);
    }

}
