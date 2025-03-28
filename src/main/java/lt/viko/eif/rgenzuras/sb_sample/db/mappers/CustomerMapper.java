package lt.viko.eif.rgenzuras.sb_sample.db.mappers;

import lt.viko.eif.rgenzuras.sb_sample.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(source = "ID", target = "ID")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "email", target = "email")
    lt.viko.eif.rgenzuras.sb_sample.schema.Customer toDTO(Customer customer);
    @Mapping(source = "ID", target = "ID")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "email", target = "email")
    Customer toModel(lt.viko.eif.rgenzuras.sb_sample.schema.Customer customer);
}
