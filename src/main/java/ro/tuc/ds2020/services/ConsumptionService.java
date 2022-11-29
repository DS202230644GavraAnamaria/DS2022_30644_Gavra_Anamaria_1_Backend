package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.builders.ConsumptionBuilder;
import ro.tuc.ds2020.dtos.detailsDTO.ConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.dto.ConsumptionDTO;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsumptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumptionService.class);
    private final DeviceRepository deviceRepository;
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public ConsumptionService(ConsumptionRepository ConsumptionRepository, DeviceRepository deviceRepository) {
        this.consumptionRepository = ConsumptionRepository;
        this.deviceRepository = deviceRepository;
    }


    public ConsumptionDetailsDTO findConsumptionById(UUID id) {
        Optional<Consumption> prosumerOptional = consumptionRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Consumption with id {} was not found in db", id);
            throw new ResourceNotFoundException(Consumption.class.getSimpleName() + " with id: " + id);
        }
        return ConsumptionBuilder.toConsumptionDetailsDTO(prosumerOptional.get());
    }

    public List<ConsumptionDTO> getConsByDay(String deviceDescription, LocalDate date) {
        Device device=deviceRepository.findByDescription(deviceDescription).orElse(null);
        List<Consumption> cons = consumptionRepository.findByDeviceAndTimeBetween(device, LocalDateTime.of(date, LocalTime.of(0, 0, 0)), LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
        return  cons.stream()
                .map(ConsumptionBuilder::toConsumptionDTO)
                .collect(Collectors.toList());
    }

    public void insertCons() {
        Device device =deviceRepository.findByDescription("device2").orElse(null);
        LocalDateTime d = LocalDateTime.of(LocalDate.of(2022, 11, 19), LocalTime.of(0, 0, 0));
        for(int j=0;j<24;j++)
            consumptionRepository.save(new Consumption(d.plusHours(23), (float)Math.random() * 30,device));
    }
}
