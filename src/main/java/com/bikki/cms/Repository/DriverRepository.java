package com.bikki.cms.Repository;

import com.bikki.cms.entity.DriverRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<DriverRegistration, Integer> {}
