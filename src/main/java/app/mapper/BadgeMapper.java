package app.mapper;

import app.entities.Badge;
import app.entities.DTOs.Badge.BadgeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BadgeMapper {
    Badge BadgeDTOToBadge(BadgeDTO badgeDTO);
    BadgeDTO BadgeToBadgeDTO(Badge badge);
    List<BadgeDTO> BadgesToBadgeDTOs(List<Badge> badges);

}
