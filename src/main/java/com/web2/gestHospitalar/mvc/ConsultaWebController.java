package com.web2.gestHospitalar.mvc;

import com.web2.gestHospitalar.model.Consulta;
import com.web2.gestHospitalar.service.ConsultaService;
import com.web2.gestHospitalar.repository.PacienteRepository;
import com.web2.gestHospitalar.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/consultas")
public class ConsultaWebController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<Consulta> consultas = consultaService.list(status, page, size);
        model.addAttribute("consultas", consultas.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", consultas.getTotalPages());
        model.addAttribute("status", status);
        model.addAttribute("pageSize", size);
        
        return "consultas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("pacientes", pacienteRepository.findAll());
        model.addAttribute("medicos", medicoRepository.findAll());
        return "consultas/form";
    }

    @PostMapping
    public String criar(@ModelAttribute Consulta consulta, RedirectAttributes redirectAttributes) {
        try {
            consultaService.create(consulta);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta criada com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/consultas/novo";
        }
        return "redirect:/consultas";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var consulta = consultaService.get(id);
        if (consulta.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Consulta não encontrada!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/consultas";
        }
        model.addAttribute("consulta", consulta.get());
        model.addAttribute("pacientes", pacienteRepository.findAll());
        model.addAttribute("medicos", medicoRepository.findAll());
        return "consultas/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Consulta consulta, RedirectAttributes redirectAttributes) {
        try {
            consultaService.update(id, consulta);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta atualizada com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/consultas/" + id + "/editar";
        }
        return "redirect:/consultas";
    }

    @GetMapping("/{id}/visualizar")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var consulta = consultaService.get(id);
        if (consulta.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Consulta não encontrada!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/consultas";
        }
        model.addAttribute("consulta", consulta.get());
        return "consultas/visualizar";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            consultaService.delete(id);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta removida com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/consultas";
    }
}
