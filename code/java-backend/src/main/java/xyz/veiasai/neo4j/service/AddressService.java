package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Address;
import xyz.veiasai.neo4j.repositories.AddressRepository;

import java.util.Optional;

@Service
@Transactional
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address addAddress(Address address)
    {
        Optional<Address> temp = addressRepository.findById(address.getAddress());
        return temp.orElse(addressRepository.save(address));
    }
}
