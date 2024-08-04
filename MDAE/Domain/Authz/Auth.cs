using System;
using System.Collections.Generic;
using DDDSample1.Domain.Entregas;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Authz
{
    public class Auth : Entity<Utilizador>, IAggregateRoot
    {
        public Utilizador  Utilizador {get; private set;}
        public Nome Nome {get; private set;}
        public Role Role {get; private set;}
        public Email Email{get; private set;}
        public Telemovel Telemovel{get; private set;}
        public Ativo Ativo {get; private set;}

        public Auth(){  }

        public Auth ( String utilizador,
                    String nome, String role,
                    String telemovel, String email, bool ativo) {
            this.Utilizador = new Utilizador(utilizador);
            this.Nome = new Nome(nome);
            this.Role = new Role (role);
            this.Telemovel = new Telemovel (telemovel);
            this.Email = new Email (email);
            this.Ativo = new Ativo(ativo);
        }

        public void Anon(){
            this.Ativo = new Ativo(false);
            this.Nome = new Nome("Hidden");
            this.Role = new Role("Hidden");
            this.Telemovel = new Telemovel("Hidden");
            this.Email = new Email("Hidden");
        }
        
        public void ChangeAtivo (bool ativo) {
            this.Ativo = new Ativo(ativo);
        }
        //TODO: fazer com que vá buscar o utilizador que está na base de dados e ajuntar com o count + 1
        public void ChangeUtilizador (string utilizador)
        {
            this.Utilizador = new Utilizador(utilizador);
        }
        public void ChangeNome (string nome) {
            this.Nome = new Nome(nome);
        }
        public void ChangeTelemovel (string telemovel) {
            this.Telemovel = new Telemovel(telemovel);
        }
        public void ChangeEmail (string email) {
            this.Email = new Email(email);
        }
       
    }
}