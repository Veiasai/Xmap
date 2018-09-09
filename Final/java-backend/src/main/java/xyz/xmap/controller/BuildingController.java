package xyz.xmap.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.xmap.domain.Address;
import xyz.xmap.domain.Building;
import xyz.xmap.service.AddressService;
import xyz.xmap.service.BuildingService;

import javax.validation.Valid;

@Api(value = "building-controller")
@RestController
@RequestMapping("/")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private AddressService addressService;

    @PostMapping("/building")
    public Building postBuilding(@RequestBody @Valid Building building, @RequestParam String address, BindingResult bindingResult) {
        Building b = buildingService.findByAddressAndName(building.getName(), address);
        if (b != null)
            return b;
        Address addr = addressService.addAddress(new Address(address));
        building.setAddress(addr);
        return buildingService.addBuilding(building);
    }

}
