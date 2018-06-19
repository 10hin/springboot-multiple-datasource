package com.example.multidatasource;

import com.example.multidatasource.mysql.Tbl1Entity;
import com.example.multidatasource.mysql.Tbl1Repository;
import com.example.multidatasource.postgresql.Tbl2Entity;
import com.example.multidatasource.postgresql.Tbl2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Main implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Autowired
    private Tbl1Repository tbl1Repository;

    @Autowired
    private Tbl2Repository tbl2Repository;

    @Autowired
    private TransactionalService transactionalService;

    @Override
    public void run(String... args) {

//        this.transactionalService.withMySQLTransaction(() -> {
//            Tbl1Entity tbl1rec1 = new Tbl1Entity();
//            Tbl1Entity tbl1rec2 = new Tbl1Entity();
//            Tbl1Entity tbl1rec3 = new Tbl1Entity();
//            tbl1rec1.setContents("table 1 - record 1 - ukey 101");
//            tbl1rec1.setUk(101);
//            tbl1rec2.setContents("table 1 - record 2 - ukey 102");
//            tbl1rec2.setUk(102);
//            tbl1rec3.setContents("table 1 - record 3 - ukey 103");
//            tbl1rec3.setUk(103);
//
//            this.tbl1Repository.saveAll(Arrays.asList(tbl1rec1, tbl1rec2, tbl1rec3));
//        }).run();

//        this.transactionalService.withPostgreSQLTransaction(() -> {
//            Tbl2Entity tbl2rec1 = new Tbl2Entity();
//            Tbl2Entity tbl2rec2 = new Tbl2Entity();
//            Tbl2Entity tbl2rec3 = new Tbl2Entity();
//            tbl2rec1.setContents("table 2 - record 1 - ukey 201");
//            tbl2rec1.setUKey(201);
//            tbl2rec2.setContents("table 2 - record 2 - ukey 202");
//            tbl2rec2.setUKey(202);
//            tbl2rec3.setContents("table 2 - record 3 - ukey 203");
//            tbl2rec3.setUKey(203);
//
//            this.tbl2Repository.saveAll(Arrays.asList(tbl2rec1, tbl2rec2, tbl2rec3));
//        }).run();

        LOGGER.info("tbl1:{}", this.tbl1Repository.findAll().stream() //
                .map(Objects::toString) //
                .collect(Collectors.joining(System.lineSeparator(), System.lineSeparator(), "")));

        LOGGER.info("tbl2:{}", this.tbl2Repository.findAll().stream() //
                .map(Objects::toString) //
                .collect(Collectors.joining(System.lineSeparator(), System.lineSeparator(), "")));

    }

}
