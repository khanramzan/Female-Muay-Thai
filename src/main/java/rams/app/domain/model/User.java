package rams.app.domain.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Long userId;

	private String email;

	private boolean enabled;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	private String password;

	@Column(name="token_expired")
	private boolean tokenExpired;
	
	//bi-directional many-to-one association to Role
	@OneToMany(mappedBy="user" , fetch=FetchType.EAGER )
	private List<Role> roles;

	
	public User() {
		super();
		this.enabled = false;
		this.tokenExpired = false;
	}

	public User(User user){
		
		super();
		this.email=user.email;
		this.enabled=user.enabled;
		this.firstName=user.firstName;
		this.lastName=user.lastName;
		this.password=user.password;
		this.roles=user.roles;
		this.tokenExpired=user.tokenExpired;
	
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}


	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}


	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Role addRole(Role role) {
		getRoles().add(role);
		role.setUser(this);

		return role;
	}

	public Role removeRole(Role role) {
		getRoles().remove(role);
		role.setUser(null);

		return role;
	}

}