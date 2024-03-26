package eu.tasgroup.springbootguide.repository;

import eu.tasgroup.springbootguide.repository.model.AccessLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccessLogRepository
    extends JpaRepository<AccessLogEntity, Long>, JpaSpecificationExecutor<AccessLogEntity> {

}
