package com.example.mutsasns.repository;

import com.example.mutsasns.entity.Alarm;
import com.example.mutsasns.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findAllByUser(Pageable pageable, User user);
}
