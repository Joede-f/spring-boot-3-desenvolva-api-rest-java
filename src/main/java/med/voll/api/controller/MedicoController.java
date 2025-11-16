package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("medicos")

public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        this.repository.save(new Medico(dados));
    }

//    @GetMapping
//    public List<DadosListagemMedico> listar(){
//        return this.repository.findAll().stream().map(DadosListagemMedico::new).toList();
//    }

//    @GetMapping
//    public Page<DadosListagemMedico> listar(Pageable paginacao){
//        return this.repository.findAll(paginacao).map(DadosListagemMedico::new);
//    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 2, page = 0, sort = {"nome"}) Pageable paginacao){
//        return this.repository.findAll(paginacao).map(DadosListagemMedico::new);
        return this.repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void ataulizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

//    Realmente eleta o registro
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id){
//        repository.deleteById(id);
//    }


//    Só muda o status para desativado que seria a exclusão lógica
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        medico.excluir();
    }




}
