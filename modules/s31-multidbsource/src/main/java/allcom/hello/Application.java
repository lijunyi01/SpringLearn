package allcom.hello;

import allcom.App;
import allcom.db.DatabaseContextHolder;
import allcom.db.DynamicDataSource;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan(basePackageClasses = App.class)
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    //@Autowired
    //private DynamicDataSource dataSource;

    @Bean
    BookingService bookingService() {
        return new BookingService();
    }

    //在本bean实例化时只会处理<property name="defaultTargetDataSource" ref="dataSource2">指定的数据源
    //因此使用DynamicDataSource时，bean定义里不能有DDL/DML操作，否则非默认数据源不会执行该DDL/DML
    @Bean
    JdbcTemplate jdbcTemplate(DynamicDataSource dataSource) {
        //DatabaseContextHolder.setDbType("dataSource1");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //DatabaseContextHolder.clearDbType();
        log.info("Creating tables");

        //mysql
        jdbcTemplate.execute("drop table if exists BOOKINGS");
        jdbcTemplate.execute("create table BOOKINGS("
                + "ID serial, FIRST_NAME varchar(5) NOT NULL)");
        return jdbcTemplate;
    }

//    @Bean
//    JdbcTemplate jdbcTemplate1(@Qualifier("dataSource1") DataSource dataSource) {
//        //DatabaseContextHolder.setDbType("dataSource1");
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        //DatabaseContextHolder.clearDbType();
//        log.info("Creating tables");
//
//        //mysql
//        jdbcTemplate.execute("drop table if exists BOOKINGS");
//        jdbcTemplate.execute("create table BOOKINGS("
//                + "ID serial, FIRST_NAME varchar(5) NOT NULL)");
//        return jdbcTemplate;
//    }
//
//    @Bean
//    JdbcTemplate jdbcTemplate2(@Qualifier("dataSource2") DataSource dataSource) {
//        //DatabaseContextHolder.setDbType("dataSource2");
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        //DatabaseContextHolder.clearDbType();
//        log.info("Creating tables");
//
//        //mysql
//        jdbcTemplate.execute("drop table if exists BOOKINGS");
//        jdbcTemplate.execute("create table BOOKINGS("
//                + "ID serial, FIRST_NAME varchar(5) NOT NULL)");
//        return jdbcTemplate;
//    }


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        BookingService bookingService = ctx.getBean(BookingService.class);
        DatabaseContextHolder.setDbType("ds1");

        bookingService.book("Alice", "Bob", "Carol");
        Assert.assertEquals("First booking should work with no problem", 3,
                bookingService.findAllBookings().size());

        try {
            bookingService.book("Chris", "Samuel");
        }
        catch (RuntimeException e) {
            log.info("v--- The following exception is expect because 'Samuel' is too big for the DB ---v");
            log.error(e.getMessage());
        }

        for (String person : bookingService.findAllBookings()) {
            log.info("So far, " + person + " is booked.");
        }
        log.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, and Chris was rolled back in the same TX");
        Assert.assertEquals("'Samuel' should have triggered a rollback", 3,
                bookingService.findAllBookings().size());

        try {
            bookingService.book("Buddy", null);
        }
        catch (RuntimeException e) {
            log.info("v--- The following exception is expect because null is not valid for the DB ---v");
            log.error(e.getMessage());
        }

        for (String person : bookingService.findAllBookings()) {
            log.info("So far, " + person + " is booked.");
        }
        log.info("You shouldn't see Buddy or null. null violated DB constraints, and Buddy was rolled back in the same TX");
        Assert.assertEquals("'null' should have triggered a rollback", 3, bookingService
                .findAllBookings().size());

        DatabaseContextHolder.clearDbType();

    }

}
