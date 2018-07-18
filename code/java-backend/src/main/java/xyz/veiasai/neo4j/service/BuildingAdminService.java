package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.repositories.BuildingAdminRepository;
import xyz.veiasai.neo4j.repositories.BuildingRepository;

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
    public void addRelationBuildingAndAuthor(String buildingId,String authorId){
        buildingAdminRepository.addBuildingAdmin(buildingId, authorId);
    }

    @Transactional(readOnly = true)
    public void deleteRelationBuildingAndAuthor(String buildingId,String authorId){
        buildingAdminRepository.deleteBuildingAdmin(buildingId, authorId);
    }

    @Transactional(readOnly = true)
    public boolean existBuildingAdmin(String buildingId,String authorId){
        if(buildingAdminRepository.countBuildingAdmin(buildingId, authorId)!=0){
            return true;
        }
        return false;
    }



}
