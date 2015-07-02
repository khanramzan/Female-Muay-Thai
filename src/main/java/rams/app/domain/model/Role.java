package rams.app.domain.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="roles_id")
	private Long rolesId;

	private String role;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id" )
	private User user;

	public Role() {
	}

	public Long getRolesId() {
		return this.rolesId;
	}

	public void setRolesId(Long rolesId) {
		this.rolesId = rolesId;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}