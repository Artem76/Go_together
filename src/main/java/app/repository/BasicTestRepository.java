package app.repository;

import app.entity.BasicTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Олег on 20.12.2017.
 */
public interface BasicTestRepository extends JpaRepository<BasicTest, Long>{

    @Query("SELECT c FROM BasicTest c ORDER BY c.date DESC")
    List<BasicTest> getListByPages(Pageable pageable);

    @Query("SELECT c FROM BasicTest c ORDER BY c.date DESC")
    List<BasicTest> getList();

}
