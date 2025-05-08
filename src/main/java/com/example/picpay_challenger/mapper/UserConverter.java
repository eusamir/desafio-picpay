package com.example.picpay_challenger.mapper;

import com.example.picpay_challenger.domain.model.dto.UserDto;
import com.example.picpay_challenger.domain.model.entity.User;
import com.example.picpay_challenger.domain.model.entity.Wallet;

public class UserConverter {
    public static UserDto toDto(User user){
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getType(),
                user.getWallet().getBalance()
        );
    }
    public static User toEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setType(userDto.getType());
        user.setEmail(userDto.getEmail());

        Wallet wallet = new Wallet(userDto.getBalance(), user);
        user.setWallet(wallet);
        return user;
    }
}
