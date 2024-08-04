using System;
using System.Collections.Generic;
using DDDSample1.Domain.Entregas;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Armazens
{
    public class Armazem : Entity<ArmazemId>, IAggregateRoot
    {
     
        public Designacao Designacao {get; private set;}
        public Endereco Endereco {get; private set;}
        public Localizacao Localizacao {get; private set;}
        public Ativo Ativo {get; private set;}
        public virtual List<Entrega> Entregas {get; private set;}
        public Armazem(){  }

        public Armazem (String armazemId, 
                        String designacao, 
                        String rua, String codigoPostal,
                        double latitude,double longitude, double altitude, bool ativo) {
            validarId(armazemId);
            this.Id = new ArmazemId(armazemId);
            this.Designacao = new Designacao(designacao);
            this.Endereco = new Endereco (rua, codigoPostal);
            this.Localizacao = new Localizacao (latitude,longitude,altitude);
            this.Ativo = new Ativo(ativo);
        }
        
        public void ChangeDesignacao(string designacao){
            this.Designacao = new Designacao(designacao);
        }

        public void ChangeEndereco(string rua, string codigoPostal){
            this.Endereco = new Endereco(rua, codigoPostal);
        }

        public void ChangeLocalizacao ( double longitude, double latitude, double altitude) {
            this.Localizacao = new Localizacao (latitude,longitude, altitude);
        }

        public void ChangeAtivo (bool ativo) {
            this.Ativo = new Ativo(ativo);
        }

        public void validarId(string id){
            if(id.Length != 3) {
                throw new BusinessRuleValidationException("Id inválido!");
            }
        }
       
    }
}