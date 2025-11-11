package com.web2.gestHospitalar.mvc;

import com.web2.gestHospitalar.dto.RecepcionistaRequestDto;
import com.web2.gestHospitalar.dto.RecepcionistaResponseDto;
import com.web2.gestHospitalar.service.RecepcionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/recepcionistas")
public class RecepcionistaWebController {

    @Autowired
    private RecepcionistaService recepcionistaService;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String setor,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        List<RecepcionistaResponseDto> recepcionistas = recepcionistaService.listarRecepcionista();
        
        if (setor != null && !setor.isBlank()) {
            Pageable pageable = PageRequest.of(page, size);
            var paginado = recepcionistaService.listarPorSetor(setor, pageable);
            model.addAttribute("recepcionistas", paginado.getContent());
            model.addAttribute("totalPages", paginado.getTotalPages());
        } else {
            model.addAttribute("recepcionistas", recepcionistas);
            model.addAttribute("totalPages", 1);
        }
        
        model.addAttribute("currentPage", page);
        model.addAttribute("setor", setor);
        model.addAttribute("pageSize", size);
        
        return "recepcionistas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("recepcionista", new RecepcionistaRequestDto(null, null, null, null, null, null, null, null, null, null));
        return "recepcionistas/form";
    }

    @PostMapping
    public String criar(@ModelAttribute RecepcionistaRequestDto recepcionista, RedirectAttributes redirectAttributes) {
        try {
            recepcionistaService.criarRecepcionista(recepcionista);
            redirectAttributes.addFlashAttribute("mensagem", "Recepcionista criado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/recepcionistas/novo";
        }
        return "redirect:/recepcionistas";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            RecepcionistaResponseDto recepcionista = recepcionistaService.buscarPorId(id);
            model.addAttribute("recepcionista", recepcionista);
            model.addAttribute("id", id);
            return "recepcionistas/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Recepcionista não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/recepcionistas";
        }
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute RecepcionistaRequestDto recepcionista, RedirectAttributes redirectAttributes) {
        try {
            recepcionistaService.atualizarRecepcionista(id, recepcionista);
            redirectAttributes.addFlashAttribute("mensagem", "Recepcionista atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/recepcionistas/" + id + "/editar";
        }
        return "redirect:/recepcionistas";
    }

    @GetMapping("/{id}/visualizar")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            RecepcionistaResponseDto recepcionista = recepcionistaService.buscarPorId(id);
            model.addAttribute("recepcionista", recepcionista);
            return "recepcionistas/visualizar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Recepcionista não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/recepcionistas";
        }
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            recepcionistaService.excluirRecepcionista(id);
            redirectAttributes.addFlashAttribute("mensagem", "Recepcionista removido com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/recepcionistas";
    }
}
