package com.example.Smartalumni.repository;

import com.example.Smartalumni.entity.User;
import com.example.Smartalumni.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // AUTH
    Optional<User> findByEmail(String email);

    Optional<User> findByResetToken(String resetToken);

    // GET USERS BY ROLE
    List<User> findByRole(Role role);

    // ALUMNI SEARCH FILTERS
    List<User> findByRoleAndJobTitleContainingIgnoreCase(Role role, String jobTitle);

    List<User> findByRoleAndSkillsContainingIgnoreCase(Role role, String skills);

    List<User> findByRoleAndCompanyContainingIgnoreCase(Role role, String company);

    List<User> findByRoleAndLocationContainingIgnoreCase(Role role, String location);

    List<User> findByRoleAndGraduationYear(Role role, String graduationYear);

    List<User> findByRoleAndHigherEducationContainingIgnoreCase(Role role, String education);

    List<User> findByRoleAndDepartmentContainingIgnoreCase(Role role, String department);
}
