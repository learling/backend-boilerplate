package de.boilerplate.springbootbackend.controller;

import de.boilerplate.springbootbackend.data.item.ItemDynamicSearch;
import de.boilerplate.springbootbackend.data.item.ItemEntity;
import de.boilerplate.springbootbackend.data.item.ItemRepository;
import de.boilerplate.springbootbackend.exception.EntityNotFoundException;
import de.boilerplate.springbootbackend.exception.PropertyNotUpdatableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "https://main.d26991zspaafgq.amplifyapp.com/", "https://reactstrap-ts.netlify.app"})
@RestController
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public Page<ItemEntity> getItems(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 50) Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @GetMapping("/items/{id}")
    public ItemEntity getItem(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @PostMapping("/items/filter")
    public Page<ItemEntity> filterItems(@RequestBody Map<String, String[]> filters,
                                  @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 50) Pageable pageable) {
//{
//    "id": [
//      "2 - 4",
//      "26"
//    ],
//      "title": [
//      "lorem",
//      "ipsum diam"
//    ],
//    "description": [
//      "bum co",
//      "dolores"
//    ],
//    "price": [
//      "55.55 - 77.77",
//      "42",
//      "2,50"
//    ],
//    "created": [
//      "2019-03-18 12-14-48 AM - 2019-03-18 12-14-48 PM",
//      "2019-03-18 12-14-48 AM"
//    ],
//    "updated": [
//      "2019-03-18 12-14-48 AM - 2019-03-18 12-14-48 PM",
//      "2019-03-18 12-14-48 AM"
//    ]
//}
        return itemRepository.findAll(new ItemDynamicSearch(filters), pageable);
    }

    @PatchMapping("/items/{id}")
    public ItemEntity updateItem(@PathVariable Long id, @RequestBody Map<String, String> partialUpdate) {
        ItemEntity itemEntity = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        partialUpdate.forEach((key, value) -> {
            updateItemValues(itemEntity, key, value);
        });
        itemEntity.setCreated(itemEntity.getCreated());
        itemEntity.setUpdated(LocalDateTime.now());
        return itemRepository.save(itemEntity);
    }

    private void updateItemValues(ItemEntity itemEntity, String key, String value) {
        switch (key) {
            case "title":
                itemEntity.setTitle(value);
                break;
            case "description":
                itemEntity.setDescription(value);
                break;
            case "price":
                itemEntity.setPrice(Double.valueOf(value));
                break;
            default:
                throw new PropertyNotUpdatableException(key);
        }
    }

    @PostMapping("/items")
    public ItemEntity createItem(@RequestBody ItemEntity newItem) {
        return itemRepository.save(newItem);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Long> deleteItem(@PathVariable Long id) {
        try {
            ItemEntity item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
            itemRepository.delete(item);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
