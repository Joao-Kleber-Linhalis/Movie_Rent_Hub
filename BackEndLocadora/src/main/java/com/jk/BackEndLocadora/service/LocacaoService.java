package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Dependente;
import com.jk.BackEndLocadora.domain.Item;
import com.jk.BackEndLocadora.domain.Locacao;
import com.jk.BackEndLocadora.domain.dto.DependenteDTO;
import com.jk.BackEndLocadora.domain.dto.ItemDTO;
import com.jk.BackEndLocadora.domain.dto.LocacaoDTO;
import com.jk.BackEndLocadora.domain.enums.StatusItem;
import com.jk.BackEndLocadora.exceptions.LocacaoEmAtrasoExceededException;
import com.jk.BackEndLocadora.exceptions.ObjectNotFoundException;
import com.jk.BackEndLocadora.repository.LocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    DependenteService dependenteService;

    ModelMapper modelMapper = new ModelMapper();

    public LocacaoDTO findById(Long id){
        Optional<Locacao> locacao = locacaoRepository.findById(id);
        return converterOptionalLocacaoParaDTO(locacao).orElseThrow(() -> new ObjectNotFoundException("Locação " + id + " inexistente"));
    }

    public List<LocacaoDTO> findAll(){
        List<Locacao> locacaoList = locacaoRepository.findAll();
        return locacaoList.stream().map(locacao -> modelMapper.map(locacao,LocacaoDTO.class)).collect(Collectors.toList());
    }

    public List<LocacaoDTO> findAllOpen(){
        List<Locacao> locacaoList = locacaoRepository.findAllByDtDevolucaoEfetivaIsNull();
        return locacaoList.stream().map(locacao -> modelMapper.map(locacao,LocacaoDTO.class)).collect(Collectors.toList());
    }

    public List<LocacaoDTO> findAllClose(){
        List<Locacao> locacaoList = locacaoRepository.findAllByDtDevolucaoEfetivaIsNotNull();
        return locacaoList.stream().map(locacao -> modelMapper.map(locacao,LocacaoDTO.class)).collect(Collectors.toList());
    }

    public LocacaoDTO create(LocacaoDTO locacaoDTO){
        locacaoDTO.setId(null);
        clienteService.findById(locacaoDTO.getCliente().getId());
        if (locacaoDTO.getDependente() != null){
            dependenteService.findById(locacaoDTO.getDependente().getId());
        }
        verifyClienteWithoutLocacaoEmAtraso(locacaoDTO.getCliente().getId());
        ItemDTO item = verifyAndRentItem(locacaoDTO.getItem().getId());
        Locacao locacao = locacaoRepository.save(modelMapper.map(locacaoDTO, Locacao.class));
        itemService.update(item.getId(),item);
        return modelMapper.map(locacao,LocacaoDTO.class);
    }

    public LocacaoDTO update(Long id,LocacaoDTO locacaoDTO){
        locacaoDTO.setId(id);
        LocacaoDTO oldLocacao = findById(id);
        Locacao locacao = null;
        if (locacaoDTO.getCliente().getId() != oldLocacao.getCliente().getId()){
            verifyClienteWithoutLocacaoEmAtraso(locacaoDTO.getCliente().getId());
        }
        if (locacaoDTO.getItem().getId() != oldLocacao.getItem().getId()){
            ItemDTO itemDTO = verifyAndRentItem(locacaoDTO.getItem().getId());
            ItemDTO OldItemDTO = itemService.findById(oldLocacao.getItem().getId());
            itemDTO.setStatusItem(StatusItem.ALUGADO);
            OldItemDTO.setStatusItem(StatusItem.DISPONIVEL);
            itemService.update(OldItemDTO.getId(),OldItemDTO);
            locacao = locacaoRepository.save(modelMapper.map(locacaoDTO, Locacao.class));
            itemService.update(itemDTO.getId(),itemDTO);
        }
        else {
            locacao = locacaoRepository.save(modelMapper.map(locacaoDTO, Locacao.class));
        }
        return modelMapper.map(locacao,LocacaoDTO.class);
    }

    public void delete(Long id){
        LocacaoDTO locacao = findById(id);
        locacaoRepository.delete(modelMapper.map(locacao,Locacao.class));
        return;
    }

    private void verifyClienteWithoutLocacaoEmAtraso(Long clienteId) {
        List<Locacao> locacaoList = locacaoRepository.findLocacoesEmAbertoByClienteId(clienteId);
        if (!locacaoList.isEmpty()) {
            throw new LocacaoEmAtrasoExceededException(locacaoList.size() + " Locações estão atrasadas");
        }
    }

    private ItemDTO verifyAndRentItem(Long itemId) {
        ItemDTO item = itemService.findById(itemId);

        if (item.getStatusItem() == StatusItem.ALUGADO) {
            throw new DataIntegrityViolationException("Item está atualmente alugado");
        }

        // Atualiza o status do item para alugado
        item.setStatusItem(StatusItem.ALUGADO);
        return item;
    }

    private Optional<LocacaoDTO> converterOptionalLocacaoParaDTO(Optional<Locacao> optionalLocacao) {
        return optionalLocacao.map(locacao -> modelMapper.map(locacao, LocacaoDTO.class));
    }
}
