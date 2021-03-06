package app.repository;

import app.entity.CustomUser;
import app.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
    @Query("SELECT u FROM CustomUser u where u.login = :login")
    CustomUser findByLogin(@Param("login") String login);

    @Query("SELECT u FROM CustomUser u ORDER BY name ASC")
    List<CustomUser> findAllSort();

    @Query("SELECT u FROM CustomUser u where u.role = :role")
    List<CustomUser> findByRole(@Param("role") UserRole role);

    @Query("SELECT u FROM CustomUser u where u.role <> :role")
    List<CustomUser> findExceptRole(@Param("role") UserRole role);

    @Query("SELECT u FROM CustomUser u where u.login = :login AND u.id <> :id")
    List<CustomUser> findByLoginExceptId(@Param("login") String login,
                                         @Param("id") long id);
}
