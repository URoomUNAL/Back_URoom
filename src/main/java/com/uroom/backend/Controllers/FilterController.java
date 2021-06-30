package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Rule;
import com.uroom.backend.Models.EntitiyModels.Service;
import com.uroom.backend.Models.RequestModels.FilterRequest;
import com.uroom.backend.Services.PostService;
import com.uroom.backend.Services.RuleService;
import com.uroom.backend.Services.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class FilterController {
    private final RuleService ruleService;
    private final ServiceService serviceService;
    private final PostService postService;

    public FilterController(RuleService ruleService, ServiceService serviceService, PostService postService){
        this.serviceService = serviceService;
        this.postService = postService;
        this.ruleService = ruleService;
    }
    public List<Post> activePosts(List<Post> posts){
        List<Post> activePosts = new ArrayList<>();
        for(Post post : posts){
            if(post.isIs_active()) {
                activePosts.add(post);
            }
        }
        return activePosts;
    }
    public List<Post> completeFilterServices(List<Post> posts, Set<Service> services){
        List<Post> filteredPostByServices = new ArrayList<>();
        for(Post post : posts){
            if(post.getServices().containsAll(services)) {
                filteredPostByServices.add(post);
            }
        }
        return filteredPostByServices;
    }
    public List<Post> completeFilterRules(List<Post> posts, Set<Rule> rules){
        List<Post> filteredPostByRules = new ArrayList<>();
        for(Post post : posts){
            if(post.getRules().containsAll(rules)) {
                filteredPostByRules.add(post);
            }
        }
        return filteredPostByRules;
    }

    public List<Post> match(List<Post> posts, List<Post> posts2){
        List<Post> postMatch = new ArrayList<>();
        for(Post post : posts){
            for(Post post2 : posts2){
                if(post.getId()==post2.getId()) {
                    postMatch.add(post);
                    break;
                }
            }
        }
        return postMatch;
    }

    @PostMapping("/test-distance-filter")
    public List<Post> distanceFilter(@RequestBody FilterRequest filterPost){
        return postService.filterDistance(filterPost.getDistance().getOrigin().get(0), filterPost.getDistance().getOrigin().get(1), filterPost.getDistance().getRadius());
    }

    @PostMapping("/get-posts-filtered")
    public List<Post> getAll(@RequestBody FilterRequest filterPost){
        Set<Service> services = serviceService.selectBySetNames(filterPost.getServices());
        Set<Rule> rules = ruleService.selectBySetNames(filterPost.getRules());
        boolean filterService =true;
        boolean filterRules = true;
        boolean filterScore = true;

        if( (filterPost.getServices().size() == 0)) filterService = false;
        if( (filterPost.getRules().size() == 0)) filterRules = false;
        if( filterPost.getMin_score() == null) filterScore = false;
        List<Post> posts;
        if(filterService && filterRules){
            if(filterScore){
                posts = postService.filterAll(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(),filterPost.getMin_score(),services.iterator().next(),rules.iterator().next());
            }
            else{
                posts = postService.filterAllNoScore(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(), services.iterator().next(),rules.iterator().next());
            }
            posts = completeFilterServices(posts, services);
            posts = completeFilterRules(posts, rules);
        }
        else if(filterRules){
            if(filterScore){
                posts = postService.filterRules(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(),filterPost.getMin_score(),rules.iterator().next());
            }
            else{
                posts = postService.filterRulesNoScore(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(), rules.iterator().next());
            }
            posts = completeFilterRules(posts, rules);
        }
        else if(filterService){
            if(filterScore){
                posts = postService.filterServices(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(),filterPost.getMin_score(),services.iterator().next());
            }else{
                posts = postService.filterServicesNoScore(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(), services.iterator().next());
            }
            posts = completeFilterServices(posts, services);
        }
        else{
            if(filterScore){
                posts = postService.filterBasic(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(),filterPost.getMin_score());
            }
            else {
                posts = postService.filterBasicNoScore(filterPost.getPrice().getMin(),filterPost.getPrice().getMax());
            }
        }
        posts = activePosts(posts); // Just Active posts
        if(filterPost.getDistance().getOrigin().size() != 0) {
            List<Post> postDistance = postService.filterDistance(filterPost.getDistance().getOrigin().get(0), filterPost.getDistance().getOrigin().get(1), filterPost.getDistance().getRadius());
            List<Post> finalPost = match(postDistance, posts);
            return finalPost;
        }
        return posts;
    }
}
