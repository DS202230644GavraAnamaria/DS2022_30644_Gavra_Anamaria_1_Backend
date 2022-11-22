package ro.tuc.ds2020.services;

import ro.tuc.ds2020.enums.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ro.tuc.ds2020.Ds2020TestConfig;
import ro.tuc.ds2020.dtos.dto.UserDTO;
import ro.tuc.ds2020.dtos.detailsDTO.UserDetailsDTO;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.util.List;
import java.util.UUID;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/test-sql/create.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/test-sql/delete.sql")
public class PersonServiceIntegrationTests extends Ds2020TestConfig {

    @Autowired
    UserService personService;

    @Test
    public void testGetCorrect() {
        List<UserDTO> personDTOList = personService.findUsers();
        assertEquals("Test Insert Person", 1, personDTOList.size());
    }

    @Test
    public void testInsertCorrectWithGetById() {
        UserDetailsDTO p = new UserDetailsDTO("John", "abc", Role.CLIENT);
        UUID insertedID = personService.insert(p);

        UserDetailsDTO insertedPerson = new UserDetailsDTO(insertedID, p.getName(),p.getPassword(), p.getRole());
        UserDetailsDTO fetchedPerson = personService.findUserById(insertedID);

        assertEquals("Test Inserted Person", insertedPerson, fetchedPerson);
    }

    @Test
    public void testInsertCorrectWithGetAll() {
        UserDetailsDTO p = new UserDetailsDTO("John", "def", Role.CLIENT);
        personService.insert(p);

        List<UserDTO> personDTOList = personService.findUsers();
        assertEquals("Test Inserted Persons", 2, personDTOList.size());
    }
}
