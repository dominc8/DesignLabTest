package com.dominc8.springbootvuejs.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "security_users")
public class SecurityUser {

    @Id
    @GenericGenerator(
            name = "security_user_sequence",
            strategy = "sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence",
                            value = "SECURITY_USER_SEQ"
                    )

            })
    @GeneratedValue(generator = "security_user_sequence")
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Column(nullable = false)
    private String authority;

    @Column
    private boolean enabled;

    protected SecurityUser() {}

    public SecurityUser(long id, String username, String password, String authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.enabled = true;
    }

    public SecurityUser(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.enabled  = true;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthority() {
        return authority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String changePassword(String newPassword) {
        this.password = new BCryptPasswordEncoder().encode(newPassword);
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
