package com.sandeep.kakfa_Demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sandeep.kakfa_Demo.entity.Userentity;

@Repository
public interface UserRepo extends JpaRepository<Userentity, Long>{

}
