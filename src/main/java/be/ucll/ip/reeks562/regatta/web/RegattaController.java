package be.ucll.ip.reeks562.regatta.web;

import be.ucll.ip.reeks562.generic.ServiceException;
import be.ucll.ip.reeks562.regatta.domain.Regatta;
import be.ucll.ip.reeks562.regatta.domain.RegattaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class RegattaController {
    @Autowired
    private final RegattaService regattaService;

    public RegattaController(RegattaService regattaService) {
        this.regattaService = regattaService;
        createSampleData();
    }

    @GetMapping("/regatta/overview")
    public String overview(@RequestParam(required = false) Integer page, Model model) {
        Page<Regatta> allRegattas = regattaService.getRegattas(getPage(page));

        model.addAttribute("regattas", allRegattas);
        return "regatta/overview";
    }

    @GetMapping("/regatta/overview/{sort}")
    public String overviewSorted(@PathVariable("sort") String sort, @RequestParam(required = false) Integer page,
            Model model) {
        Page<Regatta> allRegattas = null;
        if (sort != null) {
            if (sort.equals("club")) {
                allRegattas = regattaService.getRegattasSortedClub(getPage(page));
            } else if (sort.equals("date")) {
                allRegattas = regattaService.getRegattasSortedDate(getPage(page));
            } else {
                allRegattas = regattaService.getRegattas(getPage(page));
            }
        } else {
            allRegattas = regattaService.getRegattas(getPage(page));
        }

        model.addAttribute("regattas", allRegattas);
        return "regatta/overview";
    }

    @GetMapping("/regatta/add")
    public String add(Model model) {
        model.addAttribute("regattaDto", new RegattaDto());
        return "regatta/add";
    }

    @PostMapping("/regatta/add")
    public String add(@Valid RegattaDto regatta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("regattaDto", regatta);
            return "regatta/add";
        }
        try {
            regattaService.addRegatta(regatta);
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "regatta/add";
        }
        return "redirect:/regatta/overview";
    }

    @GetMapping("/regatta/update")
    public String update(@RequestParam("id") long id, Model model) {
        try {
            Regatta regatta = regattaService.getRegatta(id);
            model.addAttribute("regattaDto", toDto(regatta));
        } catch (RuntimeException exc) {
            model.addAttribute("error", exc.getMessage());
        }

        return "regatta/update";
    }

    @PostMapping("/regatta/update")
    public String update(@RequestParam("id") long id, @Valid RegattaDto regatta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            regatta.setId(id);
            model.addAttribute("regattaDto", regatta);
            return "regatta/update";
        }
        try {
            regattaService.updateRegatta(id, regatta);
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "regatta/update";
        }
        return "redirect:/regatta/overview";
    }

    @GetMapping("/regatta/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Regatta regatta = regattaService.getRegatta(id);
            model.addAttribute("regattaDto", toDto(regatta));
        } catch (RuntimeException exc) {
            model.addAttribute("error", exc.getMessage());
        }
        return "regatta/delete";
    }

    @PostMapping("/regatta/delete/{id}")
    public String delete(@PathVariable long id, @Valid RegattaDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            dto.setId(id);
            model.addAttribute("regattaDto", dto);
            model.addAttribute("error", result.getAllErrors());
            return "regatta/delete";
        }

        regattaService.deleteRegatta(id);
        return "redirect:/regatta/overview";
    }

    @GetMapping("/regatta/search")
    public String search(@RequestParam(value = "dateAfter", required = false) LocalDate start,
            @RequestParam(value = "dateBefore", required = false) LocalDate end,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(required = false) Integer page, Model model) {
        Page<Regatta> regattas;

        if (start == null && end == null && category == null) {
            regattas = regattaService.getRegattas(getPage(page));
            model.addAttribute("regattas", regattas);
            return "regatta/search";
        }

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("category", category);
        regattas = regattaService.searchRegatta(getPage(page), start, end, category);

        model.addAttribute("regattas", regattas);
        return "regatta/search";
    }

    private RegattaDto toDto(Regatta regatta) {
        RegattaDto dto = new RegattaDto();

        dto.setId(regatta.getId());
        dto.setNameClub(regatta.getNameClub());
        dto.setCategory(regatta.getCategory());
        dto.setDate(regatta.getDate());
        dto.setMaxTeams(regatta.getMaxTeams());
        dto.setNameCompetition(regatta.getNameCompetition());

        return dto;
    }

    private int getPage(Integer page) {
        return page == null ? 0 : Math.max(0, page);
    }

    private void createSampleData() {
        RegattaDto CoastalClassicRegatta = new RegattaDto();
        CoastalClassicRegatta.setNameCompetition("Coastal Classic Regatta");
        CoastalClassicRegatta.setNameClub("Ocean Sailors Club");
        CoastalClassicRegatta.setCategory("1234567");
        CoastalClassicRegatta.setDate(java.time.LocalDate.of(2023, 10, 15));
        CoastalClassicRegatta.setMaxTeams(10);

        RegattaDto RiverRapidsChallenge = new RegattaDto();
        RiverRapidsChallenge.setNameCompetition("River Rapids Challenge");
        RiverRapidsChallenge.setNameClub("Rapids Rowing Club");
        RiverRapidsChallenge.setCategory("abcdefg");
        RiverRapidsChallenge.setDate(java.time.LocalDate.of(2023, 9, 25));
        RiverRapidsChallenge.setMaxTeams(8);

        RegattaDto WindyWatersSailingCup = new RegattaDto();
        WindyWatersSailingCup.setNameCompetition("Windy Waters Sailing Cup");
        WindyWatersSailingCup.setNameClub("Windy Shores Sailing Association");
        WindyWatersSailingCup.setCategory("k9H1dY5");
        WindyWatersSailingCup.setDate(java.time.LocalDate.of(2023, 10, 8));
        WindyWatersSailingCup.setMaxTeams(12);

        RegattaDto SpeedyPaddlefest = new RegattaDto();
        SpeedyPaddlefest.setNameCompetition("Speedy Paddlefest");
        SpeedyPaddlefest.setNameClub("Paddle Adventures Club");
        SpeedyPaddlefest.setCategory("Aa9d8Jq");
        SpeedyPaddlefest.setDate(java.time.LocalDate.of(2023, 9, 30));
        SpeedyPaddlefest.setMaxTeams(6);

        RegattaDto LakefrontKayakChallenge = new RegattaDto();
        LakefrontKayakChallenge.setNameCompetition("Lakefront Kayak Challenge");
        LakefrontKayakChallenge.setNameClub("Lakeside Paddlers Union");
        LakefrontKayakChallenge.setCategory("d7bJud8");
        LakefrontKayakChallenge.setDate(java.time.LocalDate.of(2023, 10, 1));
        LakefrontKayakChallenge.setMaxTeams(5);

        RegattaDto CoastalCatamaranClash = new RegattaDto();
        CoastalCatamaranClash.setNameCompetition("Coastal Catamaran Clash");
        CoastalCatamaranClash.setNameClub("Catamaran Enthusiasts Society");
        CoastalCatamaranClash.setCategory("k9d8Hdq");
        CoastalCatamaranClash.setDate(java.time.LocalDate.of(2023, 10, 22));
        CoastalCatamaranClash.setMaxTeams(7);

        RegattaDto WhitewaterRaftingRumble = new RegattaDto();
        WhitewaterRaftingRumble.setNameCompetition("Whitewater Rafting Rumble");
        WhitewaterRaftingRumble.setNameClub("Whitewater Thrill Seekers Club");
        WhitewaterRaftingRumble.setCategory("nBc5J9a");
        WhitewaterRaftingRumble.setDate(java.time.LocalDate.of(2023, 10, 12));
        WhitewaterRaftingRumble.setMaxTeams(4);

        RegattaDto SailfishSprint = new RegattaDto();
        SailfishSprint.setNameCompetition("Sailfish Sprint");
        SailfishSprint.setNameClub("Sailfish Racing Club");
        SailfishSprint.setCategory("JDA13a8");
        SailfishSprint.setDate(java.time.LocalDate.of(2023, 10, 5));
        SailfishSprint.setMaxTeams(9);

        RegattaDto DragonBoatDerby = new RegattaDto();
        DragonBoatDerby.setNameCompetition("Dragon Boat Derby");
        DragonBoatDerby.setNameClub("Dragon Paddlers Association");
        DragonBoatDerby.setCategory("k098Oda");
        DragonBoatDerby.setDate(java.time.LocalDate.of(2023, 9, 29));
        DragonBoatDerby.setMaxTeams(8);

        RegattaDto InlandCanoeClassic = new RegattaDto();
        InlandCanoeClassic.setNameCompetition("Inland Canoe Classic");
        InlandCanoeClassic.setNameClub("Canoe Enthusiasts Alliance");
        InlandCanoeClassic.setCategory("1YYjsaI");
        InlandCanoeClassic.setDate(java.time.LocalDate.of(2023, 10, 14));
        InlandCanoeClassic.setMaxTeams(6);

        regattaService.addRegatta(CoastalClassicRegatta);
        regattaService.addRegatta(RiverRapidsChallenge);
        regattaService.addRegatta(WindyWatersSailingCup);
        regattaService.addRegatta(SpeedyPaddlefest);
        regattaService.addRegatta(LakefrontKayakChallenge);
        regattaService.addRegatta(CoastalCatamaranClash);
        regattaService.addRegatta(WhitewaterRaftingRumble);
        regattaService.addRegatta(SailfishSprint);
        regattaService.addRegatta(DragonBoatDerby);
        regattaService.addRegatta(InlandCanoeClassic);
    }
}