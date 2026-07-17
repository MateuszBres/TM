package com.example.task_manager_backend.user.admin;


import com.example.task_manager_backend.user.AdminFacade;
import com.example.task_manager_backend.user.dto.UpdateRoleRequest;
import com.example.task_manager_backend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
class AdminService {

    private final AdminFacade adminfacade;

    List<UserResponse> getAllUsers() {
        return adminfacade.getAllUsers();
    }

    UserResponse getUserById(Long id) {
        return adminfacade.getUserById(id);
    }

    void updateUserById(Long id, UpdateRoleRequest updateRoleRequest) {
        adminfacade.updateUser(id, updateRoleRequest);
    }

    void deleteUserById(Long id) {
        adminfacade.deleteUserById(id);
    }

}
