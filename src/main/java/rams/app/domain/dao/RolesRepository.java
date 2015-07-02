package rams.app.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.Role;
import rams.app.domain.model.User;



public interface RolesRepository extends JpaRepository<Role, Long> {
    public Role findByRole(String name);
    
    public List<Role> findByUser(User user); 

    public void delete(Role role);
}