package de.luisgerlinger.chatserver.service.mapper;

public interface AbstractEntityMapper<DTO, BE> {
    public BE toBE(DTO dto);

    public DTO toDTO(BE entity);
}
