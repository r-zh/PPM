package cn.edu.sdu.sc.spepms.system.common.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base model which Long id as PK, super class of all models
 * 
 * @author Peter Fu
 */
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    // ---------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
