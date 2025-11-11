package com.web2.gestHospitalar.mvc;

import com.web2.gestHospitalar.model.Paciente;
import com.web2.gestHospitalar.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes")
public class PacienteWebController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<Paciente> pacientes = pacienteService.list(nome, page, size);
        model.addAttribute("pacientes", pacientes.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pacientes.getTotalPages());
        model.addAttribute("nome", nome);
        model.addAttribute("pageSize", size);
        
        return "pacientes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/form";
    }

    @PostMapping
    public String criar(@ModelAttribute Paciente paciente, RedirectAttributes redirectAttributes) {
        try {
            pacienteService.create(paciente);
            redirectAttributes.addFlashAttribute("mensagem", "Paciente criado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/pacientes/novo";
        }
        return "redirect:/pacientes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var paciente = pacienteService.get(id);
        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Paciente não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/pacientes";
        }
        model.addAttribute("paciente", paciente.get());
        return "pacientes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Paciente paciente, RedirectAttributes redirectAttributes) {
        try {
            pacienteService.update(id, paciente);
            redirectAttributes.addFlashAttribute("mensagem", "Paciente atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/pacientes/" + id + "/editar";
        }
        return "redirect:/pacientes";
    }

    @GetMapping("/{id}/visualizar")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var paciente = pacienteService.get(id);
        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Paciente não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/pacientes";
        }
        model.addAttribute("paciente", paciente.get());
        return "pacientes/visualizar";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pacienteService.delete(id);
            redirectAttributes.addFlashAttribute("mensagem", "Paciente removido com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/pacientes";
    }
}
