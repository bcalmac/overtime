package io.github.bcalmac.overtime.server.controller;

import io.github.bcalmac.overtime.server.utils.IsoDateFormat;
import io.github.bcalmac.overtime.server.service.Shift;
import io.github.bcalmac.overtime.server.service.ShiftService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class ShiftsController {

    private final ShiftService shiftService;

    public ShiftsController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping
    public String index() {
        return "redirect:/shifts/list";
    }

    @GetMapping("shifts/list")
    public String listShifts(Model model) {
        model.addAttribute("shifts", shiftService.getRecentShifts());
        return "shifts-list";
    }

    @GetMapping("shifts/new")
    public String newShift(Model model) {
        model.addAttribute("shift", shiftService.emptyShift());
        return "shifts-edit";
    }

    @GetMapping("shifts/edit/{date}")
    public String editShift(@PathVariable @IsoDateFormat LocalDate date, Model model) {
        model.addAttribute("shift", shiftService.getShift(date));
        return "shifts-edit";
    }

    @PostMapping("shifts/submit")
    public String submitShift(@Valid Shift shift, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            shiftService.storeShift(shift);
            return "redirect:/shifts/list";
        }
        return "shifts-edit";
    }

    @GetMapping("shifts/delete/{date}")
    public String deleteShift(@PathVariable @IsoDateFormat LocalDate date) {
        shiftService.deleteShift(date);
        return "redirect:/shifts/list";
    }
}

