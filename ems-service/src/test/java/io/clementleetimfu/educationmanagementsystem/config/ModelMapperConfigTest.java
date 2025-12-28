package io.clementleetimfu.educationmanagementsystem.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Model Mapper Config Tests")
class ModelMapperConfigTest {

    @Test
    @DisplayName("Test Model Mapper Bean Creation")
    void testModelMapperBeanCreation() {
        ModelMapperConfig config = new ModelMapperConfig();
        ModelMapper mapper = config.modelMapper();
        assertNotNull(mapper, "ModelMapper bean should not be null");
    }
}
