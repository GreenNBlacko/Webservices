package lt.viko.eif.rgenzuras.sb_sample.db.mappers;

import lt.viko.eif.rgenzuras.sb_sample.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "ID", target = "ID")
    @Mapping(source = "itemName", target = "name")
    @Mapping(source = "itemPrice", target = "price")
    lt.viko.eif.rgenzuras.sb_sample.schema.Item toDTO(Item item);
    @Mapping(source = "ID", target = "ID")
    @Mapping(source = "name", target = "itemName")
    @Mapping(source = "price", target = "itemPrice")
    Item toModel(lt.viko.eif.rgenzuras.sb_sample.schema.Item item);
}
