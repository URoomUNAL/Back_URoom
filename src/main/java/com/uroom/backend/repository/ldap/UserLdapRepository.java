package com.uroom.backend.repository.ldap;

import com.uroom.backend.models.ldap.UserLdap;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLdapRepository extends LdapRepository<UserLdap> {
    UserLdap findByEmail(String email);

}
