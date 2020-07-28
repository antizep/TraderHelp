package traderHelp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import traderHelp.entity.EntityTools;

@Repository
public interface ToolsRepository extends CrudRepository<EntityTools, Integer> {
	EntityTools findBySecId(String secId);
}
