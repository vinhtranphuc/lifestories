package com.tranphucvinh.jpa.entity;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;

import com.tranphucvinh.jpa.entity.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User extends DateAudit {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Size(max = 255)
    private String full_name;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    private String username;

    @Size(max = 40)
    private String email;

    @Size(max = 100)
    @Column(name = "password")
    private String password;
    
    @Size(max = 200)
    private String summary;

    private boolean enabled;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String provider_id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Size(max = 1)
    private String type;

    @Size(max = 50)
    private String occupation;

    @Size(max = 50)
    private String company_name;

    @Size(max = 14)
    private String phone;

    @Size(max = 500)
    private String address;

    @Size(max = 50)
    private String city;

    @Size(max = 50)
    private String country;

    @Size(max = 500)
    private String avatar_img;

    private String social_avatar_url;

    @CreatedDate
    private LocalDateTime join_date;

    @Size(max = 500)
    private String note;

    public User() {
    }

    public User(String full_name, String username, String password) {
        this.full_name = full_name;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getAvatar_img() {
        return avatar_img;
    }

    public void setAvatar_img(String avatar_img) {
        this.avatar_img = avatar_img;
    }

    public String getSocial_avatar_url() {
        return social_avatar_url;
    }

    public void setSocial_avatar_url(String social_avatar_url) {
        this.social_avatar_url = social_avatar_url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
