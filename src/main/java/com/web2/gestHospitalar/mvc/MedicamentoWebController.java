package com.web2.gestHospitalar.mvc;

import com.web2.gestHospitalar.model.Medicamento;
import com.web2.gestHospitalar.dto.MedicamentoDTO;
import com.web2.gestHospitalar.service.MedicamentoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoWebController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public String listar(Model model) {
        List<MedicamentoDTO> medicamentos = medicamentoService.listarMedicamentos();
        model.addAttribute("medicamentos", medicamentos);

        return "medicamentos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("medicamento", new Medicamento());
        return "medicamentos/form";
    }

    @PostMapping
    public String criar(@ModelAttribute MedicamentoDTO medicamento, RedirectAttributes redirectAttributes) {
        try {
            medicamentoService.criarMedicamento(medicamento);
            redirectAttributes.addFlashAttribute("mensagem", "Medicamento criado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/medicamentos/novo";
        }
        return "redirect:/medicamentos";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute MedicamentoDTO medicamento, RedirectAttributes redirectAttributes) {
        try {
            medicamentoService.atualizarMedicamento(id, medicamento);
            redirectAttributes.addFlashAttribute("mensagem", "Medicamento atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/medicamentos/" + id + "/editar";
        }
        return "redirect:/medicamentos";
    }

    @GetMapping("/{id}/visualizar")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        MedicamentoDTO medicamento = medicamentoService.buscarMedicamento(id);
        if (medicamento.equals(null)) {
            redirectAttributes.addFlashAttribute("mensagem", "Medicamento não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/medicamentos";
        }
        model.addAttribute("medicamento", medicamento);
        return "medicamentos/visualizar";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            medicamentoService.excluirMedicamento(id);
            redirectAttributes.addFlashAttribute("mensagem", "Medicamento removido com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/medicamentos";
    }
}
