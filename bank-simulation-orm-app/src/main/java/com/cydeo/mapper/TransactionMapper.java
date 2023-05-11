package com.cydeo.mapper;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Account;
import com.cydeo.entity.Transaction;
import org.modelmapper.ModelMapper;

public class TransactionMapper {
    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionDTO convertToDTO(Account entity) {
        return modelMapper.map(entity,TransactionDTO.class);
    }
    public Transaction convertToEntity(AccountDTO dto) {
        return modelMapper.map(dto,Transaction.class);
    }
}
