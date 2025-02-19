package com.dentalclinic.controller;

import com.dentalclinic.model.TreatmentSheet;
import com.dentalclinic.service.TreatmentSheetService;
import com.dentalclinic.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/treatment-sheets")
public class TreatmentSheetController {
    @Autowired
    private TreatmentSheetService treatmentSheetService;

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping
    public String listTreatmentSheets(Model model) {
        model.addAttribute("treatmentSheets", treatmentSheetService.getAllTreatmentSheets());
        return "treatment-sheets/list";
    }

    @GetMapping("/{id}")
    public String viewTreatmentSheet(@PathVariable Long id, Model model) {
        TreatmentSheet treatmentSheet = treatmentSheetService.getTreatmentSheetById(id);
        model.addAttribute("treatmentSheet", treatmentSheet);
        model.addAttribute("treatments", treatmentService.getTreatmentsByTreatmentSheetId(id));
        return "treatment-sheets/details";
    }

    @GetMapping("/new")
    public String showTreatmentSheetForm(Model model) {
        model.addAttribute("treatmentSheet", new TreatmentSheet());
        return "treatment-sheets/form";
    }

    @PostMapping("/save")
    public String saveTreatmentSheet(@ModelAttribute TreatmentSheet treatmentSheet) {
        treatmentSheetService.saveTreatmentSheet(treatmentSheet);
        return "redirect:/treatment-sheets";
    }

    @GetMapping("/delete/{id}")
    public String deleteTreatmentSheet(@PathVariable Long id) {
        treatmentSheetService.deleteTreatmentSheet(id);
        return "redirect:/treatment-sheets";
    }
}