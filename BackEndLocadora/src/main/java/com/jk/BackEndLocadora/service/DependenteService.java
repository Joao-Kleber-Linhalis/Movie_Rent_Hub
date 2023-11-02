package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.repository.DependenteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DependenteService {

    private DependenteRepository dependenteRepository;

    ModelMapper modelMapper = new ModelMapper();

}
