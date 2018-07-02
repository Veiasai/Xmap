package xyz.veiasai.neo4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.repositories.DataSetRepository;
import xyz.veiasai.neo4j.service.DataSetService;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class DataSetController {
    @Autowired
    private DataSetService dataSetService;

    @PostMapping("/dataset")
    public DataSet postDataSet(@RequestBody @Valid DataSet dataSet, BindingResult bindingResult){
        return dataSetService.addDataSet(dataSet);
    }

}
