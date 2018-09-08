package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.domain.CountSum;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.repositories.BuildingAdminRepository;
import xyz.veiasai.neo4j.repositories.BuildingRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class BuildingAdminService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingAdminRepository buildingAdminRepository;

    @Transactional()
    public void applyBuildingAdmin(String buildingId, String authorId) {
        buildingAdminRepository.applyBuildingAdmin(buildingId, authorId);
    }
    @Transactional()
    public void setBuildingAdmin(String buildingId, String authorId) {
        buildingAdminRepository.setBuildingAdmin(buildingId, authorId);
    }

    @Transactional()
    public void deleteBuildingAdmin(String buildingId, String authorId) {
        buildingAdminRepository.deleteBuildingAdmin(buildingId, authorId);
    }

    @Transactional()
    public void refuseBuildingAdmin(String buildingId, String authorId){
        buildingAdminRepository.refuseBuildingAdmin(buildingId, authorId);
    }

    @Transactional(readOnly = true)
    public Set<Map<String, Object>> getApply(String buildingId, int skip, int limit) {
        if (buildingId == null)
            return buildingAdminRepository.getApply(skip, limit);
        else
            return buildingAdminRepository.getApplyByBuilding(buildingId, skip, limit);
    }

    @Transactional(readOnly = true)
    public Set<Map<String, Object>> getBuildingAdmin(String buildingId, int skip, int limit){
        if (buildingId == null)
            return buildingAdminRepository.getBuildingAdmin(skip, limit);
        else
            return buildingAdminRepository.getBuildingAdminByBuilding(buildingId, skip, limit);
    }


    @Transactional(readOnly = true)
    public boolean isBuildingAdmin(String authorId){
        return buildingAdminRepository.countBuildingByAdmin(authorId) != 0;
    }

    @Transactional(readOnly = true)
    public boolean existValidBuildingAdmin(String buildingId, String authorId) {
        return buildingAdminRepository.countValidBuildingAdmin(buildingId, authorId) != 0;
    }

    @Transactional(readOnly = true)
    public boolean existApplyBuildingAdmin(String buildingId, String authorId) {
        return buildingAdminRepository.countApplyBuildingAdmin(buildingId, authorId) != 0;
    }
    @Transactional(readOnly = true)
    public Collection<Building> findBuildingByAdmin(String authorId) {   //authorId是否有效可放到controller层
        return buildingAdminRepository.findBuildingByAdmin(authorId);
    }

    @Transactional(readOnly = true)
    public Collection<CountSum> findBuildingAndCountByAdmin(String authorId) {   //authorId是否有效可放到controller层
        return buildingAdminRepository.findBuildingAndCountByAdmin(authorId);
    }

    @Transactional(readOnly = true)
    public Collection<CountSum> findAllBuilding() {   //authorId是否有效可放到controller层
        return buildingAdminRepository.findAllBuilding();
    }

    @Transactional(readOnly = true)
    public Collection<Author> findAdminByBuildingId(String buildingId) {
        return buildingAdminRepository.findAdminByBuildingId(buildingId);
    }

}
