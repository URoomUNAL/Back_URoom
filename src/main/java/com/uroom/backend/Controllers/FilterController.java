package com.uroom.backend.Controllers;

import com.uroom.backend.Models.Post;
import com.uroom.backend.Models.Rule;
import com.uroom.backend.Models.Service;
import com.uroom.backend.POJOS.FilterPOJO;
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

    @PostMapping("/test-distance-filter")
    public List<Post> distanceFilter(@RequestBody FilterPOJO filterPost){
        return postService.filterDistance(filterPost.getDistance().getOrigin().get(0), filterPost.getDistance().getOrigin().get(1), filterPost.getDistance().getRadius());
    }

    @PostMapping("/get-posts-filtered")
    public List<Post> getAll(@RequestBody FilterPOJO filterPost){
        Set<Service> services = serviceService.selectBySetNames(filterPost.getServices());
        Set<Rule> rules = ruleService.selectBySetNames(filterPost.getRules());
        boolean filterService =true;
        boolean filterRules = (filterPost.getRules() == null) ? false : true;

        if( (filterPost.getServices().size() == 0)) filterService = false;
        if( (filterPost.getRules().size() == 0)) filterRules = false;
        List<Post> posts;
        if(filterService && filterRules){
            posts = postService.filterAll(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(),filterPost.getMin_score(),services.iterator().next(),rules.iterator().next());
            posts = completeFilterServices(posts, services);
            posts = completeFilterRules(posts, rules);
        }
        else if(filterRules){
            posts = postService.filterRules(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(),filterPost.getMin_score(),rules.iterator().next());
            posts = completeFilterRules(posts, rules);
        }
        else if(filterService){
            posts = postService.filterServices(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(),filterPost.getMin_score(),services.iterator().next());
            posts = completeFilterServices(posts, services);
        }
        else{
            posts = postService.filterBasic(filterPost.getPrice().getMin(),filterPost.getPrice().getMax(),filterPost.getMin_score());
        }
        return posts;
    }
}
