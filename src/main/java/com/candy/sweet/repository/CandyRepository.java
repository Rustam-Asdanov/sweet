package com.candy.sweet.repository;

import com.candy.sweet.model.Candy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandyRepository extends JpaRepository<Candy, Long> {
    Candy getCandyById(long id);
}
