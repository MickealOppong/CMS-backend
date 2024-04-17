package opp.mic.payroll.controller;

import lombok.extern.slf4j.Slf4j;
import opp.mic.payroll.exceptions.UserNotFoundException;
import opp.mic.payroll.model.AppUser;
import opp.mic.payroll.model.RoleRequest;
import opp.mic.payroll.model.UserInfoUpdateRequest;
import opp.mic.payroll.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAnyRole('SCOPE_ROLE_MANAGER','SCOPE_ROLE_ADMIN')")
    @GetMapping("/users")
    public Page<AppUser> all(int page) throws DataFormatException, IOException {
        return userService.getAll(page);
    }

    @PostMapping("/user")
    public ResponseEntity<String> add(@RequestParam String username, @RequestParam String password ,@RequestParam String fullname,@RequestParam MultipartFile file){
        AppUser appUser = new AppUser(username, fullname,
                passwordEncoder.encode(password));
        try {
         AppUser user= userService.save(appUser,file);
         if(user != null){
             return ResponseEntity.ok().body("User created");
         }
            return ResponseEntity.ok().body("Please check username or password created");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public AppUser appUser(@RequestParam Long id){
        try {
            return userService.getUser(id);
        } catch (DataFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('SCOPE_ROLE_MANAGER','SCOPE_ROLE_ADMIN','SCOPE_ROLE_USER')")
    @GetMapping("/userList")
    public ResponseEntity<List<String>> allUsers(){
        return ResponseEntity.ok().body(userService.all());
    }


    @PreAuthorize("hasAnyRole('SCOPE_ROLE_MANAGER','SCOPE_ROLE_ADMIN')")
    @PatchMapping("/role")
    public ResponseEntity<String> addRole(@RequestBody RoleRequest roleRequest) throws DataFormatException, IOException {
       AppUser appUser= userService.addRole(roleRequest.username(),roleRequest.rolename());
       if(appUser == null){
           return ResponseEntity.ok().body(roleRequest.rolename()+" already assigned to user");
       }
        return ResponseEntity.ok().body(roleRequest.rolename() +" added");
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        boolean appUser = userService.deleteUser(id);
        if(appUser){
            return ResponseEntity.ok().body("User deleted");
        }
        return ResponseEntity.badRequest().body("Cannot delete user,user has reference to role");
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<String> updateUserData(@RequestParam Long id, @RequestBody UserInfoUpdateRequest userInfoUpdateRequest){
        AppUser appUser= userService.updateUserInfo(id,userInfoUpdateRequest);
       if(appUser == null){
           return ResponseEntity.badRequest().body("Could not update user data");
       }
        return ResponseEntity.ok().body("User details updated");
    }

}
