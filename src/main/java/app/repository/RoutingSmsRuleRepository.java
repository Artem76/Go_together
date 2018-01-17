package app.repository;

import app.entity.RoutingSmsRule;
import app.entity.enums.SmsRoutingLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created by Олег on 20.08.2017.
 */
public interface RoutingSmsRuleRepository extends JpaRepository<RoutingSmsRule, String> {
    @Query("SELECT c FROM RoutingSmsRule c where c.level = :level and c.account = :account and c.mcc = :mcc ORDER BY c.mcc, c.ruleOrder DESC")
    List<RoutingSmsRule> findAllByLevelAndAccount(@Param("level") SmsRoutingLevel level,
                                                  @Param("account") Long account,
                                                  @Param("mcc") String mcc);

    @Query("SELECT c FROM RoutingSmsRule c WHERE c.account = :account ORDER BY c.mcc, c.ruleOrder DESC")
    List<RoutingSmsRule> findAllByAccount(@Param("account") Long account);
}
