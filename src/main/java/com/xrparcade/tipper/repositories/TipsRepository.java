package com.xrparcade.tipper.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xrparcade.tipper.domain.Reason;
import com.xrparcade.tipper.domain.Tip;

@Repository
public interface TipsRepository extends CrudRepository<Tip, Integer> {
	public Optional<Tip> findFirstByReasonAndUserOrderByDateDesc(Reason reason, String user);
}
