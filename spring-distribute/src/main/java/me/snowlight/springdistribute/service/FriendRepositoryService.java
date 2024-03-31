package me.snowlight.springdistribute.service;

import me.snowlight.springdistribute.config.database.Sharding;
import me.snowlight.springdistribute.config.database.ShardingTarget;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional(
//        value = "friendTransactionManager",
//        isolation = Isolation.READ_COMMITTED,
//        propagation = Propagation.REQUIRES_NEW,
//        readOnly = true)
@Sharding(target = ShardingTarget.FRIEND)
public class FriendRepositoryService {
}
