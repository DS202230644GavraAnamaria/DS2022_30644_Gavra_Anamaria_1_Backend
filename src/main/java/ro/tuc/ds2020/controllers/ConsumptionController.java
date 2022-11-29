package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.detailsDTO.ConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.dto.ConsumptionDTO;
import ro.tuc.ds2020.dtos.dto.DeviceDTO;
import ro.tuc.ds2020.services.ConsumptionService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

    @PostMapping()
    public ResponseEntity<List<ConsumptionDTO>> getConsByDay(@RequestBody String day) {
        System.out.println(day+"1234\n\n\n\n");
        System.out.println(day.substring(1,5)+"\n"+day.substring(5,7)+"\n"+day.substring(7,9)+"\n"+day.substring(9,day.length()-1)+"\n");
        LocalDate d = LocalDate.of(Integer.parseInt(day.substring(1,5)), Integer.parseInt(day.substring(5,7)), Integer.parseInt(day.substring(7,9)));
        List<ConsumptionDTO> cons =consumptionService.getConsByDay(day.substring(9,day.length()-1), d);
        return new ResponseEntity<>(cons, HttpStatus.OK);
    }
    @PutMapping()
    public void insertCons() {
        consumptionService.insertCons();
    }



}
