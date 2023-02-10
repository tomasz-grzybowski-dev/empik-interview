package com.empik.interview.requestcounting.repository;

import com.empik.interview.requestcounting.entity.LoginRequestCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestCountRepository extends JpaRepository<LoginRequestCountEntity, String> {

}
