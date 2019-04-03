package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.FinantialInstrument;

/**
 * @author Pontalti X
 *
 */
public interface FinantialInstrumentRepository extends JpaRepository<FinantialInstrument, Long> {
	
    @Query("SELECT f.finantialIstrumentNameId, o.status, o.type, COUNT(o), "
    		+ "MIN(o.quantity), MAX(o.quantity), AVG(o.quantity), "
    		+ "MIN(o.instrumentPrice), MAX(o.instrumentPrice), AVG(o.instrumentPrice), "
    		+ "MIN(o.limitPrice), MAX(o.limitPrice), AVG(o.limitPrice), "
    		+ "MIN(o.orderPrice), MAX(o.orderPrice), AVG(o.orderPrice) "
    		+ "FROM FinantialInstrument f JOIN f.orders o GROUP BY f.finantialIstrumentNameId, o.status, o.type")
    public List<Object[]> retrieveStatistics();

}
