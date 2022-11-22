package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.builders.ConsumptionBuilder;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.detailsDTO.ConsumptionDetailsDTO;
import ro.tuc.ds2020.dtos.detailsDTO.DeviceDetailsDTO;
import ro.tuc.ds2020.dtos.dto.ConsumptionDTO;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;
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

    public List<ConsumptionDTO> findConsumptions() {
        List<Consumption> ConsumptionList = consumptionRepository.findAll();
        return ConsumptionList.stream()
                .map(ConsumptionBuilder::toConsumptionDTO)
                .collect(Collectors.toList());
    }

    public ConsumptionDetailsDTO findConsumptionById(UUID id) {
        Optional<Consumption> prosumerOptional = consumptionRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Consumption with id {} was not found in db", id);
            throw new ResourceNotFoundException(Consumption.class.getSimpleName() + " with id: " + id);
        }
        return ConsumptionBuilder.toConsumptionDetailsDTO(prosumerOptional.get());
    }

    public DeviceDetailsDTO findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Consumption with id {} was not found in db", id);
            throw new ResourceNotFoundException(Consumption.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(prosumerOptional.get());
    }

    public UUID insert(ConsumptionDetailsDTO ConsumptionDTO) {
        Device u = deviceRepository.findById(ConsumptionDTO.getDevice_id()).get();
        Consumption Consumption = ConsumptionBuilder.toEntity(ConsumptionDTO, u);
        Consumption = consumptionRepository.save(Consumption);
        LOGGER.debug("Consumption with id {} was inserted in db", Consumption.getId());
        return Consumption.getId();
    }
}
