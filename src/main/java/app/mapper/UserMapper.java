package app.mapper;

import app.entities.DTOs.User.ShortUser;
import app.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "user.roles", ignore = true)
    ShortUser UserToShortUser(UserEntity user);
}
