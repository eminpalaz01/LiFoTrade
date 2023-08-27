package com.infina.lifotradeapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.infina.lifotradeapi.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 20)
	private String name;
	
	@Column(name = "surname", length = 20)
	private String surname;

	@Column(name = "username", length = 20, unique = true)
	private String username;
	
	@Column(name = "title", length = 20)
	private String title;

	@Column(name = "password")
	private String password;

	@Column(name = "email", length = 50, unique = true)
	private String email;
	
	@Column(name = "is_login_before")
	private Boolean isLoginBefore;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;
	
	@JsonBackReference
	@OneToMany(mappedBy = "performedBy", fetch = FetchType.LAZY)
	private List<FundTransaction> fundTransactions = new ArrayList<FundTransaction>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "performedBy", fetch = FetchType.LAZY)
	private List<AccountTransaction> accountransactions = new ArrayList<AccountTransaction>();	
}
