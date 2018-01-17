package app.repository;

import app.entity.WhitelistPoolElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Олег on 21.08.2017.
 */
public interface WhitelistPoolElementRepository extends JpaRepository<WhitelistPoolElement, String> {

}
