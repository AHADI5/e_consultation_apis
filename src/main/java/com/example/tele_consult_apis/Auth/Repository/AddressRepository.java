package com.example.tele_consult_apis.Auth.Repository;

import com.example.tele_consult_apis.Auth.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address , Long > {
}
