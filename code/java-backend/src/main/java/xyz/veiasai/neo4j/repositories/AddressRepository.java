package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import xyz.veiasai.neo4j.domain.Address;

public interface AddressRepository  extends Neo4jRepository<Address, String> {

}
