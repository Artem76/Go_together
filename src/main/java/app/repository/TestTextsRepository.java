package app.repository;

import app.entity.TestText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Олег on 20.12.2017.
 */
public interface TestTextsRepository extends JpaRepository<TestText, Long>{

    @Query("SELECT c FROM TestText c WHERE c.sourceAddr = :sourceAddr AND c.text = :text")
    List<TestText> getTemplateByAddrAndText(@Param("sourceAddr") String sourceAddr,
                                            @Param("text") String text);

}
