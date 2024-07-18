package com.example.dictionary.service;

import com.example.dictionary.dto.CreateDictionaryDto;
import com.example.dictionary.dto.DictionaryDto;
import com.example.dictionary.model.Dictionary;
import com.example.dictionary.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public DictionaryDto createDictionary(CreateDictionaryDto dictionaryDto) {
        Dictionary dictionary = Dictionary
                .builder()
                .code(dictionaryDto.getCode())
                .description(dictionaryDto.getDescription())
                .build();
        Dictionary createdDictionary = dictionaryRepository.save(dictionary);
        return new DictionaryDto(createdDictionary.getId(), createdDictionary.getCode(), createdDictionary.getDescription());
    }

    public List<DictionaryDto> getAllDictionaries() {
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        List<DictionaryDto> dictionariesDtos = new ArrayList<>();

        for (Dictionary dictionary : dictionaries) {
            dictionariesDtos.add(
                    new DictionaryDto(
                            dictionary.getId(),
                            dictionary.getCode(),
                            dictionary.getDescription()
                    )
            );
        }

        return dictionariesDtos;
    }

    public DictionaryDto getDictionaryById(UUID id) {
        Dictionary dictionary =  dictionaryRepository.findById(id).orElseThrow();

        return new DictionaryDto(dictionary.getId(), dictionary.getCode(), dictionary.getDescription());
    }

    public void deleteDictionary(UUID id) {
        Dictionary dictionary = dictionaryRepository.findById(id).orElseThrow();
        dictionaryRepository.deleteById(dictionary.getId());
    }
}