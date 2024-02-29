package com.example.onlineauction.config;

import com.example.onlineauction.dto.lot.LotInfoDto;
import com.example.onlineauction.entity.LotEntity;
import com.example.onlineauction.entity.TrackingEntity;
import com.example.onlineauction.props.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final MinioProperties minioProperties;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<LotEntity, LotInfoDto> propertyMapper = modelMapper.createTypeMap(LotEntity.class, LotInfoDto.class);
        propertyMapper.addMappings(mapper -> mapper
                .using(ctx -> ctx.getSource() != null && ((List<?>) ctx.getSource()).size() > 0)
                .map(LotEntity::getTracking, LotInfoDto::setTracking)
        );

        return modelMapper;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
