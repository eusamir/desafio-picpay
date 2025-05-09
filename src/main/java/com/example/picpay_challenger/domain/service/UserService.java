package com.example.picpay_challenger.domain.service;

import com.example.picpay_challenger.domain.enums.Message;
import com.example.picpay_challenger.domain.enums.UserType;
import com.example.picpay_challenger.domain.model.dto.UserDto;
import com.example.picpay_challenger.domain.model.entity.User;
import com.example.picpay_challenger.domain.model.entity.Wallet;
import com.example.picpay_challenger.domain.repository.UserRepository;
import com.example.picpay_challenger.domain.repository.WalletRepository;
import com.example.picpay_challenger.mapper.UserConverter;
import com.example.picpay_challenger.suport.expection.AuthenticationException;
import com.example.picpay_challenger.suport.expection.ForbiddenException;
import com.example.picpay_challenger.suport.expection.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public UserDto createUser(UserDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new AuthenticationException(Message.EMAIL_JA_EM_USO.getMessage());
        }
        if (userRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new AuthenticationException(Message.CPF_JA_EM_USO.getMessage());
        }
        User user = UserConverter.toEntity(dto);
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(0.0));

        user.setWallet(wallet);
        wallet.setUser(user);

        User savedUser = userRepository.save(user);

        return UserConverter.toDto(userRepository.save(savedUser));
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
                .orElseThrow(()-> new NotFoundException(Message.USUARIO_NAO_ENCONTRADO.getMessage()));
    }

    public List<UserDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(UserConverter::toDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(Message.USUARIO_NAO_ENCONTRADO.getMessage()));
        user.setType(userDto.getType());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.getWallet().setBalance(userDto.getBalance());

        return UserConverter.toDto(userRepository.save(user));
    }

    public void validateUserCanTransfer(User user){
        if(user.getType().equals(UserType.MERCHANT)){
            throw new ForbiddenException(Message.LOGISTAS_NAO_PODEM_TRANSFERIR.getMessage());
        }
    }

    public UserDto findByEmail(String email){
        return userRepository.findByEmail(email)
                .map(UserConverter::toDto)
                .orElseThrow(()-> new NotFoundException(Message.USUARIO_NAO_ENCONTRADO.getMessage()));
    }

    public void deleteUser(Long id){
        if(userRepository.findById(id).isEmpty()){
            throw new NotFoundException(Message.USUARIO_NAO_ENCONTRADO.getMessage());
        }
        userRepository.deleteById(id);
    }

}
