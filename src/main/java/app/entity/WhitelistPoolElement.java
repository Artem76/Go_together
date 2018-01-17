package app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Олег on 21.08.2017.
 */

@Entity
@Table(name = "whitelist_pool")
public class WhitelistPoolElement {
    @Id
    private String destination_addres;

    public WhitelistPoolElement() {

    }

    public WhitelistPoolElement(String destination_addres) {
        this.destination_addres = destination_addres;
    }

    public String getDestination_addres() {
        return destination_addres;
    }

    public void setDestination_addres(String destination_addres) {
        this.destination_addres = destination_addres;
    }
}
