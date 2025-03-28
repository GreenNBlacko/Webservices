package lt.viko.eif.rgenzuras.sb_sample.db.mappers;

import lt.viko.eif.rgenzuras.sb_sample.model.Customer;
import lt.viko.eif.rgenzuras.sb_sample.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MapperService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ItemMapper itemMapper;

    public lt.viko.eif.rgenzuras.sb_sample.schema.Customer toDTOCustomer(Customer customer) { return customerMapper.toDTO(customer); }
    public Customer toModelCustomer(lt.viko.eif.rgenzuras.sb_sample.schema.Customer customer) { return customerMapper.toModel(customer); }

    public lt.viko.eif.rgenzuras.sb_sample.schema.Item toDTOItem(Item item) { return itemMapper.toDTO(item); }
    public Item toModelItem(lt.viko.eif.rgenzuras.sb_sample.schema.Item item) { return itemMapper.toModel(item); }
}
