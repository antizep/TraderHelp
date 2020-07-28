package traderHelp.repository;

import org.springframework.data.repository.CrudRepository;

import traderHelp.entity.EntityOrder;

public interface OrderRepository extends CrudRepository<EntityOrder, Integer> {
	
}
