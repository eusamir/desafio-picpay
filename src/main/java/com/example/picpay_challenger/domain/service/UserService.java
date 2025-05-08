package com.example.picpay_challenger.domain.service;

import com.example.picpay_challenger.domain.enums.UserType;
import com.example.picpay_challenger.domain.model.dto.UserDto;
import com.example.picpay_challenger.domain.model.entity.User;
import com.example.picpay_challenger.domain.model.entity.Wallet;
import com.example.picpay_challenger.domain.repository.UserRepository;
import com.example.picpay_challenger.mapper.UserConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserDto createUser(UserDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("E-mail já em uso.");
        }
        User user = UserConverter.toEntity(dto);
        user.setWallet(new Wallet(BigDecimal.valueOf(0.0), user));

        return UserConverter.toDto(userRepository.save(user));
    }

    public List<UserDto> findAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserConverter::toDto)
                .toList();
    }

    public UserDto findById(Long id){
        return userRepository.findById(id)
                .map(UserConverter::toDto)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado."));
    }

    public UserDto updateUser(Long id, UserDto userDto){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado."));
        user.setType(userDto.getType());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.getWallet().setBalance(userDto.getBalance());

        return UserConverter.toDto(userRepository.save(user));
    }

    public void validateUserCanTransfer(User user){
        if(user.getType().equals(UserType.MERCHANT)){
            throw new RuntimeException("Logistas não podem realizar transferências.");
        }
    }

    public UserDto findByEmail(String email){
        return userRepository.findByEmail(email)
                .map(UserConverter::toDto)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado."));
    }

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException("Usuário não encontrado.");
        }
        userRepository.deleteById(id);
    }

}
