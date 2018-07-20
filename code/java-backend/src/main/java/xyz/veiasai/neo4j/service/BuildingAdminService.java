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

import java.security.PublicKey;
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
    public void applyBuildingAdmin(String buildingId, String authorId) {
        buildingAdminRepository.applyBuildingAdmin(buildingId, authorId);
    }
    @Transactional(readOnly = true)
    public void setBuildingAdmin(String buildingId, String authorId) {
        buildingAdminRepository.setBuildingAdmin(buildingId, authorId);
    }

    @Transactional(readOnly = true)
    public void deleteBuildingAdmin(String buildingId, String authorId) {
        buildingAdminRepository.deleteBuildingAdmin(buildingId, authorId);
    }

    @Transactional(readOnly = true)
    public void refuseBuildingAdmin(String buildingId, String authorId){

    }

    @Transactional(readOnly = true)
    public boolean isBuildingAdmin(String authorId){
        if(buildingAdminRepository.countBuildingByAdmin(authorId)!=0){
            return true;
        }
        return false;
    }
    @Transactional(readOnly = true)
    public boolean existValidBuildingAdmin(String buildingId, String authorId) {
        if (buildingAdminRepository.countValidBuildingAdmin(buildingId, authorId) != 0) {
            return true;    //buildlingId和authorId都有效且相连state=1的时候 才为true
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean existApplyBuildingAdmin(String buildingId, String authorId) {
        if (buildingAdminRepository.countApplyBuildingAdmin(buildingId, authorId) != 0) {
            return true;    //buildlingId和authorId都有效且相连state=0的时候 才为true
        }
        return false;
    }
    @Transactional(readOnly = true)
    public Collection<Building> findBuildingByAdmin(String authorId) {   //authorId是否有效可放到controller层
        return buildingAdminRepository.findBuildingByAdmin(authorId);
    }

    @Transactional(readOnly = true)
    public Collection<Author> findAdminByBuildingId(String buildingId) {
        return buildingAdminRepository.findAdminByBuildingId(buildingId);
    }

}
