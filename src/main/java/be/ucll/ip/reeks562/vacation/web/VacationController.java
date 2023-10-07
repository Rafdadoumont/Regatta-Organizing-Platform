package be.ucll.ip.reeks562.vacation.web;

import be.ucll.ip.reeks562.vacation.domain.Vacation;
import be.ucll.ip.reeks562.vacation.domain.VacationService;
import be.ucll.ip.reeks562.generic.ServiceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class VacationController {
    @Autowired
    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
        createSampleData();
    }

    @GetMapping("/vacation/all")
    public String all(Model model) {
        List<Vacation> allVacations = vacationService.getVacations();

        model.addAttribute("vacations", allVacations);
        return "vacation/all";
    }

    @GetMapping("/vacation/add")
    public String add(Model model) {
        model.addAttribute("vacationDto", new VacationDto());
        return "vacation/add";
    }

    @PostMapping("/vacation/add")
    public String add(@Valid @ModelAttribute VacationDto vacationDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vacationDto", vacationDto);
            return "vacation/add";
        }
        try {
            vacationService.addVacation(vacationDto);
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "vacation/add";
        }
        return "redirect:/vacation/all";
    }

    @GetMapping("/vacation/update")
    public String update(@RequestParam("id") long id, Model model) {
        try {
            Vacation vacation = vacationService.getVacation(id);
            model.addAttribute("vacationDto", toDto(vacation));
        } catch (ServiceException e) {
            if (e.getMessage().equals("vacation.not.found")) {
                model.addAttribute("errorNotFound", e.getMessage());
            }
            model.addAttribute("error", e.getMessage());
        }
        return "vacation/update";
    }

    @PostMapping("/vacation/update")
    public String update(@RequestParam("id") long id, @Valid VacationDto vacationDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            vacationDto.setId(id);
            model.addAttribute("vacationDto", vacationDto);
            return "vacation/update";
        }
        try {
            vacationService.updateVacation(id, vacationDto);
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "vacation/update";
        }
        return "redirect:/vacation/all";
    }

    @GetMapping("/vacation/delete")
    public String delete(@RequestParam("id") long id, Model model) {
        try {
            Vacation vacation = vacationService.getVacation(id);
            model.addAttribute("vacationDto", toDto(vacation));
        } catch (ServiceException e) {
            if (e.getMessage().equals("vacation.not.found")) {
                model.addAttribute("errorNotFound", e.getMessage());
            }
            model.addAttribute("error", e.getMessage());
        }
        return "vacation/delete";
    }

    @PostMapping("/vacation/delete")
    public String delete(@RequestParam("id") long id, @ModelAttribute VacationDto vacationDto, Model model) {
        try {
            vacationService.deleteVacation(id);
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "vacation/delete";
        }
        return "redirect:/vacation/all";
    }

    @GetMapping("/vacation/filter")
    public String filter(@RequestParam(value = "van", required = false) String van,
            @RequestParam(value = "tot", required = false) String tot, Model model) {

        List<Vacation> vacations;
        if (van == null && tot == null) {
            return "vacation/filter";
        }

        if (van.isEmpty() && tot.isEmpty()) {
            model.addAttribute("error", "vacation.filter.empty");
        } else {
            if (van.isEmpty()) {
                vacations = vacationService.filterVacations(null, LocalDate.parse(tot));
            } else if (tot.isEmpty()) {
                vacations = vacationService.filterVacations(LocalDate.parse(van), null);
            } else {
                vacations = vacationService.filterVacations(LocalDate.parse(van), LocalDate.parse(tot));
            }
            model.addAttribute("vacations", vacations);
            model.addAttribute("van", van);
            model.addAttribute("tot", tot);
        }
        return "vacation/filter";
    }

    private VacationDto toDto(Vacation vacation) {
        VacationDto dto = new VacationDto();

        dto.setId(vacation.getId());
        dto.setNaam(vacation.getNaam());
        dto.setPlaats(vacation.getPlaats());
        dto.setBegindatum(vacation.getBegindatum());
        dto.setOvernachtingen(vacation.getOvernachtingen());

        return dto;
    }

    private void createSampleData() {
        VacationDto vacationPorto = new VacationDto();
        vacationPorto.setNaam("Vakantie Porto Juni 2021");
        vacationPorto.setPlaats("Porto");
        vacationPorto.setBegindatum(LocalDate.of(2021, 6, 14));
        vacationPorto.setOvernachtingen(10);
        vacationService.addVacation(vacationPorto);

        VacationDto vacationCuba = new VacationDto();
        vacationCuba.setNaam("Vakantie Cuba Mei 2020");
        vacationCuba.setPlaats("Cuba");
        vacationCuba.setBegindatum(LocalDate.of(2020, 5, 3));
        vacationCuba.setOvernachtingen(14);
        vacationService.addVacation(vacationCuba);

        VacationDto vacationCopenhagen = new VacationDto();
        vacationCopenhagen.setNaam("Vakantie Kopenhagen Januari 2019");
        vacationCopenhagen.setPlaats("Kopenhagen");
        vacationCopenhagen.setBegindatum(LocalDate.of(2019, 1, 23));
        vacationCopenhagen.setOvernachtingen(7);
        vacationService.addVacation(vacationCopenhagen);
    }

}
