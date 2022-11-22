package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.detailsDTO.ConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.dto.ConsumptionDTO;
import ro.tuc.ds2020.services.ConsumptionService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/consumption")
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @Autowired
    public ConsumptionController(ConsumptionService cService) {
        this.consumptionService = cService;
    }

    @GetMapping()
    public ResponseEntity<List<ConsumptionDTO>> getConsumptions() {
        List<ConsumptionDTO> dtos = consumptionService.findConsumptions();
        for (ConsumptionDTO dto : dtos) {
            Link personLink = linkTo(methodOn(ConsumptionController.class)
                    .getConsumption(dto.getId())).withRel("personDetails");
            dto.add(personLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody ConsumptionDetailsDTO personDTO) {
        UUID personID = consumptionService.insert(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsumptionDetailsDTO> getConsumption(@PathVariable("id") UUID personId) {
        ConsumptionDetailsDTO dto = consumptionService.findConsumptionById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource

}
