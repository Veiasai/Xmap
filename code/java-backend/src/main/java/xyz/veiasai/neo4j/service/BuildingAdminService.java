package xyz.veiasai.neo4j.service;

import org.neo4j.ogm.annotation.StartNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.repositories.BuildingAdminRepository;
import xyz.veiasai.neo4j.repositories.BuildingRepository;
import xyz.veiasai.neo4j.result.Result;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class BuildingAdminService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingAdminRepository buildingAdminRepository;

    @Transactional(readOnly = true)
    public void addBuildingAdmin(String buildingId, String authorId){
        buildingAdminRepository.addBuildingAdmin(buildingId, authorId);
    }
    @Transactional(readOnly = true)
    public void deleteBuildingAdmin(String buildingId,String authorId){
        buildingAdminRepository.deleteBuildingAdmin(buildingId, authorId);
    }


    @Transactional(readOnly = true)
    public boolean existBuildingAdmin(String buildingId,String authorId){
        if(buildingAdminRepository.countBuildingAdmin(buildingId, authorId)!=0){
            return true;    //buildlingId和authorId都有效且相连的时候 才为true
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Collection<Building> findBuildingByAdmin(String authorId){   //authorId是否有效可放到controller层
        return buildingAdminRepository.findBuildingByAdmin(authorId);
    }

    @Transactional(readOnly = true)
    public Collection<Author> findAdminByBuildingId(String buildingId){
        return buildingAdminRepository.findAdminByBuildingId(buildingId);
    }

}
