package be.ucll.ip.reeks562.storage.web;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.storage.domain.Storage;
import be.ucll.ip.reeks562.storage.domain.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@Controller
public class StorageController {
    @Autowired
    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
        createSampleData();
    }

    @GetMapping("/storage/add")
    public String add(Model model) {
        model.addAttribute("storageDto", new StorageDto());
        return "storage/add";
    }

    @PostMapping("/storage/add")
    public String add(@Valid StorageDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("storageDto", dto);
            return "storage/add";
        }

        try {
            storageService.addStorage(dto);
            model.addAttribute("storageDto", new StorageDto());
        } catch (ServiceException exc) {
            model.addAttribute("error", exc.getMessage());
            model.addAttribute("storageDto", dto);
            return "storage/add";
        }

        return "redirect:/storage/overview";
    }

    @GetMapping("/storage/update/{id}")
    public String update(@PathVariable long id, Model model) {
        try {
            Storage storage = storageService.getStorage(id);
            model.addAttribute("storageDto", toDto(storage));
        } catch (RuntimeException exc) {
            model.addAttribute("error", exc.getMessage());
        }
        return "storage/update";
    }

    @PostMapping("/storage/update/{id}")
    public String update(@PathVariable long id, @Valid StorageDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            dto.setId(id);
            model.addAttribute("storageDto", dto);
            return "storage/update";
        }

        storageService.updateStorage(id, dto);
        return "redirect:/storage/overview";
    }

    @GetMapping("/storage/delete")
    public String delete(@RequestParam("id") long id, Model model) {
        try {
            Storage storage = storageService.getStorage(id);
            model.addAttribute("storageDto", toDto(storage));
        } catch (RuntimeException exc) {
            model.addAttribute("error", exc.getMessage());
        }
        return "storage/delete";
    }

    @PostMapping("/storage/delete")
    public String delete(@RequestParam long id, @Valid StorageDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            dto.setId(id);
            model.addAttribute("storageDto", dto);
            model.addAttribute("error", result.getAllErrors());
            return "storage/delete";
        }
        storageService.deleteStorage(id);
        return "redirect:/storage/overview";
    }

    @GetMapping("/storage/overview")
    public String overview(Model model, @RequestParam(required = false) Integer page) {
        Page<Storage> storages = storageService.getAll(null, getPage(page));

        model.addAttribute("storages", storages);
        return "storage/overview";
    }

    @GetMapping("/storage/sort/{order}")
    public String sort(Model model, @PathVariable String order, @RequestParam(required = false) Integer page) {
        Page<Storage> storages = storageService.getAll(order, getPage(page));
        model.addAttribute("storages", storages);
        return "storage/overview";
    }

    @GetMapping("/storage/search")
    public String search(Model model, @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) Integer page) {
        Page<Storage> storages;

        if (searchValue == null) {
            storages = storageService.getAll(null, getPage(page));
            model.addAttribute("searchValue", "");
        } else {
            storages = storageService.searchStorage(searchValue, getPage(page));
            model.addAttribute("searchValue", searchValue);
        }
        model.addAttribute("storages", storages);
        return "storage/search";
    }

    private StorageDto toDto(Storage storage) {
        StorageDto dto = new StorageDto();

        dto.setId(storage.getId());
        dto.setName(storage.getName());
        dto.setPostalCode(storage.getPostalCode());
        dto.setSpace(storage.getSpace());
        dto.setHeight(storage.getHeight());

        return dto;
    }

    private int getPage(Integer page) {
        return page == null ? 0 : Math.max(0, page);
    }

    private void createSampleData() {

        StorageDto HarborHaven = new StorageDto();
        HarborHaven.setName("Harbor Haven");
        HarborHaven.setPostalCode(3000);
        HarborHaven.setSpace(50.0);
        HarborHaven.setHeight(4.5);

        StorageDto MarinaShores = new StorageDto();
        MarinaShores.setName("Marina Shores");
        MarinaShores.setPostalCode(4500);
        MarinaShores.setSpace(70.0);
        MarinaShores.setHeight(5.2);

        StorageDto SunsetBayStorage = new StorageDto();
        SunsetBayStorage.setName("Sunset Bay Storage");
        SunsetBayStorage.setPostalCode(1000);
        SunsetBayStorage.setSpace(10.0);
        SunsetBayStorage.setHeight(1.8);

        StorageDto CoastalRetreat = new StorageDto();
        CoastalRetreat.setName("Coastal Retreat");
        CoastalRetreat.setPostalCode(8590);
        CoastalRetreat.setSpace(60.0);
        CoastalRetreat.setHeight(4.0);

        StorageDto OceanBreezeMarina = new StorageDto();
        OceanBreezeMarina.setName("Ocean Breeze Marina");
        OceanBreezeMarina.setPostalCode(8300);
        OceanBreezeMarina.setSpace(55.0);
        OceanBreezeMarina.setHeight(4.7);

        StorageDto SeasideBoatVault = new StorageDto();
        SeasideBoatVault.setName("Seaside Boat Vault");
        SeasideBoatVault.setPostalCode(9100);
        SeasideBoatVault.setSpace(75.0);
        SeasideBoatVault.setHeight(5.5);

        StorageDto BayfrontHarbor = new StorageDto();
        BayfrontHarbor.setName("Bayfront Harbor");
        BayfrontHarbor.setPostalCode(1000);
        BayfrontHarbor.setSpace(65.0);
        BayfrontHarbor.setHeight(4.8);

        StorageDto LighthouseMarina = new StorageDto();
        LighthouseMarina.setName("Lighthouse Marina");
        LighthouseMarina.setPostalCode(3000);
        LighthouseMarina.setSpace(45.0);
        LighthouseMarina.setHeight(3.6);

        StorageDto SailorsCoveStorage = new StorageDto();
        SailorsCoveStorage.setName("Sailors Cove Storage");
        SailorsCoveStorage.setPostalCode(2400);
        SailorsCoveStorage.setSpace(85.0);
        SailorsCoveStorage.setHeight(6.0);

        StorageDto AnchorageHeights = new StorageDto();
        AnchorageHeights.setName("Anchorage Heights");
        AnchorageHeights.setPostalCode(3100);
        AnchorageHeights.setSpace(50.0);
        AnchorageHeights.setHeight(4.2);

        storageService.addStorage(HarborHaven);
        storageService.addStorage(MarinaShores);
        storageService.addStorage(SunsetBayStorage);
        storageService.addStorage(CoastalRetreat);
        storageService.addStorage(OceanBreezeMarina);
        storageService.addStorage(SeasideBoatVault);
        storageService.addStorage(BayfrontHarbor);
        storageService.addStorage(LighthouseMarina);
        storageService.addStorage(SailorsCoveStorage);
        storageService.addStorage(AnchorageHeights);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class, ServiceException.class, ResponseStatusException.class })
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof ServiceException) {
            errors.put(((ServiceException) ex).getAction(), ex.getMessage());
        } else {
            errors.put(((ResponseStatusException) ex).getReason(), ex.getCause().getMessage());
        }
        return errors;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true) {
            @Override
            public void setAsText(String text) {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null);
                } else {
                    super.setAsText(text);
                }
            }
        });

        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true) {
            @Override
            public void setAsText(String text) {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null);
                } else {
                    super.setAsText(text);
                }
            }
        });
    }
}
