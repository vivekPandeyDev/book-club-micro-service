package org.loop.troop.domain.user;

import org.loop.troop.domain.dto.RegisterDto;
import org.loop.troop.domain.dto.UserDto;

public interface UserService {

	UserDto saveUser(RegisterDto registerDto);

	UserDto getUserByUsername(String username);

	void deleteUserById(String id);

	boolean isUserExist(String username);

}
