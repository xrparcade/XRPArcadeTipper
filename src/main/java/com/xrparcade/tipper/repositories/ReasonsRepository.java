package com.xrparcade.tipper.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xrparcade.tipper.domain.Reason;

@Repository
public interface ReasonsRepository extends CrudRepository<Reason, String> {

}
