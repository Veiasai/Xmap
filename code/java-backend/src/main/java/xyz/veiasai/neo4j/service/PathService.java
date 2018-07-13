package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.repositories.BuildingRepository;
import xyz.veiasai.neo4j.repositories.NodeRepository;
import xyz.veiasai.neo4j.repositories.PathRepository;

import java.util.Collection;

@Service
@Transactional
public class PathService {
    @Autowired
    private PathRepository pathRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Collection<Path> findByName(String name,Integer skip,Integer limit)
    {
        return  pathRepository.findByNameLike(name,skip,limit);
    }

    public Path addPath(Path path)
    {
        path.setId(null);
        path.setState(1);
        return pathRepository.save(path);
    }

    @Transactional(readOnly = true)
    public Collection<Path> findByOrigin(String id)
    {
        return pathRepository.findByOrigin(id);
    }

    @Transactional(readOnly = true)
    public Collection<Path> findByBuildingAndName(String buildingId, String name,Integer skip,Integer limit)
    {
        return pathRepository.findByBuildingAndName(buildingId,name,skip,limit);
    }

    public void addRelation(String pathId, String buildingId, String author, String origin, String end) {
        pathRepository.addRelationAuthorAndBuilding(pathId,buildingId,author);
        pathRepository.addRelationOriginAndEnd(pathId, origin, end);
    }

    @Transactional(readOnly = true)
    public Collection<Path> findByOriginAndEnd(String originId, String endId) {
        return pathRepository.findByOriginAndEnd(originId, endId);
    }

    @Transactional(readOnly = true)
    public Collection<Path> findByAuthorId(String authorId, String name,Integer skip,Integer limit)
    {
        return pathRepository.findByAuthorId(authorId,name,skip,limit);
    }
}
