package com.vk.dispatcher.service;

import com.vk.dispatcher.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
