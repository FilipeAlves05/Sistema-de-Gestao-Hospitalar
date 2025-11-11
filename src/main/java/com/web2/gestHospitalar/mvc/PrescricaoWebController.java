package com.web2.gestHospitalar.mvc;

import com.web2.gestHospitalar.model.Prescricao;
import com.web2.gestHospitalar.model.Consulta;
import com.web2.gestHospitalar.service.PrescricaoService;
import com.web2.gestHospitalar.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/prescricoes")
public class PrescricaoWebController {

    @Autowired
    private PrescricaoService prescricaoService;

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) Long consultaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<Prescricao> prescricoes = prescricaoService.list(consultaId, start, end, page, size);
        model.addAttribute("prescricoes", prescricoes.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prescricoes.getTotalPages());
        model.addAttribute("consultaId", consultaId);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("pageSize", size);
        
        return "prescricoes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("prescricao", new Prescricao());
        model.addAttribute("consultas", consultaRepository.findAll());
        return "prescricoes/form";
    }

    @PostMapping
    public String criar(@ModelAttribute Prescricao prescricao, RedirectAttributes redirectAttributes) {
        try {
            prescricaoService.create(prescricao);
            redirectAttributes.addFlashAttribute("mensagem", "Prescrição criada com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/prescricoes/novo";
        }
        return "redirect:/prescricoes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var prescricao = prescricaoService.get(id);
        if (prescricao.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Prescrição não encontrada!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/prescricoes";
        }
        model.addAttribute("prescricao", prescricao.get());
        model.addAttribute("consultas", consultaRepository.findAll());
        return "prescricoes/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Prescricao prescricao, RedirectAttributes redirectAttributes) {
        try {
            prescricaoService.update(id, prescricao);
            redirectAttributes.addFlashAttribute("mensagem", "Prescrição atualizada com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/prescricoes/" + id + "/editar";
        }
        return "redirect:/prescricoes";
    }

    @GetMapping("/{id}/visualizar")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var prescricao = prescricaoService.get(id);
        if (prescricao.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Prescrição não encontrada!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/prescricoes";
        }
        model.addAttribute("prescricao", prescricao.get());
        return "prescricoes/visualizar";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            prescricaoService.delete(id);
            redirectAttributes.addFlashAttribute("mensagem", "Prescrição removida com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/prescricoes";
    }
}
