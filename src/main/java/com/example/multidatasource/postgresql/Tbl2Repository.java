package com.example.multidatasource.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface Tbl2Repository extends JpaRepository<Tbl2Entity, Long>, JpaSpecificationExecutor<Tbl2Entity> {
}
