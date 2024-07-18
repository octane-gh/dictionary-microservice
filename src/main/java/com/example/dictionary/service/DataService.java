package com.example.dictionary.service;

import com.example.dictionary.dto.CreateDataDto;
import com.example.dictionary.dto.DataDto;
import com.example.dictionary.model.Data;
import com.example.dictionary.model.Dictionary;
import com.example.dictionary.repository.DataRepository;
import com.example.dictionary.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public DataDto createData(CreateDataDto dataDto) {
        Dictionary dictionary = dictionaryRepository.findById(dataDto.getDictionary()).orElseThrow();

        Data data = Data.builder().code(dataDto.getCode()).value(dataDto.getValue()).dictionary(dictionary).build();
        Data createdData = dataRepository.save(data);

        return new DataDto(
                createdData.getId(),
                createdData.getDictionary().getId(),
                createdData.getCode(),
                createdData.getValue()
        );
    }

    public List<DataDto> getAllData() {
        List<Data> dataRecords = dataRepository.findAll();
        List<DataDto> dataDtos = new ArrayList<>();

        for (Data data : dataRecords) {
            dataDtos.add(
                    new DataDto(
                            data.getId(),
                            data.getDictionary().getId(),
                            data.getCode(),
                            data.getValue()
                    )
            );
        }

        return dataDtos;
    }

    public List<DataDto> getAllDataByDictionaryId(UUID dictionaryId) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId).orElseThrow();
        List<Data> dataRecords = dataRepository.findByDictionaryId(dictionary.getId());
        List<DataDto> dataDtos = new ArrayList<>();

        for (Data data : dataRecords) {
            dataDtos.add(
                    new DataDto(
                            data.getId(),
                            data.getDictionary().getId(),
                            data.getCode(),
                            data.getValue()
                    )
            );
        }

        return dataDtos;
    }

    public DataDto getDataById(UUID id) {
        Data data = dataRepository.findById(id).orElseThrow();
        return new DataDto(data.getId(), data.getDictionary().getId(), data.getCode(), data.getValue());
    }

    public void deleteData(UUID id) {
        Data data = dataRepository.findById(id).orElseThrow();
        dataRepository.deleteById(data.getId());
    }
}