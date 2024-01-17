package ru.danilov.Smoke.House.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class CigarettesResponse {
private List<CigarettesDTO> cigarettesDTOList;
}
