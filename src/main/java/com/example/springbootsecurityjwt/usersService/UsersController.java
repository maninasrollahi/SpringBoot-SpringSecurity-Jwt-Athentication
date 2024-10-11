package com.example.springbootsecurityjwt.usersService;

import com.example.springbootsecurityjwt.security.baseInfo.entity.Users;
import com.example.springbootsecurityjwt.security.baseInfo.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UsersController {

    private final UsersRepository usersRepository;

    @RequestMapping("loadAll")
    private ResponseEntity<List<Users>> loadAllUsers() {
        List<Users> list = usersRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
