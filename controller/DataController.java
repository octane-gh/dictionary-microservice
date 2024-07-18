package com.example.dictionary.controller;

import com.example.dictionary.dto.CreateDataDto;
import com.example.dictionary.dto.DataDto;
import com.example.dictionary.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class DataController {

    @Autowired
    private DataService dataService;


    @PostMapping("/records")
    @Operation(summary = "Create a new record in a dictionary",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Record created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Dictionary not found",
                            content = @Content(schema = @Schema())
                    )
            })
    public DataDto createDataRecord(@RequestBody CreateDataDto dataDto) {
        try {
            return dataService.createData(dataDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dictionary record not found with id: %s", dataDto.getDictionary()));
        }
    }

    @GetMapping("/records")
    @Operation(summary = "Get all data records",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of data records",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema( schema = @Schema(implementation = DataDto.class))
                            )
                    )
            })
    public List<DataDto> getAllDataRecords() {
        return dataService.getAllData();
    }

    @GetMapping("/dictionaries/{id}/records")
    @Operation(summary = "Get all data records by dictionary id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of data records",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema( schema = @Schema(implementation = DataDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Dictionary not found",
                            content = @Content(schema = @Schema())
                    )
            })
    public List<DataDto> getAllDataRecordsByDictionaryId(@PathVariable UUID id) {
        try {
            return dataService.getAllDataByDictionaryId(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dictionary record not found with id: %s", id));
        }
    }

    @GetMapping("/records/{id}")
    @Operation(summary = "Get data record by ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Data record found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DataDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Data not found",
                            content = @Content(schema = @Schema())
                    )
            })
    public DataDto getDataRecordById(@PathVariable UUID id) {
        try {
            return dataService.getDataById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Data record not found with id: %s", id));
        }
    }

    @DeleteMapping("/records/{id}")
    @Operation(summary = "Delete data record by ID",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Data record deleted successfully"),
                    @ApiResponse(responseCode = "404",
                            description = "Data record not found")
            })
    public void deleteDataRecord(@PathVariable UUID id) {
        try {
            dataService.deleteData(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Data record not found with id: %s", id));
        }
    }
}