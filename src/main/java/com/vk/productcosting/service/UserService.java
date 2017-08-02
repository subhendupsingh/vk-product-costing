package com.vk.productcosting.service;

import com.vk.productcosting.model.Role;
import com.vk.productcosting.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public Role findByRole(String role);
}
