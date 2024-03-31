package me.snowlight.springdistribute.service;

import lombok.RequiredArgsConstructor;
import me.snowlight.springdistribute.config.database.Sharding;
import me.snowlight.springdistribute.config.database.ShardingTarget;
import me.snowlight.springdistribute.model.Friend;
import me.snowlight.springdistribute.model.FriendRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(
//        value = "friendTransactionManager",
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRES_NEW,
        readOnly = true)
@Sharding(target = ShardingTarget.FRIEND)
@RequiredArgsConstructor
public class FriendRepositoryService {

    private final FriendRepository repository;

    @Transactional(
//        value = "friendTransactionManager",
            isolation = Isolation.READ_COMMITTED)
    public Friend save(long id) {
        return repository.save(new Friend(id, id + "-name"));
    }

    public List<Friend> findAll() {
        return repository.findAll();
    }

}
