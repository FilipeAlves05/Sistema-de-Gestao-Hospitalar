package com.web2.gestHospitalar.mvc;

import com.web2.gestHospitalar.dto.MedicoRequestDto;
import com.web2.gestHospitalar.dto.MedicoResponseDto;
import com.web2.gestHospitalar.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/medicos")
public class MedicoWebController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String especialidade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        List<MedicoResponseDto> medicos = medicoService.listarMedicos();
        
        if (especialidade != null && !especialidade.isBlank()) {
            Pageable pageable = PageRequest.of(page, size);
            var paginado = medicoService.listarPorEspecialidade(especialidade, pageable);
            model.addAttribute("medicos", paginado.getContent());
            model.addAttribute("totalPages", paginado.getTotalPages());
        } else {
            model.addAttribute("medicos", medicos);
            model.addAttribute("totalPages", 1);
        }
        
        model.addAttribute("currentPage", page);
        model.addAttribute("especialidade", especialidade);
        model.addAttribute("pageSize", size);
        
        return "medicos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("medico", new MedicoRequestDto(null, null, null, null, null, null, null, null, null, null, null, null));
        return "medicos/form";
    }

    @PostMapping
    public String criar(@ModelAttribute MedicoRequestDto medico, RedirectAttributes redirectAttributes) {
        try {
            medicoService.criarMedico(medico);
            redirectAttributes.addFlashAttribute("mensagem", "Médico criado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/medicos/novo";
        }
        return "redirect:/medicos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            MedicoResponseDto medico = medicoService.buscarPorId(id);
            model.addAttribute("medico", medico);
            model.addAttribute("id", id);
            return "medicos/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Médico não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/medicos";
        }
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute MedicoRequestDto medico, RedirectAttributes redirectAttributes) {
        try {
            medicoService.atualizarMedico(id, medico);
            redirectAttributes.addFlashAttribute("mensagem", "Médico atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/medicos/" + id + "/editar";
        }
        return "redirect:/medicos";
    }

    @GetMapping("/{id}/visualizar")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            MedicoResponseDto medico = medicoService.buscarPorId(id);
            model.addAttribute("medico", medico);
            return "medicos/visualizar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Médico não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/medicos";
        }
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            medicoService.excluirMedico(id);
            redirectAttributes.addFlashAttribute("mensagem", "Médico removido com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/medicos";
    }
}
