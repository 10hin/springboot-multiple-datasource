package com.example.multidatasource.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface Tbl1Repository extends JpaRepository<Tbl1Entity, Long>, JpaSpecificationExecutor<Tbl1Entity> {
}
