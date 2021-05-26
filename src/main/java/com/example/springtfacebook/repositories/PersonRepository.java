package com.example.springtfacebook.repositories;

import com.example.springtfacebook.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findPersonByEmail(String email);
}
