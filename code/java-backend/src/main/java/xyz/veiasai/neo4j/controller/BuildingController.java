package xyz.veiasai.neo4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Address;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.service.AddressService;
import xyz.veiasai.neo4j.service.BuildingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private AddressService addressService;

    @PostMapping("/building")
    public Building postBuilding(@RequestBody @Valid  Building building,@RequestParam String address, BindingResult bindingResult)
    {
        Building b = buildingService.findByAddressAndName(building.getName(), address);
        if (b != null)
            return b;
        Address addr = addressService.addAddress(new Address(address));
        building.setAddress(addr);
        return buildingService.addBuilding(building);
    }

}
