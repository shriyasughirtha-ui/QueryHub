package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByOrderByReputationDesc();
    List<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String name,
        String email
);
List<User> findByReputationGreaterThanEqual(int reputation);


List<User> findAllByOrderByReputationAsc();

List<User> findAllByOrderByNameAsc();

List<User> findAllByOrderByNameDesc();
List<User> findByRoleIgnoreCase(String role);
long countByRoleIgnoreCase(String role);

long countByEmailEndingWithIgnoreCase(String domain);
List<User> findByReputationBetween(int min, int max);
@Query("SELECT AVG(u.reputation) FROM User u WHERE LOWER(u.role) = LOWER(:role)")
Double findAverageReputationByRole(@Param("role") String role);
@Query("SELECT MAX(u.reputation) FROM User u")
Integer findHighestReputation();

@Query("SELECT MIN(u.reputation) FROM User u")
Integer findLowestReputation();

@Query("SELECT AVG(u.reputation) FROM User u")
Double findAverageReputation();
@Query("""
    SELECT COUNT(u)
    FROM User u
    WHERE u.reputation >= :min
    AND u.reputation <= :max
""")
long countByReputationRange(
        @Param("min") int min,
        @Param("max") int max);

@Query("SELECT MAX(u.reputation) FROM User u WHERE LOWER(u.role) = LOWER(:role)")
Integer findHighestReputationByRole(@Param("role") String role);

@Query("SELECT MIN(u.reputation) FROM User u WHERE LOWER(u.role) = LOWER(:role)")
Integer findLowestReputationByRole(@Param("role") String role);
List<User> findByActiveTrue();
List<User> findByActiveFalse();
long countByActiveTrue();
long countByActiveFalse();
@Query("""
    SELECT
        CASE
            WHEN u.reputation < 10 THEN 'BEGINNER'
            WHEN u.reputation < 50 THEN 'INTERMEDIATE'
            ELSE 'ADVANCED'
        END,
        COUNT(u)
    FROM User u
    GROUP BY
        CASE
            WHEN u.reputation < 10 THEN 'BEGINNER'
            WHEN u.reputation < 50 THEN 'INTERMEDIATE'
            ELSE 'ADVANCED'
        END
""")
List<Object[]> countUsersByReputationLevel();

List<User> findByActiveAndRoleAndReputationGreaterThanEqual(
        boolean active, String role, int reputation);

List<User> findByActiveAndReputationGreaterThanEqual(
        boolean active, int reputation);






}