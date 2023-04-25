package me.snowlight.springtransactionisoration01.domain;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface TeamRepository {
    public Team findById(Long id);
    public long findCount();
    public void save(Team team);
    public void update(Team team);
}
