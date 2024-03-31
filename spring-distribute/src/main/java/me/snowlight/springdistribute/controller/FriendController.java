package me.snowlight.springdistribute.controller;

import lombok.RequiredArgsConstructor;
import me.snowlight.springdistribute.model.Friend;
import me.snowlight.springdistribute.service.FriendRepositoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendRepositoryService friendRepositoryService;

    @PostMapping("/friend/{friendId}")
    public String create(@PathVariable Long friendId) {
        Friend friend = friendRepositoryService.save(friendId);
        return friend.toString();
    }

    @GetMapping("/friend")
    public List<Friend> all() {
        return friendRepositoryService.findAll();
    }
}
