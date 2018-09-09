package xyz.xmap.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import xyz.xmap.domain.Address;

public interface AddressRepository  extends Neo4jRepository<Address, String> {

}
