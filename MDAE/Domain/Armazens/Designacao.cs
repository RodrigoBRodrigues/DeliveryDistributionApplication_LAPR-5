using System;
using DDDSample1.Domain.Shared;
using System.Text.RegularExpressions;

namespace DDDSample1.Domain.Armazens{

    public class Designacao : IValueObject {

        public string Nome { get ; private set;}

        public Designacao (string nome)
        {
            validarDesignacao(nome);
            this.Nome = nome;
        }

        public void validarDesignacao(string designacao){
            if (designacao.Length < 0 || designacao.Length > 50){
                throw new BusinessRuleValidationException("Designação inválida!");
            }
        }
    }
}