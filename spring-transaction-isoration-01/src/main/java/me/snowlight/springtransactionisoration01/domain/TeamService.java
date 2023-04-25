package me.snowlight.springtransactionisoration01.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

//@Transactional(isolation = Isolation.REPEATABLE_READ)
@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public Team registerTeam(TeamCommand.RegisterTeam registerTeam) {
        Team team = registerTeam.toEntity();
        teamRepository.save(team);
        return team;
    };

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean insert() {
        UUID uuid = UUID.randomUUID();

        String uuidName = uuid.toString();
        log.info(uuid + " : START MEMBER_COUNT : " + uuidName);
        var registerTeam = new TeamCommand.RegisterTeam(uuidName);
        Team team = registerTeam.toEntity();
        teamRepository.save(team);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long lastId = teamRepository.findCount();
        String name = teamRepository.findById(lastId).getName();

        log.info(uuid + " : END MEMBER_COUNT : " + name);
        return uuidName.equals(name);
    }

    public void increase(Long id) {
        UUID uuid = UUID.randomUUID();
        Team teamLoad = teamRepository.findById(id);
        log.info(uuid + " : START MEMBER_COUNT : " + teamLoad.getMemberCount());
        teamLoad.increase();
        teamRepository.update(teamLoad);
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info(uuid + " : PROCESS MEMBER_COUNT : " + teamLoad.getMemberCount());
        log.info(uuid + " : END MEMBER_COUNT : " + teamRepository.findById(id).getMemberCount());
    }

    public Team retrieveTeam(Long id) {
        return teamRepository.findById(id);
    }
}
