package br.com.ligabue.communication_api.repository;

import br.com.ligabue.communication_api.entity.CommunicationScheduled;
import br.com.ligabue.communication_api.enumeration.CommunicationScheduledStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationScheduledRepository extends JpaRepository<CommunicationScheduled, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE CommunicationScheduled c SET c.status = :status WHERE c.id = :id ")
    Integer updateStatusById(@Param("id") Long id, @Param("status") CommunicationScheduledStatus status);
}
