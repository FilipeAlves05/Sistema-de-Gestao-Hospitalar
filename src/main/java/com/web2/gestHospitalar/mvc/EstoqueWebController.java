package com.web2.gestHospitalar.mvc;

import com.web2.gestHospitalar.model.Estoque;
import com.web2.gestHospitalar.dto.EstoqueDTO;
import com.web2.gestHospitalar.service.EstoqueService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estoque")
public class EstoqueWebController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public String listar(Model model) {
        List<EstoqueDTO> estoques = estoqueService.listarEstoques();
        model.addAttribute("estoque", estoques);

        return "estoque/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("estoque", new Estoque());
        return "estoque/form";
    }

    @PostMapping
    public String criar(@ModelAttribute EstoqueDTO estoque, RedirectAttributes redirectAttributes) {
        try {
            estoqueService.criarEstoque(estoque);
            redirectAttributes.addFlashAttribute("mensagem", "Estoque criado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/estoque/novo";
        }
        return "redirect:/estoque";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute EstoqueDTO estoque, RedirectAttributes redirectAttributes) {
        try {
            estoqueService.atualizarEstoque(id, estoque);
            redirectAttributes.addFlashAttribute("mensagem", "Estoque atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/estoque/" + id + "/editar";
        }
        return "redirect:/estoque";
    }

    @GetMapping("/{id}/visualizar")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        EstoqueDTO estoque = estoqueService.buscarEstoque(id);
        if (estoque.equals(null)) {
            redirectAttributes.addFlashAttribute("mensagem", "Estoque não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/estoque";
        }
        model.addAttribute("estoque", estoque);
        return "estoque/visualizar";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            estoqueService.excluirEstoque(id);
            redirectAttributes.addFlashAttribute("mensagem", "Estoque removido com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/estoque";
    }
}
