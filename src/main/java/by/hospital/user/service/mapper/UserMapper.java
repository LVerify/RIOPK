package by.hospital.user.service.mapper;

import by.hospital.sequrity.dto.SignUpRequest;
import by.hospital.user.dto.UserReadDTO;
import by.hospital.user.dto.UserUpdateDTO;
import by.hospital.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

  User dtoToEntity(SignUpRequest dto);

  UserReadDTO entityToDto(User user);

  void updateEntity(UserUpdateDTO dto, @MappingTarget User user);
}
