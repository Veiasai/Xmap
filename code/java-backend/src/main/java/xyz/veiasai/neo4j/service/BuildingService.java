package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.repositories.BuildingRepository;

import java.util.Optional;

@Service
@Transactional
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    public Building addBuilding(Building building)
    {
        building.setId(null);
        return buildingRepository.save(building);
    }

    @Transactional(readOnly = true)
    public Building findById(String id)
    {
        Optional<Building> optionalBuilding = buildingRepository.findById(id);
        return optionalBuilding.orElse(new Building(id));
    }

    @Transactional(readOnly = true)
    public Building findByAddressAndName(String name, String address)
    {
        return buildingRepository.findByNameAndAddress(name, address);
    }
}
