package xyz.xmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xmap.domain.Address;
import xyz.xmap.repositories.AddressRepository;

import java.util.Optional;

@Service
@Transactional
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address addAddress(Address address)
    {
        Optional<Address> temp = addressRepository.findById(address.getAddress());
        return temp.orElseGet(()->addressRepository.save(address));
    }
}
