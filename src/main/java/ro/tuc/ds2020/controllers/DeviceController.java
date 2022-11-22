package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.detailsDTO.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.dto.DeviceDTO;
import ro.tuc.ds2020.dtos.dto.MappingDTO;
import ro.tuc.ds2020.services.DeviceService;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService personService) {
        this.deviceService = personService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        for (DeviceDTO dto : dtos) {
            Link personLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("personDetails");
            dto.add(personLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(path = "/mapping")
    public ResponseEntity<List<MappingDTO>> getMapping() {
        List<MappingDTO> dtos = deviceService.findMappingDU();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{user}")
    public ResponseEntity<List<DeviceDTO>> getUserDevices(@PathVariable("user")String user) {
        System.out.println("\n\n\n\n\n"+user+"\n\n\n\n");
        List<DeviceDTO> device = deviceService.getUserDevices(user);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID deviceID = deviceService.insert(deviceDetailsDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }
    @PutMapping(path="/mapping")
    public ResponseEntity<String> updateUser(@RequestBody MappingDTO mappingDTO) {
        deviceService.insertMapping(mappingDTO);
        return new ResponseEntity<>(mappingDTO.getName(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UUID> updateDevice(@RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        UUID deviceID = deviceService.update(deviceDetailsDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable("id") UUID personId) {
        DeviceDetailsDTO dto = deviceService.findDeviceById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}