package com.guilhermeduarte.projectecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guilhermeduarte.projectecommerce.dto.UserDTO;
import com.guilhermeduarte.projectecommerce.entities.Role;
import com.guilhermeduarte.projectecommerce.entities.User;
import com.guilhermeduarte.projectecommerce.projections.UserDetailsProjection;
import com.guilhermeduarte.projectecommerce.repositories.UserRepository;
import com.guilhermeduarte.projectecommerce.services.exceptions.DatabaseException;
import com.guilhermeduarte.projectecommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(@NonNull Pageable pageable) {
		Page<User> list = repository.findAll(pageable);
		
		return list.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(@NonNull Long id) {
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		
		UserDTO dto = new UserDTO(entity);
		
		return dto;
	}
	
	@Transactional
	public UserDTO insert(UserDTO dto) {	
		
		User entity = new User();
		
		copyDTOToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		UserDTO newDTO = new UserDTO(entity);
		
		return newDTO;
	}
	
	@Transactional
	public UserDTO update(@NonNull Long id, UserDTO dto) {
		try {
			User entity = repository.getReferenceById(id);
			
			copyDTOToEntity(dto, entity);
			
			entity = repository.save(entity);
			
			UserDTO newDTO = new UserDTO(entity);
			
			return newDTO;
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(@NonNull Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		
		try {
			repository.deleteById(id);    		
		}
	    catch (DataIntegrityViolationException e) {
	        
	    	throw new DatabaseException("Falha de integridade referencial");
	   	}
	}
	
	private void copyDTOToEntity(UserDTO dto, User entity) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setPhone(dto.getPhone());
		entity.setBirthDate(dto.getBirthDate());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserDetailsProjection> users = repository.searchUserAndRolesByEmail(username);
		
		if(users.size() == 0) {
			throw new UsernameNotFoundException("User not found");
		}
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(users.get(0).getPassword());
		
		for(UserDetailsProjection projection: users) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		
		return user;
	}
	
	protected User authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			
			return repository.findByEmail(username).get();
		}
		catch(Exception error) {
			throw new UsernameNotFoundException("Email not found");
		}
	}
	
	@Transactional(readOnly = true)
	public UserDTO me() {
		User user = authenticated();
		return new UserDTO(user);
	}
}
