package com.sistemaclinica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;



@Entity
@Table(name="medico")
public class Medico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String nome;
	private String sexo;
	private Date nascimento;
	private String email;
	private String cpf;
	private String rg;
	private String numeroConselho;
	private String telefone1;
	private String telefone2;
	
	private List<Especialidade> especialidades;
	private List<AgendaMedico> agenda;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_medico")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotBlank
	@Column(nullable = false, length = 200)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Email
	@NotEmpty
	@Column(unique = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotBlank(message = "deve ser informado") @CPF(message = "inválido")
	@Column(nullable = false, length = 14, unique = true)
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@NotNull
	@Column(nullable = false)
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = false)
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	
	@NotBlank
	@Column(nullable = false)
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	@NotBlank
	@Column(nullable = false)
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "medico_has_especialidades", joinColumns = @JoinColumn(name = "id_medico"),
				inverseJoinColumns = @JoinColumn(name = "id_especialidade"))
	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
	@NotBlank
	@Column(name="numero_conselho", length = 10)
	public String getNumeroConselho() {
		return numeroConselho;
	}
	public void setNumeroConselho(String numeroConselho) {
		this.numeroConselho = numeroConselho;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medico other = (Medico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@OneToMany(mappedBy = "medico", targetEntity = AgendaMedico.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<AgendaMedico> getAgenda() {
		if(agenda !=null) {
			return agenda;
		}else
			return new ArrayList<AgendaMedico>();
		
	}
	public void setAgenda(List<AgendaMedico> agenda) {
		this.agenda = agenda;
	}
	
	
}
