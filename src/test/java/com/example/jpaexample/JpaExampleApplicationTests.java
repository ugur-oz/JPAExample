package com.example.jpaexample;

import com.example.jpaexample.domain.Attendance;
import com.example.jpaexample.domain.User;
import com.example.jpaexample.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaExampleApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;
    // bu datenbank demek aslinda

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    public void testUserEntity() {

        User admin = new User();

        admin.setUsername("admin");
        admin.setPasswordEncoded("abcdef");

        entityManager.persist(admin);
        //bu aslinda insert into demek

        assertNotNull(admin.getId());
        Assertions.assertTrue(admin.getId() > 0);

        User adminFromDb = entityManager.find(User.class, admin.getId());

        assertEquals("admin", adminFromDb.getUsername());
        assertEquals("abcdef", adminFromDb.getPasswordEncoded());

        admin.setPasswordEncoded("fedcba");

        entityManager.persist(admin);

        adminFromDb = entityManager.find(User.class, admin.getId());

        assertEquals("admin", adminFromDb.getUsername());
        assertEquals("fedcba", adminFromDb.getPasswordEncoded());

        entityManager.remove(admin);
        //burda aslinda sql deki satiri silme yapiyorsun !!

        adminFromDb = entityManager.find(User.class, adminFromDb.getId());

        assertNull(adminFromDb);


    }

    @Test
    @Transactional
    public void testAttendanceEntity(){
        // trans anotasyonu burda bir kapi acar ve bu kapi ile datenbanki esitler

        User user = new User();

        user.setUsername("max.muster");
        user.setPasswordEncoded("qwertz");

        entityManager.persist(user);

        assertNotNull(user.getId());

        Attendance attendance = new Attendance();

        attendance.setDate(LocalDateTime.now());
        attendance.setUser(user);

        entityManager.persist(attendance);

        assertNotNull(attendance.getId());

        Attendance attendanceFromDb = entityManager.find(Attendance.class,attendance.getId());

        assertNotNull(attendanceFromDb);
        assertNotNull(attendanceFromDb.getUser());
        assertEquals("max.muster", attendanceFromDb.getUser().getUsername());

        entityManager.clear();  // burda hash hatasi aldik ondan bunu kullandik .refresh() de kullanablirsin
        // yani entityManager.refresch de yapabilirsin
        User maxFromDb = entityManager.find(User.class, user.getId());

        assertEquals(1,maxFromDb.getAttendanceList().size());


    }

    @Test
    public void testUserRepo(){

        User user = new User();
        user.setUsername("brigitte.musterfrau");
        user.setPasswordEncoded("trewq");

        user = userRepository.save(user);

        assertNotNull(user.getId());
        assertTrue(user.getId() > 0);

        Optional<User> userFromDb = userRepository.findById(user.getId());
        assertTrue(userFromDb.isPresent());
        assertEquals("brigitte.musterfrau",userFromDb.get().getUsername());

        Optional<User> max = userRepository.findByUsernameIs("max");
        assertFalse(max.isPresent());

        //isPreset birsey var mi yok mu bakar

        List<User> user2 = userRepository.findByUsernameStartingWith("bri");

        List<User> usersWithoutAttendance = userRepository.findByAttendancesIsEmpty();



    }
}

